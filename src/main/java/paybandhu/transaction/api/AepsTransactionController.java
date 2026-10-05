package paybandhu.transaction.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import paybandhu.transaction.api.dto.AepsBalanceEnquiryRequest;
import paybandhu.transaction.api.dto.AepsTransactionResponse;
import paybandhu.transaction.domain.AepsTransaction;
import paybandhu.transaction.service.AepsTransactionService;

@RestController
@RequestMapping("/api/v1/aeps")
@RequiredArgsConstructor
public class AepsTransactionController {

    private final AepsTransactionService aepsTransactionService;

    @PostMapping("/balance-enquiry")
    public ResponseEntity<AepsTransactionResponse> balanceEnquiry(
            @Valid @RequestBody AepsBalanceEnquiryRequest request
    ) {

        return ResponseEntity.ok(
                aepsTransactionService.balanceEnquiry(request)
        );
    }
}
