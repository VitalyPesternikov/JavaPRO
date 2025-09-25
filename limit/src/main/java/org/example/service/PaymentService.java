package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.PaymentRequestDto;
import org.example.dto.PaymentResponseDto;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentService {
    public PaymentResponseDto executePayment(PaymentRequestDto paymentRequest) {
        log.info("Выполняется запрос в сервис платежей");
        final var rand = Math.random();
        if (rand > 0.5) {
            return new PaymentResponseDto(true, "Платёж успешно прошёл");
        } else {
            return new PaymentResponseDto(false, "Ошибка при попытке провести платёж");
        }
    }
}
