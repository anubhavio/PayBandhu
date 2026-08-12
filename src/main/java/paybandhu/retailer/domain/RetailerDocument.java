package paybandhu.retailer.domain;

import jakarta.persistence.*;
import lombok.*;
import paybandhu.agent.domain.DocumentStatus;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "retailer_documents")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RetailerDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "retailer_id", nullable = false)
    private Retailer retailer;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_url", nullable = false)
    private String fileUrl;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "uploaded_at", nullable = false)
    private LocalDateTime uploadedAt;

    @Enumerated(EnumType.STRING)
    private DocumentStatus documentStatus;


}
