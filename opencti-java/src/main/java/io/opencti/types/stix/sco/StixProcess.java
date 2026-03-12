package io.opencti.types.stix.sco;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.types.stix.common.*;

import java.util.List;
import java.util.Map;

/**
 * STIX Process object.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sco.d.ts
 */
public class StixProcess extends StixCyberObject {
    public static final String TYPE = "process";

    @JsonProperty("is_hidden")
    private Boolean isHidden;
    private Integer pid;
    @JsonProperty("created_time")
    private String createdTime;
    private String cwd;
    @JsonProperty("command_line")
    private String commandLine;
    @JsonProperty("environment_variables")
    private Map<String, String> environmentVariables;
    @JsonProperty("opened_connection_refs")
    private List<String> openedConnectionRefs;
    @JsonProperty("creator_user_ref")
    private String creatorUserRef;
    @JsonProperty("image_ref")
    private String imageRef;
    @JsonProperty("parent_ref")
    private String parentRef;
    @JsonProperty("child_refs")
    private List<String> childRefs;

    public StixProcess() {
        this.type = TYPE;
    }

    public Boolean getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(Boolean isHidden) {
        this.isHidden = isHidden;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getCwd() {
        return cwd;
    }

    public void setCwd(String cwd) {
        this.cwd = cwd;
    }

    public String getCommandLine() {
        return commandLine;
    }

    public void setCommandLine(String commandLine) {
        this.commandLine = commandLine;
    }

    public Map<String, String> getEnvironmentVariables() {
        return environmentVariables;
    }

    public void setEnvironmentVariables(Map<String, String> environmentVariables) {
        this.environmentVariables = environmentVariables;
    }

    public List<String> getOpenedConnectionRefs() {
        return openedConnectionRefs;
    }

    public void setOpenedConnectionRefs(List<String> openedConnectionRefs) {
        this.openedConnectionRefs = openedConnectionRefs;
    }

    public String getCreatorUserRef() {
        return creatorUserRef;
    }

    public void setCreatorUserRef(String creatorUserRef) {
        this.creatorUserRef = creatorUserRef;
    }

    public String getImageRef() {
        return imageRef;
    }

    public void setImageRef(String imageRef) {
        this.imageRef = imageRef;
    }

    public String getParentRef() {
        return parentRef;
    }

    public void setParentRef(String parentRef) {
        this.parentRef = parentRef;
    }

    public List<String> getChildRefs() {
        return childRefs;
    }

    public void setChildRefs(List<String> childRefs) {
        this.childRefs = childRefs;
    }
}
