package io.opencti.types.stix.sco;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.types.stix.common.*;

import java.util.List;

/**
 * STIX Payment Card object.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sco.d.ts
 */
public class StixPaymentCard extends StixCyberObject {
    public static final String TYPE = "payment-card";

    @JsonProperty("card_number")
    private String cardNumber;
    @JsonProperty("expiration_date")
    private String expirationDate;
    private Integer cvv;
    @JsonProperty("holder_name")
    private String holderName;
    private String description;
    private Integer score;
    private List<String> labels;
    @JsonProperty("created_by_ref")
    private String createdByRef;
    @JsonProperty("object_marking_refs")
    private List<StixId> objectMarkingRefs;

    public StixPaymentCard() {
        this.type = TYPE;
    }

    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }
    public String getExpirationDate() { return expirationDate; }
    public void setExpirationDate(String expirationDate) { this.expirationDate = expirationDate; }
    public Integer getCvv() { return cvv; }
    public void setCvv(Integer cvv) { this.cvv = cvv; }
    public String getHolderName() { return holderName; }
    public void setHolderName(String holderName) { this.holderName = holderName; }
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
