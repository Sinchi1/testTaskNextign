package com.Truskovski.testTask.Controlers;

import com.Truskovski.testTask.DataBase.CallerRepository;
import com.Truskovski.testTask.Fabrics.CDRFabric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cdr")
public class CDRController {

    private final CDRFabric cdrFabric;

    private final CallerRepository callerRepository;

    public CDRController(CDRFabric cdrFabric, CallerRepository callerRepository) {
        this.cdrFabric = cdrFabric;
        this.callerRepository = callerRepository;
    }

    @PostMapping("/generate")
    public ResponseEntity<?> generateCDRs(@RequestParam int count) {
        if (callerRepository.findAll().isEmpty()) {
            return ResponseEntity.badRequest().body("В базе данных нету ни единого абонента");
        } else if (callerRepository.findAll().size() < 2) {
            return ResponseEntity.badRequest().body("В базе данных нету двух абонентов для взаимных звонков");
        }
        cdrFabric.generateCDR(count);
        return ResponseEntity.ok("Создано " + count + " CDR Записей!");
    }


}
