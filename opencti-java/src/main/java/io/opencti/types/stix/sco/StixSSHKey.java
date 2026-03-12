package io.opencti.types.stix.sco;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.types.stix.common.*;

import java.util.List;

/**
 * STIX SSH Key object.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sco.d.ts
 */
public class StixSSHKey extends StixCyberObject {
    public static final String TYPE = "ssh-key";

    @JsonProperty("key_type")
    private String keyType;
    @JsonProperty("public_key")
    private String publicKey;
    @JsonProperty("fingerprint_sha256")
    private String fingerprintSha256;
    @JsonProperty("fingerprint_md5")
    private String fingerprintMd5;
    @JsonProperty("key_length")
    private Integer keyLength;
    private String comment;
    private String created;
    @JsonProperty("expiration_date")
    private String expirationDate;

    public StixSSHKey() {
        this.type = TYPE;
    }

    public String getKeyType() { return keyType; }
    public void setKeyType(String keyType) { this.keyType = keyType; }
    public String getPublicKey() { return publicKey; }
    public void setPublicKey(String publicKey) { this.publicKey = publicKey; }
    public String getFingerprintSha256() { return fingerprintSha256; }
    public void setFingerprintSha256(String fingerprintSha256) { this.fingerprintSha256 = fingerprintSha256; }
    public String getFingerprintMd5() { return fingerprintMd5; }
    public void setFingerprintMd5(String fingerprintMd5) { this.fingerprintMd5 = fingerprintMd5; }
    public Integer getKeyLength() { return keyLength; }
    public void setKeyLength(Integer keyLength) { this.keyLength = keyLength; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    public String getCreated() { return created; }
    public void setCreated(String created) { this.created = created; }
    public String getExpirationDate() { return expirationDate; }
    public void setExpirationDate(String expirationDate) { this.expirationDate = expirationDate; }
}
