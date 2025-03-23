package com.Truskovski.testTask.Fabrics;

import com.Truskovski.testTask.DataBase.CallerRepository;
import com.Truskovski.testTask.Objects.CallerDataClass;

import java.util.Random;

public class CallerFabric {

    private final Random random = new Random();

    private final CallerRepository callerRepository;

    public CallerFabric(CallerRepository callerRepository){
        this.callerRepository = callerRepository;
    }

    public void generateCallers(int callersAmount){
        for (int i = 0; i < callersAmount; i++){
            callerRepository.save(new CallerDataClass(
                    generatePhoneNumber()
            ));
        }
    }

    private String generatePhoneNumber() {
        return "79" + (random.nextInt(900000000));
    }

}
