package io.opencti.types.stix.sco;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.types.stix.common.*;

import java.util.List;
import java.util.Map;

/**
 * STIX File object.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sco.d.ts
 */
public class StixFile extends StixCyberObject {
    public static final String TYPE = "file";

    private Map<String, String> hashes;
    private Long size;
    private String name;
    @JsonProperty("name_enc")
    private String nameEnc;
    @JsonProperty("magic_number_hex")
    private String magicNumberHex;
    @JsonProperty("mime_type")
    private String mimeType;
    private String ctime;
    private String mtime;
    private String atime;
    @JsonProperty("parent_directory_ref")
    private String parentDirectoryRef;
    @JsonProperty("contains_refs")
    private List<String> containsRefs;
    @JsonProperty("content_ref")
    private String contentRef;
    @JsonProperty("additional_names")
    private List<String> additionalNames;

    public StixFile() {
        this.type = TYPE;
    }

    public Map<String, String> getHashes() {
        return hashes;
    }

    public void setHashes(Map<String, String> hashes) {
        this.hashes = hashes;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameEnc() {
        return nameEnc;
    }

    public void setNameEnc(String nameEnc) {
        this.nameEnc = nameEnc;
    }

    public String getMagicNumberHex() {
        return magicNumberHex;
    }

    public void setMagicNumberHex(String magicNumberHex) {
        this.magicNumberHex = magicNumberHex;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getMtime() {
        return mtime;
    }

    public void setMtime(String mtime) {
        this.mtime = mtime;
    }

    public String getAtime() {
        return atime;
    }

    public void setAtime(String atime) {
        this.atime = atime;
    }

    public String getParentDirectoryRef() {
        return parentDirectoryRef;
    }

    public void setParentDirectoryRef(String parentDirectoryRef) {
        this.parentDirectoryRef = parentDirectoryRef;
    }

    public List<String> getContainsRefs() {
        return containsRefs;
    }

    public void setContainsRefs(List<String> containsRefs) {
        this.containsRefs = containsRefs;
    }

    public String getContentRef() {
        return contentRef;
    }

    public void setContentRef(String contentRef) {
        this.contentRef = contentRef;
    }

    public List<String> getAdditionalNames() {
        return additionalNames;
    }

    public void setAdditionalNames(List<String> additionalNames) {
        this.additionalNames = additionalNames;
    }
}
