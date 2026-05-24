package paybandhu.agent.domain;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "agents")
public class Agent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "agent_no", length = 20, unique = true, nullable = false)
    private String agentCode;

    @Column(name = "pan_number",length = 50, unique = true, nullable = false)
    private String panNumber;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "middle_name", length = 50)
    private String middleName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "mobile_number", length = 50, nullable = false, unique = true)
    private String mobileNumber;

    @Column(name = "email_address", length = 50, unique = true, nullable = false)
    private String emailAddress;

    @Column(name ="aadhaar_number", length = 50, unique = true, nullable = false)
    private String aadhaarNumber;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AgentStatus status;

    @Column(name = "registration_ip" , nullable = false)
    private String registrationIp;

    @CreatedDate
    private LocalDateTime createdAt;

    private LocalDateTime verifiedAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
