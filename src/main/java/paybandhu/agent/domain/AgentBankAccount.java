package paybandhu.agent.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "agent_bank_account")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public  class AgentBankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_number", length = 30, nullable = false)
    private String accountNumber;

    @Column(name = "ifsc_code", length = 15, nullable = false)
    private String ifscCode;

    @Column(name = "account_holder_name", length = 100, nullable = false)
    private String accountHolderName;

    @Column(name = "bank_name", length = 100, nullable = false)
    private String bankName;

    @Enumerated(EnumType.STRING)
    @Column(name = "verification_status", nullable = false)
    private VerificationStatus verificationStatus;

    public enum VerificationStatus {
        PENDING,
        VERIFIED,
        FAILED,
        REJECTED
    }
}