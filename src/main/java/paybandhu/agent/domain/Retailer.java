package paybandhu.agent.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "retailers")
public class Retailer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "retailer_no", length = 30, unique = true, nullable = false)
    private String retailerCode;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "middle_name", length = 50)
    private String middleName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "mobile_number", length = 50, nullable = false, unique = true)
    private String mobileNumber;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @Column(name = "Date_Of_Birth", nullable = false)
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "aadhaar_number", length = 50, unique = true, nullable = false)
    private String aadhaarNumber;

    @Column(name = "pan_number", length = 50, unique = true,nullable = false)
    private String panNumber;

    @OneToMany(
            mappedBy = "retailer",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<RetailerDocument> documents = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RetailerStatus status;

    @Column(name = "registration_ip", nullable = false)
    private String registrationIp;

    @Embedded
    private RegistrationLocation registrationLocation;

    @Enumerated(EnumType.STRING)
    @Column(name = "rejection_reason")
    private RetailerRejectionReason retailerRejectionReason;

    @CreatedDate
    private LocalDateTime createdAt;

    private LocalDateTime verifiedAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public void reject(RetailerRejectionReason reason){
        this.status = RetailerStatus.REJECTED;
        this.retailerRejectionReason =  reason;
        this.verifiedAt = LocalDateTime.now();
    }
}
