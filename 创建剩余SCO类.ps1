# 创建剩余SCO类的PowerShell脚本
# 需要创建的类：
# - StixCryptocurrencyWallet
# - StixCryptographicKey
# - StixHostname
# - StixText
# - StixUserAgent
# - StixBankAccount
# - StixCredential
# - StixMediaContent
# - StixPaymentCard
# - StixPersona
# - StixPhoneNumber
# - StixSSHKey
# - StixTrackingNumber

$basePath = "d:\project\open_cti\opencti\opencti-java\src\main\java\io\opencti\types\stix\sco"

# StixCryptocurrencyWallet
$walletContent = @'
package io.opencti.types.stix.sco;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.types.stix.common.*;

import java.util.List;

/**
 * STIX Cryptocurrency Wallet object.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sco.d.ts
 */
public class StixCryptocurrencyWallet extends StixCyberObject {
    public static final String TYPE = "cryptocurrency-wallet";

    private String value;
    private String description;
    private Integer score;
    private List<String> labels;
    @JsonProperty("created_by_ref")
    private String createdByRef;
    @JsonProperty("object_marking_refs")
    private List<String> objectMarkingRefs;

    public StixCryptocurrencyWallet() {
        this.type = TYPE;
    }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }
    public List<String> getLabels() { return labels; }
    public void setLabels(List<String> labels) { this.labels = labels; }
    public String getCreatedByRef() { return createdByRef; }
    public void setCreatedByRef(String createdByRef) { this.createdByRef = createdByRef; }
    public List<String> getObjectMarkingRefs() { return objectMarkingRefs; }
    public void setObjectMarkingRefs(List<String> objectMarkingRefs) { this.objectMarkingRefs = objectMarkingRefs; }
}
'@
Set-Content -Path "$basePath\StixCryptocurrencyWallet.java" -Value $walletContent -Encoding UTF8
Write-Host "Created: StixCryptocurrencyWallet.java"

# StixCryptographicKey
$keyContent = @'
package io.opencti.types.stix.sco;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.types.stix.common.*;

import java.util.List;

/**
 * STIX Cryptographic Key object.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sco.d.ts
 */
public class StixCryptographicKey extends StixCyberObject {
    public static final String TYPE = "cryptographic-key";

    private String value;
    private String description;
    private Integer score;
    private List<String> labels;
    @JsonProperty("created_by_ref")
    private String createdByRef;
    @JsonProperty("object_marking_refs")
    private List<String> objectMarkingRefs;

    public StixCryptographicKey() {
        this.type = TYPE;
    }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }
    public List<String> getLabels() { return labels; }
    public void setLabels(List<String> labels) { this.labels = labels; }
    public String getCreatedByRef() { return createdByRef; }
    public void setCreatedByRef(String createdByRef) { this.createdByRef = createdByRef; }
    public List<String> getObjectMarkingRefs() { return objectMarkingRefs; }
    public void setObjectMarkingRefs(List<String> objectMarkingRefs) { this.objectMarkingRefs = objectMarkingRefs; }
}
'@
Set-Content -Path "$basePath\StixCryptographicKey.java" -Value $keyContent -Encoding UTF8
Write-Host "Created: StixCryptographicKey.java"

# StixHostname
$hostnameContent = @'
package io.opencti.types.stix.sco;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.types.stix.common.*;

import java.util.List;

/**
 * STIX Hostname object.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sco.d.ts
 */
public class StixHostname extends StixCyberObject {
    public static final String TYPE = "hostname";

    private String value;
    private String description;
    private Integer score;
    private List<String> labels;
    @JsonProperty("created_by_ref")
    private String createdByRef;
    @JsonProperty("object_marking_refs")
    private List<String> objectMarkingRefs;

    public StixHostname() {
        this.type = TYPE;
    }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }
    public List<String> getLabels() { return labels; }
    public void setLabels(List<String> labels) { this.labels = labels; }
    public String getCreatedByRef() { return createdByRef; }
    public void setCreatedByRef(String createdByRef) { this.createdByRef = createdByRef; }
    public List<String> getObjectMarkingRefs() { return objectMarkingRefs; }
    public void setObjectMarkingRefs(List<String> objectMarkingRefs) { this.objectMarkingRefs = objectMarkingRefs; }
}
'@
Set-Content -Path "$basePath\StixHostname.java" -Value $hostnameContent -Encoding UTF8
Write-Host "Created: StixHostname.java"

# StixText
$textContent = @'
package io.opencti.types.stix.sco;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.types.stix.common.*;

import java.util.List;

/**
 * STIX Text object.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sco.d.ts
 */
