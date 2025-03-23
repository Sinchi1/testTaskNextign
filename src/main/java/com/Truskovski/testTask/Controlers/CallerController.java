package com.Truskovski.testTask.Controlers;

import com.Truskovski.testTask.DataBase.CallerRepository;
import com.Truskovski.testTask.Fabrics.CallerFabric;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class CallerController {

    private final CallerFabric callerFabric;
    private final CallerRepository callerRepository;

    public CallerController(CallerFabric callerFabric, CallerRepository callerRepository) {
        this.callerFabric = callerFabric;
        this.callerRepository = callerRepository;
    }

    @PostMapping("caller/generate/{count}")
    public String generateCallers(@PathVariable int count){
        callerFabric.generateCallers(count);
        return System.out.printf("Создано %s записей", count).toString();
    }

    @GetMapping("caller/get/{phoneNumber}")
    public String getCallerByNumber(@PathVariable String phoneNumber){
        Boolean caller = callerRepository.findByPhoneNumber(phoneNumber);
        return caller ? "Найден абонент с номером " + phoneNumber :
                "Абонента с номером " + phoneNumber + " не существует!";
    }
}
