package paybandhu.agent.domain;

public enum AgentRejectionReason {
    PAN_MISMATCH("Pan Mismatch"),
    AADHAAR_MISMATCH("Aadhaar Mismatch"),
    INVALID_DOCUMENT("Invalid Document"),
    FRAUD_SUSPECTED("Fraud Suspected"),
    KYC_FAILED("KYC Failed"),
    OTHER("Other");

   private final String value;

    AgentRejectionReason(String value) {
    this.value = value;
    }
    public String getValue(){
        return value;
    }
}
