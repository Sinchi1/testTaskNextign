package com.Truskovski.testTask.Fabrics;

import com.Truskovski.testTask.Objects.CDRDataClass;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class CDRFabric {

    private final Random random = new Random();

    public List<CDRDataClass> generateCDR(int numberToGenerate){
        List<CDRDataClass> cdrDataClassList = new ArrayList<>();
        for (int i = 0; i < numberToGenerate; i++){
            CDRDataClass cdrDataClass = new CDRDataClass(
                    random.nextBoolean() ? "01" : "02",
                    generatePhoneNumber(),
                    generatePhoneNumber(),
                    generateRandomTimestamp(),
                    generateRandomTimestamp()
            );
            cdrDataClassList.add(cdrDataClass);
        }
        return cdrDataClassList;
    }

    private String generatePhoneNumber() {
        return "79" + (random.nextInt(900000000));
    }

    private String generateRandomTimestamp() {
        return "2025-02-" + (random.nextInt(28) + 1) + "T" +
                String.format("%02d:%02d:%02d", random.nextInt(24), random.nextInt(60), random.nextInt(60));
    }

}


/** CDR Example
 *  02,79876543221, 79123456789, 2025-02-10T14:56:12, 2025-02-10T14:58:20
 *  CDR-запись включает в себя следующие данные:
 *  тип вызова (01 - исходящие, 02 - входящие);
 *  номер абонента, инициирующего звонок;
 *  номер абонента, принимающего звонок;
 * дата и время начала звонка (ISO 8601);
 *  дата и время окончания звонка (ISO 8601);
 * CDR-отчет представляет собой набор CDR-записей.
 * Разделитель данных – запятая;
 * разделитель записей – перенос строки;
 * данные обязательно формируются в хронологическом порядке;
 * в рамках задания CDR-отчет может быть обычным txt\csv;
 *
 */