public class StixText extends StixCyberObject {
    public static final String TYPE = "text";

    private String value;
    private String description;
    private Integer score;
    private List<String> labels;
    @JsonProperty("created_by_ref")
    private String createdByRef;
    @JsonProperty("object_marking_refs")
    private List<String> objectMarkingRefs;

    public StixText() {
        this.type = TYPE;
    }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }
    public List<String> getLabels() { return labels; }
    public void setLabels(List<String> labels) { this.labels = labels; }
    public String getCreatedByRef() { return createdByRef; }
    public void setCreatedByRef(String createdByRef) { this.createdByRef = createdByRef; }
    public List<String> getObjectMarkingRefs() { return objectMarkingRefs; }
    public void setObjectMarkingRefs(List<String> objectMarkingRefs) { this.objectMarkingRefs = objectMarkingRefs; }
}
'@
Set-Content -Path "$basePath\StixText.java" -Value $textContent -Encoding UTF8
Write-Host "Created: StixText.java"

# StixUserAgent
$agentContent = @'
package io.opencti.types.stix.sco;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.types.stix.common.*;

import java.util.List;

/**
 * STIX User Agent object.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sco.d.ts
 */
public class StixUserAgent extends StixCyberObject {
    public static final String TYPE = "user-agent";

    private String value;
    private String description;
    private Integer score;
    private List<String> labels;
    @JsonProperty("created_by_ref")
    private String createdByRef;
    @JsonProperty("object_marking_refs")
    private List<String> objectMarkingRefs;

    public StixUserAgent() {
        this.type = TYPE;
    }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }
    public List<String> getLabels() { return labels; }
    public void setLabels(List<String> labels) { this.labels = labels; }
    public String getCreatedByRef() { return createdByRef; }
    public void setCreatedByRef(String createdByRef) { this.createdByRef = createdByRef; }
    public List<String> getObjectMarkingRefs() { return objectMarkingRefs; }
    public void setObjectMarkingRefs(List<String> objectMarkingRefs) { this.objectMarkingRefs = objectMarkingRefs; }
}
'@
Set-Content -Path "$basePath\StixUserAgent.java" -Value $agentContent -Encoding UTF8
Write-Host "Created: StixUserAgent.java"

# StixBankAccount
$bankContent = @'
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
    private List<String> objectMarkingRefs;

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
    public List<String> getObjectMarkingRefs() { return objectMarkingRefs; }
    public void setObjectMarkingRefs(List<String> objectMarkingRefs) { this.objectMarkingRefs = objectMarkingRefs; }
}
'@
Set-Content -Path "$basePath\StixBankAccount.java" -Value $bankContent -Encoding UTF8
Write-Host "Created: StixBankAccount.java"

# StixCredential
$credentialContent = @'
package io.opencti.types.stix.sco;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.types.stix.common.*;

import java.util.List;

/**
 * STIX Credential object.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sco.d.ts
 */
public class StixCredential extends StixCyberObject {
    public static final String TYPE = "credential";

    private String value;
    private String description;
    private Integer score;
    private List<String> labels;
    @JsonProperty("created_by_ref")
    private String createdByRef;
    @JsonProperty("object_marking_refs")
    private List<String> objectMarkingRefs;

    public StixCredential() {
        this.type = TYPE;
    }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }
    public List<String> getLabels() { return labels; }
    public void setLabels(List<String> labels) { this.labels = labels; }
    public String getCreatedByRef() { return createdByRef; }
    public void setCreatedByRef(String createdByRef) { this.createdByRef = createdByRef; }
    public List<String> getObjectMarkingRefs() { return objectMarkingRefs; }
    public void setObjectMarkingRefs(List<String> objectMarkingRefs) { this.objectMarkingRefs = objectMarkingRefs; }
}
'@
Set-Content -Path "$basePath\StixCredential.java" -Value $credentialContent -Encoding UTF8
Write-Host "Created: StixCredential.java"

# StixMediaContent
$mediaContent = @'
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
    private List<String> objectMarkingRefs;

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
    public List<String> getObjectMarkingRefs() { return objectMarkingRefs; }
    public void setObjectMarkingRefs(List<String> objectMarkingRefs) { this.objectMarkingRefs = objectMarkingRefs; }
}
'@
Set-Content -Path "$basePath\StixMediaContent.java" -Value $mediaContent -Encoding UTF8
Write-Host "Created: StixMediaContent.java"

