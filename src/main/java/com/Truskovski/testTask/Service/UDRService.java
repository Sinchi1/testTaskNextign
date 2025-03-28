package com.Truskovski.testTask.Service;

import com.Truskovski.testTask.DataBase.CDRRepository;
import com.Truskovski.testTask.Objects.CDRDataClass;
import com.Truskovski.testTask.Objects.DTO.UDRResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Вспомогательный класс для генерации UDR отчётов по существующим CDR записям.
 * Имеет на борту два основным метода: Создание UDR отчёта для одного абонента, создание UDR отчёта для всех пользователей за месяц.
 * Вспомогательным методами являются функции для вычисления времени звонков абонентов.
 */
@Service
public class UDRService {

    @Autowired
    private CDRRepository cdrRepository;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS");


    /**
     * Метод для создания одного UDR Отчёта для конкретного абонента по его номеру телефона.
     *
     * @param msisdn      Номер телефона абонента
     * @param month       Месяц представляющий период поиска CDR записей. Взаимозаменяем с fullPeriod.
     * @param fullPeriod  Переменная либо true, либо false. Сигнализирует методу, что нужно получить данные за весь период пользования.
     * @return Возвращает DTO класс несущий в себе информацию UDR отчёта
     */
    public UDRResponse getUDRForOneCaller(String msisdn, String month, boolean fullPeriod) {
        List<CDRDataClass> records;
        if (fullPeriod) {
            records = cdrRepository.findByMsisdn(msisdn);
        } else {
            if (month == null) {
                throw new IllegalArgumentException("Месяц не может быть null");
            }
            records = cdrRepository.findByMsisdnAndMonth(msisdn, month);
        }

        return buildUDRResponse(msisdn, records);
    }

    /**
     * Метод, который возвращает UDR отчёт по всем пользователям совершавшим звонки в определённый месяц.
     *
     * @param month  Переменная, по которой ищутся записи за определённый месяц.
     * @return  Список DTO классов, которые несут в себе информацию за одного абонента.
     */
    public List<UDRResponse> getUDRForAllCallers(String month) {
        if (month == null) {
            throw new IllegalArgumentException("Месяц не может быть null");
        }

        List<CDRDataClass> records = cdrRepository.findAllByMonth(month);

        Map<String, List<CDRDataClass>> recordsByMsisdn = new HashMap<>();

        for (CDRDataClass record : records) {
            String key;
            if (record.getType().equals("01")) {
                key = record.getInComeNumber();
            } else {
                key = record.getOutComeNumber();
            }

            recordsByMsisdn.computeIfAbsent(key, k -> new ArrayList<>()).add(record);
        }

        List<UDRResponse> responses = new ArrayList<>();

        for (Map.Entry<String, List<CDRDataClass>> entry : recordsByMsisdn.entrySet()) {
            responses.add(buildUDRResponse(entry.getKey(), entry.getValue()));
        }

        return responses;

    }

    /**
     * Создаёт UDR отчёт для одного абонента.
     *
     * @param msisdn   номер телефона абонента.
     * @param records  список всех CDR-записей об абоненте.
     * @return  Возвращает DTO Объект, который хранит в себе информацию об абоненте. Номер, время входящих и исходящих звонков.
     */
    private UDRResponse buildUDRResponse(String msisdn, List<CDRDataClass> records) {

        String incomingTotalTime = "00:00:00";
        String outgoingTotalTime = "00:00:00";

        for (CDRDataClass record : records) {
            String duration = calculateDuration(record.getTimeStarted(), record.getTimeEnded());
            if (duration.startsWith("Продолжительность")) {
                return new UDRResponse(msisdn + ": " + duration, incomingTotalTime, outgoingTotalTime);
            }
            if (record.getType().equals("01")) {
                incomingTotalTime = addDurations(incomingTotalTime, duration);
            } else if (record.getType().equals("02")) {
                outgoingTotalTime = addDurations(outgoingTotalTime, duration);
            } else {
                return new UDRResponse(msisdn + ": Неизвестный тип звонка", incomingTotalTime, outgoingTotalTime);
            }
        }

        return new UDRResponse(msisdn, incomingTotalTime, outgoingTotalTime);
    }

    /**
     * Метод, который вычисляет длительность звонка.
     *
     * @param timeStarted  время когда звонок начался
     * @param timeEnded    время когда звонок закончился
     * @return  Длительность звонка в формате String HH:mm:ss.
     */
    private String calculateDuration(String timeStarted, String timeEnded) {
        LocalDateTime start = LocalDateTime.parse(timeStarted, formatter);
        LocalDateTime end = LocalDateTime.parse(timeEnded, formatter);
        Duration duration = Duration.between(start, end);

        if (duration.isNegative()) {
            return "Продолжительность звонков абонента отрицательна.";
        }

        return getString(duration);
    }

    /**
     * Складывает две длительности звонков.
     */
    private String addDurations(String duration1, String duration2) {
        Duration d1 = parseDuration(duration1);
        Duration d2 = parseDuration(duration2);
        Duration sum = d1.plus(d2);
        return getString(sum);
    }

    private String getString(Duration sum) {
        long totalMillis = sum.toMillis();
        long hours = totalMillis / (1000 * 60 * 60);
        long minutes = (totalMillis % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (totalMillis % (1000 * 60)) / 1000;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private Duration parseDuration(String duration) {
        String[] parts = duration.split(":");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Длительность должна быть в формате HH:mm:ss");
        }

        long hours = Long.parseLong(parts[0]);
        long minutes = Long.parseLong(parts[1]);
        long seconds = Long.parseLong(parts[2]);
        return Duration.ofHours(hours).plusMinutes(minutes).plusSeconds(seconds);
    }
}