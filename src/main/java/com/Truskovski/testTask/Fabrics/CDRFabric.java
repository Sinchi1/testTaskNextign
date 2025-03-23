package com.Truskovski.testTask.Fabrics;

import com.Truskovski.testTask.DataBase.CDRRepository;
import com.Truskovski.testTask.DataBase.CallerRepository;
import com.Truskovski.testTask.Objects.CDRDataClass;
import com.Truskovski.testTask.Objects.CallerDataClass;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Component
public class CDRFabric {

    private final Random random = new Random();

    private final CDRRepository cdrRepository;
    private final CallerRepository callerRepository;

    public CDRFabric(CDRRepository cdrRepository, CallerRepository subscriberRepository) {
        this.cdrRepository = cdrRepository;
        this.callerRepository = subscriberRepository;
    }

    public void generateCDR(int callCount) {
        List<CallerDataClass> callers = callerRepository.findAll();
        if (callers.size() < 10) {
            throw new IllegalStateException("В БД должно быть минимум 10 абонентов!");
        }

        LocalDateTime currentDate = LocalDateTime.now().minusYears(1);

        for (int i = 0; i < callCount; i++) {
            CallerDataClass caller = callers.get(random.nextInt(callers.size()));
            CallerDataClass receiver;
            do {
                receiver = callers.get(random.nextInt(callers.size()));
            } while (receiver.getNumber().equals(caller.getNumber()));

            LocalDateTime callStart = currentDate.plusMinutes(random.nextInt(60));
            int callDuration = random.nextInt(10) + 1;
            LocalDateTime callEnd = callStart.plusMinutes(callDuration);

            CDRDataClass cdr = new CDRDataClass(
                    random.nextBoolean() ? "01" : "02",
                    caller.getNumber(),
                    receiver.getNumber(),
                    callStart.toString(),
                    callEnd.toString()
            );

            cdrRepository.save(cdr);
            currentDate = callEnd.plusMinutes(random.nextInt(30));
        }
    }

}


/** CDR Example
 *  02,79876543221, 79123456789, 2025-02-10T14:56:12, 2025-02-10T14:58:20
 *  CDR-запись включает в себя следующие данные:
 *  тип вызова (01 - исходящие, 02 - входящие);
 *  номер абонента, инициирующего звонок;
 *  номер абонента, принимающего звонок;
 * дата и время начала звонка (ISO 8601);
 *  дата и время окончания звонка (ISO 8601);
 * CDR-отчет представляет собой набор CDR-записей.
 * Разделитель данных – запятая;
 * разделитель записей – перенос строки;
 * данные обязательно формируются в хронологическом порядке;
 * в рамках задания CDR-отчет может быть обычным txt\csv;
 *
 */