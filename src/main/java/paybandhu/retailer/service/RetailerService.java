package paybandhu.retailer.service;

import org.springframework.stereotype.Service;
import paybandhu.agent.domain.RegistrationLocation;
import paybandhu.retailer.api.request.RetailerDocumentRequest;
import paybandhu.retailer.api.request.RetailerRegistrationRequest;
import paybandhu.retailer.api.request.RetailerRejectionReason;
import paybandhu.retailer.api.response.RetailerDocumentResponse;
import paybandhu.retailer.api.response.RetailerRegistrationResponse;
import paybandhu.retailer.api.response.RetailerVerificationResponse;
import paybandhu.retailer.domain.RetailerDocument;

import java.util.List;


public interface RetailerService {

    RetailerRegistrationResponse registerRetailer(RetailerRegistrationRequest request, String ipAddress, RegistrationLocation registrationLocation);
    RetailerDocumentResponse uploadDocuments(List<RetailerDocumentRequest> documentRequests, Long retailerId);
    RetailerVerificationResponse verifyRetailer(Long retailerId);
    RetailerVerificationResponse rejectRetailer(Long retailerId , RetailerRejectionReason reason);
}
