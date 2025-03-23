package com.Truskovski.testTask.Controlers;

import com.Truskovski.testTask.DataBase.CallerRepository;
import com.Truskovski.testTask.Fabrics.CallerFabric;
import com.Truskovski.testTask.Objects.CallerDataClass;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/caller")
public class CallerController {

    private final CallerFabric callerFabric;
    private final CallerRepository callerRepository;

    public CallerController(CallerFabric callerFabric, CallerRepository callerRepository) {
        this.callerFabric = callerFabric;
        this.callerRepository = callerRepository;
    }

    @PostMapping("/generate")
    public String generateCallers(@RequestParam int count){
        callerFabric.generateCallers(count);
        return ("Создано "+ count +" записей");
    }

    @GetMapping("/get")
    public String getCallerByNumber(@RequestParam String phoneNumber){
        CallerDataClass caller = callerRepository.findByPhoneNumber(phoneNumber);
        return caller!=null ? "Найден абонент с номером " + phoneNumber :
                "Абонента с номером " + phoneNumber + " не существует!";
    }
}
