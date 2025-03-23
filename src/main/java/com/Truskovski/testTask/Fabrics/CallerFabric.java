package com.Truskovski.testTask.Fabrics;

import com.Truskovski.testTask.DataBase.CallerRepository;
import com.Truskovski.testTask.Objects.CallerDataClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Класс фабрика для создания абонентов.
 */
@Component
public class CallerFabric {

    private final Random random = new Random();

    private final CallerRepository callerRepository;

    @Autowired
    public CallerFabric(CallerRepository callerRepository) {
        this.callerRepository = callerRepository;
    }

    /**
     * Метод для генерации заданного числа абонентов.
     *
     * @param callersAmount  заданное число создаваемых абонентов.
     */
    public void generateCallers(int callersAmount) {
        for (int i = 0; i < callersAmount; i++) {
            callerRepository.save(new CallerDataClass(
                    generatePhoneNumber()
            ));
        }
    }

    private String generatePhoneNumber() {
        int randomPart = random.nextInt(900000000);
        String number = String.format("%09d", randomPart);
        return "79" + number;
    }

}
