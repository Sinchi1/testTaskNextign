package com.Truskovski.testTask.Controlers;

import com.Truskovski.testTask.DataBase.CDRRepository;
import com.Truskovski.testTask.Fabrics.CDRFabric;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CDRController {

    private final CDRFabric cdrFabric;
    private final CDRRepository cdrRepository;

    public CDRController(CDRFabric cdrFabric, CDRRepository cdrRepository) {
        this.cdrFabric = cdrFabric;
        this.cdrRepository = cdrRepository;
    }

    @PostMapping("cdr/generate{count}")
    public String generateCDRs(@PathVariable int count) {
        cdrRepository.saveAll(cdrFabric.generateCDR(count));
        return "Создано " + count + " CDR-записей!";
    }
}
