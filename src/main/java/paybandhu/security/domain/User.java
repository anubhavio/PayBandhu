package paybandhu.security.domain;

import jakarta.persistence.*;
import lombok.*;
import paybandhu.agent.domain.Agent;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mobile_number", nullable = false, unique = true, length = 15)
    private String mobileNumber;


    @Column(name = "password_hash", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private boolean enabled;

    @OneToOne
    @JoinColumn(name = "agent_id")
    private Agent agent;

}
