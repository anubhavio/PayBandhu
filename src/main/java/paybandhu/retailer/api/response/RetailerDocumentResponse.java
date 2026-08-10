package paybandhu.retailer.api.response;

import lombok.*;
import paybandhu.agent.domain.DocumentType;
import paybandhu.retailer.domain.RetailerStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RetailerDocumentResponse {

    private Long id;

    private String retailerCode;

    private RetailerStatus status;

    private DocumentType documentType;

    private String fileName;

    private String message;
}
