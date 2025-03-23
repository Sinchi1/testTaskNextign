package com.Truskovski.testTask.Controlers;

import com.Truskovski.testTask.DataBase.CallerRepository;
import com.Truskovski.testTask.Fabrics.CallerFabric;
import com.Truskovski.testTask.Objects.CallerDataClass;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;


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
    public ResponseEntity<?> generateCallers(@RequestParam int count) {
        if (count > 0) {
            callerFabric.generateCallers(count);
            return ResponseEntity.ok("Создано " + count + " записей");
        } else return ResponseEntity.badRequest().body("Вы ввели некорректное число создаваемых абонентов ");
    }

    @PostMapping("/generate")
    public ResponseEntity<?> createCaller(@RequestParam String number) {
        if (number.matches("79[0-9]{9}")) {
            callerRepository.save(new CallerDataClass(number));
            return ResponseEntity.ok("Успешно создан абонент");
        } else return ResponseEntity.badRequest().body("Вы ввели некорректный номер. Используйте формат \"7...11\"");
    }

    @GetMapping("/get")
    public ResponseEntity<?> getCallerByNumber(@RequestParam String phoneNumber) {
        CallerDataClass caller = callerRepository.findByPhoneNumber(phoneNumber);
        return caller != null ? ResponseEntity.ok("Найден абонент с номером " + phoneNumber) :
                ResponseEntity.badRequest().body("Абонента с номером " + phoneNumber + " не существует!");
    }

}
