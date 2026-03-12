package io.opencti.types.store;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * External Reference type.
 * Original file: opencti-platform/opencti-graphql/src/types/store.d.ts
 */
public class ExternalReference {
    private String sourceName;
    private String description;
    private String url;
    private Map<String, String> hashes;
    private String externalId;

    @JsonProperty("source_name")
    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getHashes() {
        return hashes;
    }

    public void setHashes(Map<String, String> hashes) {
        this.hashes = hashes;
    }

    @JsonProperty("external_id")
    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }
}

