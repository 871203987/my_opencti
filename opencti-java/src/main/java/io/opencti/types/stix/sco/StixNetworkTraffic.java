package io.opencti.types.stix.sco;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.types.stix.common.*;

import java.util.List;
import java.util.Map;

/**
 * STIX Network Traffic object.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sco.d.ts
 */
public class StixNetworkTraffic extends StixCyberObject {
    public static final String TYPE = "network-traffic";

    private String start;
    private String end;
    @JsonProperty("is_active")
    private Boolean isActive;
    @JsonProperty("src_ref")
    private String srcRef;
    @JsonProperty("dst_ref")
    private String dstRef;
    @JsonProperty("src_port")
    private Integer srcPort;
    @JsonProperty("dst_port")
    private Integer dstPort;
    private List<String> protocols;
    @JsonProperty("src_byte_count")
    private Long srcByteCount;
    @JsonProperty("dst_byte_count")
    private Long dstByteCount;
    @JsonProperty("src_packets")
    private Long srcPackets;
    @JsonProperty("dst_packets")
    private Long dstPackets;
    private Map<String, Object> ipfix;
    @JsonProperty("src_payload_ref")
    private String srcPayloadRef;
    @JsonProperty("dst_payload_ref")
    private String dstPayloadRef;
    @JsonProperty("encapsulates_refs")
    private List<String> encapsulatesRefs;
    @JsonProperty("encapsulated_by_ref")
    private String encapsulatedByRef;

    public StixNetworkTraffic() {
        this.type = TYPE;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getSrcRef() {
        return srcRef;
    }

    public void setSrcRef(String srcRef) {
        this.srcRef = srcRef;
    }

    public String getDstRef() {
        return dstRef;
    }

    public void setDstRef(String dstRef) {
        this.dstRef = dstRef;
    }

    public Integer getSrcPort() {
        return srcPort;
    }

    public void setSrcPort(Integer srcPort) {
        this.srcPort = srcPort;
    }

    public Integer getDstPort() {
        return dstPort;
    }

    public void setDstPort(Integer dstPort) {
        this.dstPort = dstPort;
    }

    public List<String> getProtocols() {
        return protocols;
    }

    public void setProtocols(List<String> protocols) {
        this.protocols = protocols;
    }

    public Long getSrcByteCount() {
        return srcByteCount;
    }

    public void setSrcByteCount(Long srcByteCount) {
        this.srcByteCount = srcByteCount;
    }

    public Long getDstByteCount() {
        return dstByteCount;
    }

    public void setDstByteCount(Long dstByteCount) {
        this.dstByteCount = dstByteCount;
    }

    public Long getSrcPackets() {
        return srcPackets;
    }

    public void setSrcPackets(Long srcPackets) {
        this.srcPackets = srcPackets;
    }

    public Long getDstPackets() {
        return dstPackets;
    }

    public void setDstPackets(Long dstPackets) {
        this.dstPackets = dstPackets;
    }

    public Map<String, Object> getIpfix() {
        return ipfix;
    }

    public void setIpfix(Map<String, Object> ipfix) {
        this.ipfix = ipfix;
    }

    public String getSrcPayloadRef() {
        return srcPayloadRef;
    }

    public void setSrcPayloadRef(String srcPayloadRef) {
        this.srcPayloadRef = srcPayloadRef;
    }

    public String getDstPayloadRef() {
        return dstPayloadRef;
    }

    public void setDstPayloadRef(String dstPayloadRef) {
        this.dstPayloadRef = dstPayloadRef;
    }

    public List<String> getEncapsulatesRefs() {
        return encapsulatesRefs;
    }

    public void setEncapsulatesRefs(List<String> encapsulatesRefs) {
        this.encapsulatesRefs = encapsulatesRefs;
    }

    public String getEncapsulatedByRef() {
        return encapsulatedByRef;
    }

    public void setEncapsulatedByRef(String encapsulatedByRef) {
        this.encapsulatedByRef = encapsulatedByRef;
    }
}
