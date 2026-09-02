package paybandhu.retailer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import paybandhu.agent.domain.Address;
import paybandhu.agent.domain.AgentStatus;
import paybandhu.agent.domain.RegistrationLocation;
import paybandhu.common.Exception.DuplicateResourceException;
import paybandhu.common.Exception.ResourceNotFoundException;
import paybandhu.retailer.api.request.RetailerRegistrationRequest;
import paybandhu.retailer.api.request.RetailerRejectionReason;
import paybandhu.retailer.api.response.RetailerResponse;
import paybandhu.retailer.api.response.RetailerUpdateRequest;
import paybandhu.retailer.domain.Retailer;
import paybandhu.retailer.domain.RetailerBlockReason;
import paybandhu.retailer.domain.RetailerStatus;
import paybandhu.retailer.repository.RetailerRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RetailerServiceImp implements RetailerService{

    private final RetailerRepository retailerRepository;

    @Override
    public RetailerResponse register(RetailerRegistrationRequest request, String ipAddress, RegistrationLocation registrationLocation) {

        checkDuplicates(request);

        Address address = Address.builder()
                .state(request.getAddress().getState())
                .city(request.getAddress().getCity())
                .pinCode(request.getAddress().getPinCode())
                .streetAddress(request.getAddress().getStreetAddress())
                .build();

        Retailer retailer = Retailer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .middleName(request.getMiddleName())
                .mobileNumber(request.getMobileNumber())
                .emailAddress(request.getEmailAddress())
                .aadhaarNumber(request.getAadhaarNumber())
                .panNumber(request.getPanNumber())
                .gender(request.getGender())
                .dateOfBirth(request.getDateOfBirth())
                .address(address)
                .retailerCode("RET-"+ UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .status(RetailerStatus.REGISTERED)
                .registrationIp(ipAddress)
                .registrationLocation(request.getRegistrationLocation())
                .build();

        Retailer saved = retailerRepository.save(retailer);
        return RetailerResponse.builder()
                .id(saved.getId())
                .retailerCode(saved.getRetailerCode())
                .status(saved.getStatus())
                .message("Retailer registered successfully, Pending onboarding")
                .build();
    }

    @Override
    public RetailerResponse getById(Long retailerId) {

        Retailer retailer = retailerRepository.findById(retailerId)
                .orElseThrow(() -> new ResourceNotFoundException("" +
                        "Retailer not found with id: " + retailerId));
        return  mapToResponse(retailer);
    }

    @Override
    public RetailerResponse getByCode(String retailerCode) {
        return null;
    }

    @Override
    public Page<RetailerResponse> getCurrentAgentRetailers(Pageable pageable) {
        return null;
    }

    @Override
    public RetailerResponse update(Long retailerCode, RetailerUpdateRequest retailerUpdateRequest, RegistrationLocation registrationLocation) {
        return null;
    }

    @Override
    public void submitForReview(Long retailerId) {

    }

    @Override
    public void approve(Long retailerId) {

    }

    @Override
    public void reject(Long retailerId, RetailerRejectionReason reason) {

    }

    @Override
    public void block(Long retailerId, RetailerBlockReason reason) {

    }

    private void checkDuplicates(RetailerRegistrationRequest request){

        Optional<Retailer> existing = retailerRepository.findByMobileNumber(request.getMobileNumber());

        if(existing.isPresent()){
            Retailer retailer = existing.get();
            switch (retailer.getStatus()){
                case REJECTED -> throw new DuplicateResourceException(
                        "Application was rejected. Reason: " + retailer.getRetailerRejectionReason()
                );
                case ACTIVE -> throw new DuplicateResourceException(
                        "Mobile number is already registered"
                );
                default -> throw new DuplicateResourceException(
                        "Application in progress"
                );
            }
        }
        if(retailerRepository.findByAadhaarNumber(request.getAadhaarNumber()).isPresent()){
            throw new DuplicateResourceException("Aadhaar number already registered");
        }
        if(retailerRepository.findByPanNumber(request.getPanNumber()).isPresent()){
            throw new DuplicateResourceException("Pan number already registered");
        }


    }

    private RetailerResponse mapToResponse(Retailer retailer){
        return RetailerResponse.builder()
                .id(retailer.getId())
                .retailerCode(retailer.getRetailerCode())
                .firstName(retailer.getFirstName())
                .mobileNumber(retailer.getMobileNumber())
                .status(retailer.getStatus())
                .build();
    }

}
