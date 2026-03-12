package io.opencti.types.stix.sco;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.types.stix.common.*;

import java.util.List;

/**
 * STIX Media Content object.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sco.d.ts
 */
public class StixMediaContent extends StixCyberObject {
    public static final String TYPE = "media-content";

    private String title;
    private String description;
    private String content;
    @JsonProperty("media_category")
    private String mediaCategory;
    private String url;
    @JsonProperty("publication_date")
    private String publicationDate;
    private Integer score;
    private List<String> labels;
    @JsonProperty("created_by_ref")
    private String createdByRef;
    @JsonProperty("object_marking_refs")
    private List<StixId> objectMarkingRefs;

    public StixMediaContent() {
        this.type = TYPE;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getMediaCategory() { return mediaCategory; }
    public void setMediaCategory(String mediaCategory) { this.mediaCategory = mediaCategory; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public String getPublicationDate() { return publicationDate; }
    public void setPublicationDate(String publicationDate) { this.publicationDate = publicationDate; }
    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }
    public List<String> getLabels() { return labels; }
    public void setLabels(List<String> labels) { this.labels = labels; }
    public String getCreatedByRef() { return createdByRef; }
    public void setCreatedByRef(String createdByRef) { this.createdByRef = createdByRef; }
    public List<StixId> getObjectMarkingRefs() { return objectMarkingRefs; }
    public void setObjectMarkingRefs(List<StixId> objectMarkingRefs) { this.objectMarkingRefs = objectMarkingRefs; }
}
