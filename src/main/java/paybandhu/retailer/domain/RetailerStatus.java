package paybandhu.retailer.domain;

import lombok.Getter;

@Getter
public enum RetailerStatus {
    REGISTERED,
    PENDING_REVIEW,
    ACTIVE,
    REJECTED,
    BLOCKED
}