# StixPaymentCard
$paymentContent = @'
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
    private List<String> objectMarkingRefs;

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
    public List<String> getObjectMarkingRefs() { return objectMarkingRefs; }
    public void setObjectMarkingRefs(List<String> objectMarkingRefs) { this.objectMarkingRefs = objectMarkingRefs; }
}
'@
Set-Content -Path "$basePath\StixPaymentCard.java" -Value $paymentContent -Encoding UTF8
Write-Host "Created: StixPaymentCard.java"

# StixPersona
$personaContent = @'
package io.opencti.types.stix.sco;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.types.stix.common.*;

import java.util.List;

/**
 * STIX Persona object.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sco.d.ts
 */
public class StixPersona extends StixCyberObject {
    public static final String TYPE = "persona";

    @JsonProperty("persona_name")
    private String personaName;
    @JsonProperty("persona_type")
    private String personaType;
    private Integer score;
    private List<String> labels;
    @JsonProperty("created_by_ref")
    private String createdByRef;
    @JsonProperty("object_marking_refs")
    private List<String> objectMarkingRefs;

    public StixPersona() {
        this.type = TYPE;
    }

    public String getPersonaName() { return personaName; }
    public void setPersonaName(String personaName) { this.personaName = personaName; }
    public String getPersonaType() { return personaType; }
    public void setPersonaType(String personaType) { this.personaType = personaType; }
    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }
    public List<String> getLabels() { return labels; }
    public void setLabels(List<String> labels) { this.labels = labels; }
    public String getCreatedByRef() { return createdByRef; }
    public void setCreatedByRef(String createdByRef) { this.createdByRef = createdByRef; }
    public List<String> getObjectMarkingRefs() { return objectMarkingRefs; }
    public void setObjectMarkingRefs(List<String> objectMarkingRefs) { this.objectMarkingRefs = objectMarkingRefs; }
}
'@
Set-Content -Path "$basePath\StixPersona.java" -Value $personaContent -Encoding UTF8
Write-Host "Created: StixPersona.java"

# StixPhoneNumber
$phoneContent = @'
package io.opencti.types.stix.sco;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.types.stix.common.*;

import java.util.List;

/**
 * STIX Phone Number object.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sco.d.ts
 */
public class StixPhoneNumber extends StixCyberObject {
    public static final String TYPE = "phone-number";

    private String value;
    private String description;
    private Integer score;
    private List<String> labels;
    @JsonProperty("created_by_ref")
    private String createdByRef;
    @JsonProperty("object_marking_refs")
    private List<String> objectMarkingRefs;

    public StixPhoneNumber() {
        this.type = TYPE;
    }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }
    public List<String> getLabels() { return labels; }
    public void setLabels(List<String> labels) { this.labels = labels; }
    public String getCreatedByRef() { return createdByRef; }
    public void setCreatedByRef(String createdByRef) { this.createdByRef = createdByRef; }
    public List<String> getObjectMarkingRefs() { return objectMarkingRefs; }
    public void setObjectMarkingRefs(List<String> objectMarkingRefs) { this.objectMarkingRefs = objectMarkingRefs; }
}
'@
Set-Content -Path "$basePath\StixPhoneNumber.java" -Value $phoneContent -Encoding UTF8
Write-Host "Created: StixPhoneNumber.java"

# StixSSHKey
$sshContent = @'
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
'@
Set-Content -Path "$basePath\StixSSHKey.java" -Value $sshContent -Encoding UTF8
Write-Host "Created: StixSSHKey.java"

# StixTrackingNumber
$trackingContent = @'
package io.opencti.types.stix.sco;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.types.stix.common.*;

import java.util.List;

/**
 * STIX Tracking Number object.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sco.d.ts
 */
public class StixTrackingNumber extends StixCyberObject {
    public static final String TYPE = "tracking-number";

    private String value;
    private String description;
    private Integer score;
    private List<String> labels;
    @JsonProperty("created_by_ref")
    private String createdByRef;
    @JsonProperty("object_marking_refs")
    private List<String> objectMarkingRefs;

    public StixTrackingNumber() {
        this.type = TYPE;
    }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }
    public List<String> getLabels() { return labels; }
    public void setLabels(List<String> labels) { this.labels = labels; }
    public String getCreatedByRef() { return createdByRef; }
    public void setCreatedByRef(String createdByRef) { this.createdByRef = createdByRef; }
    public List<String> getObjectMarkingRefs() { return objectMarkingRefs; }
    public void setObjectMarkingRefs(List<String> objectMarkingRefs) { this.objectMarkingRefs = objectMarkingRefs; }
}
'@
Set-Content -Path "$basePath\StixTrackingNumber.java" -Value $trackingContent -Encoding UTF8
Write-Host "Created: StixTrackingNumber.java"

Write-Host "`nAll remaining SCO classes created successfully!" -ForegroundColor Green
