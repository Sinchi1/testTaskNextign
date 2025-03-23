package com.Truskovski.testTask.Controlers;

import com.Truskovski.testTask.DataBase.CDRRepository;
import com.Truskovski.testTask.Fabrics.CDRFabric;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CDRController {

    private final CDRFabric cdrFabric;

    public CDRController(CDRFabric cdrFabric) {
        this.cdrFabric = cdrFabric;
    }

    @PostMapping("cdr/generate{count}")
    public String generateCDRs(@PathVariable int count) {
        cdrFabric.generateCDR(count);
        return "Создано " + count + " CDR-записей!";
    }



}
