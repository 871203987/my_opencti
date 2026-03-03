package io.opencti.common.types.stix.sdo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.common.types.stix.StixId;

import java.util.List;

/**
 * STIX Opinion type.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sdo.d.ts
 * Original type:
 * export interface StixOpinion extends StixContainer {
 *   explanation: string;
 *   authors: Array<string>;
 *   opinion: 'strongly-disagree' | 'disagree' | 'neutral' | 'agree' | 'strongly-agree';
 *   extensions: {
 *     [STIX_EXT_OCTI]: StixOpinionExtension;
 *   };
 * }
 */
public class StixOpinion extends StixContainer {
    public static final String TYPE = "opinion";

    public static final String OPINION_STRONGLY_DISAGREE = "strongly-disagree";
    public static final String OPINION_DISAGREE = "disagree";
    public static final String OPINION_NEUTRAL = "neutral";
    public static final String OPINION_AGREE = "agree";
    public static final String OPINION_STRONGLY_AGREE = "strongly-agree";

    private String explanation;
    private List<String> authors;
    private String opinion;
    private StixOpinionExtension opinionExtension;

    public StixOpinion() {
        this.type = TYPE;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public StixOpinionExtension getOpinionExtension() {
        return opinionExtension;
    }

    public void setOpinionExtension(StixOpinionExtension opinionExtension) {
        this.opinionExtension = opinionExtension;
    }
}
