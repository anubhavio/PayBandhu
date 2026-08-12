package paybandhu.retailer.service;

import org.springframework.stereotype.Service;
import paybandhu.agent.domain.RegistrationLocation;
import paybandhu.retailer.api.request.RetailerDocumentRequest;
import paybandhu.retailer.api.request.RetailerRegistrationRequest;
import paybandhu.retailer.api.request.RetailerRejectionReason;
import paybandhu.retailer.api.response.RetailerDocumentResponse;
import paybandhu.retailer.api.response.RetailerRegistrationResponse;
import paybandhu.retailer.api.response.RetailerVerificationResponse;

import java.util.List;

@Service
public class RetailerServiceImp implements RetailerService{

    @Override
    public RetailerRegistrationResponse registerRetailer(RetailerRegistrationRequest request, String ipAddress, RegistrationLocation registrationLocation) {
        return null;
    }

    @Override
    public RetailerDocumentResponse uploadDocuments(List<RetailerDocumentRequest> documentRequests, Long retailerId) {
        return null;
    }

    @Override
    public RetailerVerificationResponse verifyRetailer(Long retailerId) {
        return null;
    }

    @Override
    public RetailerVerificationResponse rejectRetailer(Long retailerId, RetailerRejectionReason reason) {
        return null;
    }
}
