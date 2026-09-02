package paybandhu.retailer.service;


import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import paybandhu.agent.domain.RegistrationLocation;
import paybandhu.retailer.api.request.RetailerDocumentRequest;
import paybandhu.retailer.api.request.RetailerRegistrationRequest;
import paybandhu.retailer.api.request.RetailerRejectionReason;
import paybandhu.retailer.api.response.RetailerDocumentResponse;
import paybandhu.retailer.api.response.RetailerResponse;
import paybandhu.retailer.api.response.RetailerUpdateRequest;
import paybandhu.retailer.api.response.RetailerVerificationResponse;
import paybandhu.retailer.domain.RetailerBlockReason;
import paybandhu.retailer.domain.RetailerDocument;
import org.springframework.data.domain.Page;


import java.util.List;


public interface RetailerService {

    RetailerResponse register(RetailerRegistrationRequest request, String ipAddress, RegistrationLocation registrationLocation);

    RetailerResponse getById(Long retailerId);

    RetailerResponse getByCode(String retailerCode);

    Page<RetailerResponse> getCurrentAgentRetailers(Pageable pageable);

    RetailerResponse update(Long retailerCode, RetailerUpdateRequest retailerUpdateRequest, RegistrationLocation registrationLocation);

    void submitForReview(Long retailerId);

    void approve(Long retailerId);

    void reject(Long retailerId, RetailerRejectionReason reason);

    void block(
            Long retailerId,
            RetailerBlockReason reason
    );
}
