package paybandhu.agent.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "agent_applications")
@Entity
public class AgentApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "application_number", length = 30, unique = true, nullable = false)
    private String applicationNumber;

    @Column(name = "first_name", length = 50,  nullable = false)
    private String firstName;

    @Column(name = "middle_name", length = 50)
    private String middleName;

    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    @Column(name = "mobile_number", length = 15, nullable = false, unique = true)
    private String mobileNumber;

    @Column(name = "email_address", length = 150, unique = true, nullable = false)
    private String emailAddress;

    @Column(name = "aadhaar_number", length = 15, unique = true, nullable = false)
    private String aadhaarNumber;

    @Column(name = "pan_number",length = 50, unique = true, nullable = false)
    private String panNumber;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @Column(name = "registration_ip" , nullable = false)
    private String registrationIp;

    @Embedded
    private RegistrationLocation registrationLocation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AgentApplicationStatus status;

    @OneToOne(mappedBy = "agentApplication")
    private AgentKyc agentKyc;

    @Enumerated(EnumType.STRING)
    @Column(name = "rejection_reason")
    private AgentRejectionReason agentRejectionReason;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

}
