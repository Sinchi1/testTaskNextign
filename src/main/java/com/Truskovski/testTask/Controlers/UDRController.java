package com.Truskovski.testTask.Controlers;

import com.Truskovski.testTask.Objects.DTO.UDRResponse;
import com.Truskovski.testTask.Service.UDRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller предоставляющий доступ к работе с UDR отчётами.
 * Реализованы два энд-поинта. Один возвращает UDR для одного абонента, другой для всех за определённый период времени.
 */
@RestController
@RequestMapping("/api/udr")
public class UDRController {

    @Autowired
    private UDRService udrService;

    /**
     * Энд-поинт(/subscriber) POST для создания UDR отчёта для одного пользователя по номеру телефона за весь период или за определённый год и месяц.
     * @param msisdn  номер телефона абонента.
     * @param year год за который будет предоставляться UDR отчёт.
     * @param month  месяц за который будет предоставляться UDR отчёт.
     * @param entirePeriod  флаг, который ставится если пользователю нужен отчёт за всё время. При значении true исключает тэги year, month.
     * @return Возвращает сообщение об успешности запроса, которое включает в себя UDR отчёт, либо ошибку.
     */
    @GetMapping("/subscriber")
    public ResponseEntity<?> getUDRForCaller(
            @RequestParam String msisdn,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            @RequestParam(defaultValue = "false") boolean entirePeriod) {
        if ((year == null || month == null) && !entirePeriod) {
            return ResponseEntity.badRequest()
                    .body("В запросе для получения UDR нужно указать либо год и месяц, либо entirePeriod = true ");
        }
        if (year != null && month != null && (month < 1 || month > 12)) {
            return ResponseEntity.badRequest()
                    .body("Ошибка, введите номер месяца от 1 до 12");
        }
        String monthString = (year != null && month != null) ? String.format("%d-%02d", year, month) : null;
        UDRResponse response = udrService.getUDRForOneCaller(msisdn, monthString, entirePeriod);
        return ResponseEntity.ok(response);
    }


    /**
     * Энд-поинт(/all) GET для создания UDR отчёта для всех пользователей за период, который определяется входными параметрами.
     * @param year  год, за который создаётся UDR.
     * @param month  месяц, за который создаётся UDR.
     * @return  Возвращает сообщение об успешности запроса, которое включает в себя UDR отчёт, либо ошибку.
     */
    @GetMapping("/all")
    public ResponseEntity<?> getUDRForAllSubscribers(
            @RequestParam Integer year,
            @RequestParam Integer month) {
        try {
            if (month < 1 || month > 12) {
                return ResponseEntity.badRequest()
                        .body("Ошибка, введите номер месяца от 1 до 12");
            }
            String monthString = String.format("%d-%02d", year, month);
            List<UDRResponse> responses = udrService.getUDRForAllCallers(monthString);
            return ResponseEntity.ok(responses);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Упс.. Кажется при вводе вы использовали неверный формат");
        }
    }
}