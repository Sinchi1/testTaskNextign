package com.Truskovski.testTask.Controlers;

import com.Truskovski.testTask.Fabrics.CDRFabric;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cdr")
public class CDRController {

    private final CDRFabric cdrFabric;

    public CDRController(CDRFabric cdrFabric) {
        this.cdrFabric = cdrFabric;
    }

    @PostMapping("/generate")
    public String generateCDRs(@RequestParam int count) {
        cdrFabric.generateCDR(count);
        return "Создано "+ count +" CDR Записей!";
    }



}
