package org.example.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.LimitDto;
import org.example.dto.PaymentRequestDto;
import org.example.dto.PaymentResponseDto;
import org.example.exception.LimitReachedException;
import org.example.exception.PaymentServiceException;
import org.example.model.LimitEntity;
import org.example.repository.LimitRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Service
public class LimitService {
    private final LimitRepository limitRepository;
    private final PaymentService paymentService;

    @Value("${limits.standard}")
    private BigDecimal amount;

    public LimitService(LimitRepository limitRepository, PaymentService paymentService) {
        this.limitRepository = limitRepository;
        this.paymentService = paymentService;
    }

    @Transactional
    public LimitDto getLimitByUserId(Long userId) {
        return limitEntityToLimitDto(getLimitByUserIdOrCreate(userId));
    }

    @Transactional
    public LimitDto executePaymentRequest(PaymentRequestDto paymentRequestDto) {
        LimitEntity limitEntity = getLimitByUserIdOrCreate(paymentRequestDto.userId());
        if (limitEntity.getLimitAmount().compareTo(paymentRequestDto.amount()) < 0) {
            throw new LimitReachedException("Достигнут лимит средств у пользователя с id = " + paymentRequestDto.userId());
        }
        PaymentResponseDto paymentResponseDto = paymentService.executePayment(paymentRequestDto);
        if (paymentResponseDto.isSucceed()) {
            limitEntity.setLimitAmount(limitEntity.getLimitAmount().subtract(paymentRequestDto.amount()));
            limitRepository.save(limitEntity);
            log.info("Лимит пользователя с id = {} уменьшен на сумму {}", paymentRequestDto.userId(), limitEntity.getLimitAmount());
            return limitEntityToLimitDto(limitEntity);
        } else {
            throw new PaymentServiceException(paymentResponseDto.message());
        }
    }

    private LimitEntity getLimitByUserIdOrCreate(Long userId) {
        Optional<LimitEntity> limitOptional = limitRepository.findByUserId(userId);
        if (limitOptional.isPresent()) {
            return limitOptional.get();
        }
        LimitEntity newLimit = new LimitEntity();
        newLimit.setUserId(userId);
        newLimit.setLimitAmount(amount);
        limitRepository.save(newLimit);
        log.info("Добавлен новый лимит для пользователя с id = {}", userId);
        return newLimit;
    }

    private LimitDto limitEntityToLimitDto(LimitEntity limitEntity) {
        return new LimitDto(limitEntity.getId(), limitEntity.getUserId(), limitEntity.getLimitAmount());
    }
}
