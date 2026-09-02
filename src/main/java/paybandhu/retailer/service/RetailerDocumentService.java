package paybandhu.retailer.service;

import paybandhu.retailer.api.request.RetailerDocumentRequest;
import paybandhu.retailer.api.response.RetailerDocumentResponse;

import java.util.List;

public interface RetailerDocumentService {

    List<RetailerDocumentResponse> uploadDocuments(
            Long retailerId,
            List<RetailerDocumentRequest> documentRequests
    );

    List<RetailerDocumentResponse> getDocuments(Long retailerId);

    void deleteDocument(Long retailerId, Long documentId);
}
