package io.opencti.types.stix.sco;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.types.stix.common.*;

import java.util.List;

/**
 * STIX Bank Account object.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sco.d.ts
 */
public class StixBankAccount extends StixCyberObject {
    public static final String TYPE = "bank-account";

    private String iban;
    private String bic;
    @JsonProperty("account_number")
    private String accountNumber;
    private String description;
    private Integer score;
    private List<String> labels;
    @JsonProperty("created_by_ref")
    private String createdByRef;
    @JsonProperty("object_marking_refs")
    private List<StixId> objectMarkingRefs;

    public StixBankAccount() {
        this.type = TYPE;
    }

    public String getIban() { return iban; }
    public void setIban(String iban) { this.iban = iban; }
    public String getBic() { return bic; }
    public void setBic(String bic) { this.bic = bic; }
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }
    public List<String> getLabels() { return labels; }
    public void setLabels(List<String> labels) { this.labels = labels; }
    public String getCreatedByRef() { return createdByRef; }
    public void setCreatedByRef(String createdByRef) { this.createdByRef = createdByRef; }
    public List<StixId> getObjectMarkingRefs() { return objectMarkingRefs; }
    public void setObjectMarkingRefs(List<StixId> objectMarkingRefs) { this.objectMarkingRefs = objectMarkingRefs; }
}
