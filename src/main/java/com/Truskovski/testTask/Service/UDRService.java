package com.Truskovski.testTask.Service;

import com.Truskovski.testTask.DataBase.CDRRepository;
import com.Truskovski.testTask.Objects.CDRDataClass;
import com.Truskovski.testTask.Objects.DTO.UDRResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UDRService {

    @Autowired
    private CDRRepository cdrRepository;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS");

    public UDRResponse getUDRForOneCaller(String msisdn, String month, boolean fullPeriod) {
            List<CDRDataClass> records;
            if (fullPeriod) {
                records = cdrRepository.findByMsisdn(msisdn);
            } else {
                records = cdrRepository.findByMsisdnAndMonth(msisdn, month);
            }

            return buildUDRResponse(msisdn, records);
    }

    public List<UDRResponse> getUDRForAllCallers(String month) {
            if (month == null) {
                throw new IllegalArgumentException("Месяц не может быть null");
            }

            List<CDRDataClass> records = cdrRepository.findAllByMonth(month);

            Map<String, List<CDRDataClass>> recordsByMsisdn = new HashMap<>();

            for (CDRDataClass record : records) {
                String key;
                if (record.getType().equals("1")) {
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

    private UDRResponse buildUDRResponse(String msisdn, List<CDRDataClass> records) {
        String incomingTotalTime = "00:00:00";
        String outgoingTotalTime = "00:00:00";

        for (CDRDataClass record : records) {
                String duration = calculateDuration(record.getTimeStarted(), record.getTimeEnded());

                if (record.getType().equals("01")) {
                    incomingTotalTime = addDurations(incomingTotalTime, duration);
                } else if (record.getType().equals("02")) {
                    outgoingTotalTime = addDurations(outgoingTotalTime, duration);
                }
                else {
                    return new UDRResponse("Неизвестный тип звонка", incomingTotalTime, outgoingTotalTime);
                }
        }

        return new UDRResponse(msisdn, incomingTotalTime, outgoingTotalTime);
    }

    private String calculateDuration(String timeStarted, String timeEnded) {

            LocalDateTime start = LocalDateTime.parse(timeStarted, formatter);
            LocalDateTime end = LocalDateTime.parse(timeEnded, formatter);
            Duration duration = Duration.between(start, end);

            if (duration.isNegative()) {
                return ("Продолжительность звонков абонента отрицательна.");
            }

            long hours = duration.toHours();
            long minutes = duration.toMinutesPart();
            long seconds = duration.toSecondsPart();
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);

    }

    private String addDurations(String duration1, String duration2) {
            Duration d1 = parseDuration(duration1);
            Duration d2 = parseDuration(duration2);
            Duration sum = d1.plus(d2);
            long hours = sum.toHours();
            long minutes = sum.toMinutesPart();
            long seconds = sum.toSecondsPart();
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private Duration parseDuration(String duration) {
            String[] parts = duration.split(":");
            long hours = Long.parseLong(parts[0]);
            long minutes = Long.parseLong(parts[1]);
            long seconds = Long.parseLong(parts[2]);
            return Duration.ofHours(hours).plusMinutes(minutes).plusSeconds(seconds);
    }
}