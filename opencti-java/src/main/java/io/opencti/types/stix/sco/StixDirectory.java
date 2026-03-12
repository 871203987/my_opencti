package io.opencti.types.stix.sco;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.types.stix.common.*;

import java.util.List;

/**
 * STIX Directory object.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sco.d.ts
 * Original type:
 * export interface StixDirectory extends StixCyberObject {
 *   path: string;
 *   path_enc: string; // optional
 *   ctime: StixDate; // optional
 *   mtime: StixDate; // optional
 *   atime: StixDate; // optional
 *   contains_refs: Array<StixId>; // optional
 * }
 */
public class StixDirectory extends StixCyberObject {
    public static final String TYPE = "directory";

    private String path;
    @JsonProperty("path_enc")
    private String pathEnc;
    private String ctime;
    private String mtime;
    private String atime;
    @JsonProperty("contains_refs")
    private List<String> containsRefs;

    public StixDirectory() {
        this.type = TYPE;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPathEnc() {
        return pathEnc;
    }

    public void setPathEnc(String pathEnc) {
        this.pathEnc = pathEnc;
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

    public List<String> getContainsRefs() {
        return containsRefs;
    }

    public void setContainsRefs(List<String> containsRefs) {
        this.containsRefs = containsRefs;
    }
}
