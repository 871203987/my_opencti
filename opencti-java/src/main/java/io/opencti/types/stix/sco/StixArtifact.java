package io.opencti.types.stix.sco;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.types.stix.common.*;

import java.util.List;
import java.util.Map;

/**
 * STIX Artifact object.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sco.d.ts
 * Original type:
 * export interface StixArtifact extends StixCyberObject {
 *   mime_type: string; // optional
 *   payload_bin: string; // optional
 *   url: string; // optional
 *   hashes: { [k: string]: string }; // optional
 *   encryption_algorithm: string; // optional
 *   decryption_key: string; // optional
 *   extensions: {
 *     [STIX_EXT_OCTI]: StixOpenctiExtension;
 *     [STIX_EXT_OCTI_SCO]: ArtifactExtension;
 *   };
 * }
 */
public class StixArtifact extends StixCyberObject {
    public static final String TYPE = "artifact";

    @JsonProperty("mime_type")
    private String mimeType;
    @JsonProperty("payload_bin")
    private String payloadBin;
    private String url;
    private Map<String, String> hashes;
    @JsonProperty("encryption_algorithm")
    private String encryptionAlgorithm;
    @JsonProperty("decryption_key")
    private String decryptionKey;
    @JsonProperty("additional_names")
    private List<String> additionalNames;

    public StixArtifact() {
        this.type = TYPE;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getPayloadBin() {
        return payloadBin;
    }

    public void setPayloadBin(String payloadBin) {
        this.payloadBin = payloadBin;
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

    public String getEncryptionAlgorithm() {
        return encryptionAlgorithm;
    }

    public void setEncryptionAlgorithm(String encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm;
    }

    public String getDecryptionKey() {
        return decryptionKey;
    }

    public void setDecryptionKey(String decryptionKey) {
        this.decryptionKey = decryptionKey;
    }

    public List<String> getAdditionalNames() {
        return additionalNames;
    }

    public void setAdditionalNames(List<String> additionalNames) {
        this.additionalNames = additionalNames;
    }
}
