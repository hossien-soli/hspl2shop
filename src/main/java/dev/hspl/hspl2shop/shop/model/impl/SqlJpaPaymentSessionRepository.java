package dev.hspl.hspl2shop.shop.model.impl;

import dev.hspl.hspl2shop.common.exception.EntityVersionMismatchException;
import dev.hspl.hspl2shop.shop.model.impl.jpa.entity.PaymentSessionJpaEntity;
import dev.hspl.hspl2shop.shop.model.impl.jpa.repository.PaymentSessionJpaRepository;
import dev.hspl.hspl2shop.shop.model.write.entity.PaymentSession;
import dev.hspl.hspl2shop.shop.model.write.repository.PaymentSessionRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@NullMarked
public class SqlJpaPaymentSessionRepository implements PaymentSessionRepository {
    private final PaymentSessionJpaRepository jpaRepository;

    @Override
    public Optional<PaymentSession> find(UUID id) {
        return jpaRepository.findById(id).map(jpaEntity -> PaymentSession.existingSession(
                jpaEntity.getId(),
                jpaEntity.getUserId(),
                jpaEntity.getTargetOrderId(),
                jpaEntity.getPaymentWebServiceAuthority(),
                jpaEntity.getPriceToPay(),
                jpaEntity.getState(),
                jpaEntity.getPaymentBankingReference(),
                jpaEntity.getPaymentCardPan(),
                jpaEntity.getPaymentCardHash(),
                jpaEntity.getCreatedAt(),
                jpaEntity.getPaidAt(),
                jpaEntity.getVerifiedAt(),
                jpaEntity.getFailedAt(),
                jpaEntity.getVersion()
        ));
    }

    @Override
    public Optional<LocalDateTime> findUserLastSessionCreatedAt(UUID userId) {
        return jpaRepository.findLastSessionsByUserId(userId, Limit.of(1)).stream().findFirst();
    }
    
    @Override
    public void save(PaymentSession session) throws EntityVersionMismatchException {
        try {
            jpaRepository.saveAndFlush(PaymentSessionJpaEntity.builder()
                    .id(session.getId())
                    .userId(session.getUserId())
                    .targetOrderId(session.getTargetOrderId())
                    .paymentWebServiceAuthority(session.getPaymentWebServiceAuthority())
                    .priceToPay(session.getPriceToPay())
                    .state(session.getState())
                    .paymentBankingReference(session.getPaymentBankingReference())
                    .paymentCardPan(session.getPaymentCardPan())
                    .paymentCardHash(session.getPaymentCardHash())
                    .createdAt(session.getCreatedAt())
                    .paidAt(session.getPaidAt())
                    .verifiedAt(session.getVerifiedAt())
                    .failedAt(session.getFailedAt())
                    .version(session.getVersion())
                    .build());
        } catch (OptimisticLockingFailureException exception) {
            throw new EntityVersionMismatchException(PaymentSession.class.getSimpleName(), session.getId().toString());
        }
    }
}
