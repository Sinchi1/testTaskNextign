package com.Truskovski.testTask.Fabrics;

import com.Truskovski.testTask.DataBase.CDRRepository;
import com.Truskovski.testTask.DataBase.CallerRepository;
import com.Truskovski.testTask.Objects.CDRDataClass;
import com.Truskovski.testTask.Objects.CallerDataClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Component
public class CDRFabric {

    private final Random random = new Random();

    private final CDRRepository cdrRepository;
    private final CallerRepository callerRepository;

    @Autowired
    public CDRFabric(CDRRepository cdrRepository, CallerRepository subscriberRepository) {
        this.cdrRepository = cdrRepository;
        this.callerRepository = subscriberRepository;
    }

    public void generateCDR(int callCount) {
        List<CallerDataClass> callers = callerRepository.findAll();

        if (callers.size() < 10) {
            throw new IllegalStateException("В БД должно быть минимум 10 абонентов!");
        }

        LocalDateTime currentDate = LocalDateTime.now();

        for (int i = 0; i < callCount; i++) {
            CallerDataClass caller = callers.get(random.nextInt(callers.size()));
            CallerDataClass receiver;
            do {
                receiver = callers.get(random.nextInt(callers.size()));
            } while (receiver.getNumber().equals(caller.getNumber()));

            currentDate = currentDate.plusMonths(random.nextInt(12)).plusDays(random.nextInt(28));
            currentDate = currentDate.withYear(2025);


            LocalDateTime callStart = currentDate
                    .withHour( random.nextInt(24))
                    .withMinute(random.nextInt(60))
                    .withSecond(random.nextInt(60))
                    .withNano(random.nextInt(1_000_000_000));


            int callDuration = random.nextInt(60)+10;
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