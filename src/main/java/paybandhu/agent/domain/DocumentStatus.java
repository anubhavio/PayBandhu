package paybandhu.agent.domain;

public enum DocumentStatus {
    IN_REVIEW("Your document sent for review"),
        REVIEW_PENDING("Review pending"),
     REJECTED("Document rejected");

    private final String value;

    DocumentStatus(String value){
        this.value = value;
    }
    public String getValue(){
        return value;
    }
}
