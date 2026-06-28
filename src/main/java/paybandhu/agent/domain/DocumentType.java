package paybandhu.agent.domain;

import lombok.Getter;

@Getter
public enum DocumentType {

    PAN_CARD("Pan card"),
    AADHAAR_FRONT("Aadhaar front "),
    AADHAAR_BACK("Aadhaar back"),
    AGENT_PHOTO("Agent photo"),
    SHOP_PHOTO("Shop photo"),
    BANK_PASSBOOK("Bank passbook");


    private final String value;

    DocumentType(String value){
        this.value = value;
    }
}
