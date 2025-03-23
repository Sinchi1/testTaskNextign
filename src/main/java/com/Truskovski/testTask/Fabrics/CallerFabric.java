package com.Truskovski.testTask.Fabrics;

import com.Truskovski.testTask.DataBase.CallerRepository;
import com.Truskovski.testTask.Objects.CallerDataClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CallerFabric {

    private final Random random = new Random();

    private final CallerRepository callerRepository;

    @Autowired
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
