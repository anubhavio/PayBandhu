package paybandhu.transaction.api.dto;

import lombok.Builder;
import lombok.Getter;
import paybandhu.transaction.domain.AepsTransactionStatus;
import paybandhu.transaction.domain.AepsTransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class AepsTransactionResponse {

    private String transactionReference;

    private String agentCode;

    private AepsTransactionType type;

    private AepsTransactionStatus status;

    private String bankCode;

    private String providerReference;

    private BigDecimal customerBankBalance;


    private LocalDateTime createdAt;

    private LocalDateTime completedAt;
}
