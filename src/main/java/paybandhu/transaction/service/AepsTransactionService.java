package paybandhu.transaction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import paybandhu.agent.domain.Agent;
import paybandhu.integration.aeps.AepsProvider;
import paybandhu.integration.aeps.AepsProviderRequest;
import paybandhu.integration.aeps.AepsProviderResponse;
import paybandhu.security.service.CurrentUserService;
import paybandhu.transaction.api.dto.AepsBalanceEnquiryRequest;
import paybandhu.transaction.api.dto.AepsTransactionResponse;
import paybandhu.transaction.domain.AepsTransaction;
import paybandhu.transaction.domain.AepsTransactionStatus;
import paybandhu.transaction.domain.AepsTransactionType;
import paybandhu.transaction.repository.AepsTransactionRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AepsTransactionService {

    private final AepsTransactionRepository aepsTransactionRepository;
    private final CurrentUserService currentUserService;
    private final AepsProvider aepsProvider;

    @Transactional
    public AepsTransactionResponse balanceEnquiry(
            AepsBalanceEnquiryRequest request
    ) {

        Agent agent = currentUserService.getCurrentUser().getAgent();

        if (agent == null) {
            throw new IllegalStateException(
                    "Current user is not associated with an agent"
            );
        }

        AepsTransaction transaction = AepsTransaction.builder()
                .transactionReference(generateTransactionReference())
                .agent(agent)
                .type(AepsTransactionType.BALANCE_ENQUIRY)
                .status(AepsTransactionStatus.INITIATED)
                .customerAadhaar(request.getAadhaarNumber())
                .bankCode(request.getBankCode())
                .createdAt(LocalDateTime.now())
                .build();

        aepsTransactionRepository.save(transaction);

        transaction.setStatus(AepsTransactionStatus.PROCESSING);
        aepsTransactionRepository.save(transaction);

        AepsProviderRequest providerRequest =
                AepsProviderRequest.builder()
                        .transactionReference(
                                transaction.getTransactionReference()
                        )
                        .aadhaarNumber(request.getAadhaarNumber())
                        .bankCode(request.getBankCode())
                        .build();

        AepsProviderResponse providerResponse =
                aepsProvider.balanceEnquiry(providerRequest);

        if ("SUCCESS".equals(providerResponse.getStatus())) {

            transaction.setStatus(AepsTransactionStatus.SUCCESS);
            transaction.setProviderReference(
                    providerResponse.getProviderReference()
            );
            transaction.setCompletedAt(LocalDateTime.now());

        } else {

            transaction.setStatus(AepsTransactionStatus.FAILED);
        }

        aepsTransactionRepository.save(transaction);

        return   AepsTransactionResponse.builder()
                .transactionReference(transaction.getTransactionReference())
                .agentCode(transaction.getAgent().getAgentCode())
                .type(transaction.getType())
                .status(transaction.getStatus())
                .bankCode(transaction.getBankCode())
                .providerReference(transaction.getProviderReference())
                .customerBankBalance(providerResponse.getBalance())
                .createdAt(transaction.getCreatedAt())
                .completedAt(transaction.getCompletedAt())
                .build();

    }
    private String generateTransactionReference() {

        return "TXN-" +
                UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase();
    }
}
