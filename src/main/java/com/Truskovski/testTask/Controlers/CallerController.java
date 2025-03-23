package com.Truskovski.testTask.Controlers;

import com.Truskovski.testTask.DataBase.CallerRepository;
import com.Truskovski.testTask.Fabrics.CallerFabric;
import com.Truskovski.testTask.Objects.CallerDataClass;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

/**
 * Controller предоставляющий доступ к работе с абонентами.
 */
@RestController
@RequestMapping("/api/caller")
public class CallerController {

    private final CallerFabric callerFabric;
    private final CallerRepository callerRepository;

    public CallerController(CallerFabric callerFabric, CallerRepository callerRepository) {
        this.callerFabric = callerFabric;
        this.callerRepository = callerRepository;
    }

    /**
     * POST энд-поинт (/generate) предоставляет возможность создания определённого параметром количества абонентов.
     * @param count сколько абонентов будет создано.
     * @return Возвращает информацию в виде String, об успешности создания абонентов, либо ошибку.
     */
    @PostMapping("/generate")
    public ResponseEntity<?> generateCallers(@RequestParam int count) {
        if (count > 0) {
            callerFabric.generateCallers(count);
            return ResponseEntity.ok("Создано " + count + " записей");
        } else return ResponseEntity.badRequest().body("Вы ввели некорректное число создаваемых абонентов ");
    }

    /**
     * POST энд-поинт (/createCaller) предоставляет возможность создания абонента с определённым номером.
     * @param number номер абонента, который будет добавлен в базу данных.
     * @return Возвращает информацию в виде String, об успешности создания абонента, либо ошибку.
     */
    @PostMapping("/createCaller")
    public ResponseEntity<?> createCaller(@RequestParam String number) {
        if (number.matches("79[0-9]{9}")) {
            callerRepository.save(new CallerDataClass(number));
            return ResponseEntity.ok("Успешно создан абонент");
        } else return ResponseEntity.badRequest().body("Вы ввели некорректный номер. Используйте формат \"7...11\"");
    }

    /**
     * GET энд-поинт (/get) предоставляет возможность проверить существование абонента по его номеру телефона.
     * @param phoneNumber телефонный номер абонента, который требуется обнаружить.
     * @return Возвращает информацию в виде String, о существовании абонента, либо ошибку.
     */
    @GetMapping("/get")
    public ResponseEntity<?> getCallerByNumber(@RequestParam String phoneNumber) {
        if (phoneNumber.matches("79[0-9]{9}")) {
            CallerDataClass caller = callerRepository.findByPhoneNumber(phoneNumber);
            return caller != null ? ResponseEntity.ok("Найден абонент с номером " + phoneNumber) :
                    ResponseEntity.badRequest().body("Абонента с номером " + phoneNumber + " не существует!");
        }
        else {
            return ResponseEntity.badRequest().body("Вы ввели изначально неправильный номер");
        }
    }

}
