package io.opencti.database.stix;

import java.util.*;

/**
 * STIX Reference Mapping.
 * Original file: opencti-platform/opencti-graphql/src/database/stix-ref.ts
 *
 * This class provides mapping for STIX reference relationships.
 */
public class StixRefMapping {

    /**
     * Reference attribute information.
     * Corresponds to RefAttribute in the original TypeScript code.
     */
    public static class RefAttribute {
        private final String name;
        private final List<String> toTypes;

        public RefAttribute(String name, List<String> toTypes) {
            this.name = name;
            this.toTypes = toTypes;
        }

        public String getName() {
            return name;
        }

        public List<String> getToTypes() {
            return toTypes;
        }
    }

    /**
     * Reference mapping entry.
     */
    public static class RefMappingEntry {
        private final String key;
        private final List<RefAttributeInfo> values;

        public RefMappingEntry(String key, List<RefAttributeInfo> values) {
            this.key = key;
            this.values = values;
        }

        public String getKey() {
            return key;
        }

        public List<RefAttributeInfo> getValues() {
            return values;
        }
    }

    /**
     * Simplified reference attribute information for mapping.
     */
    public static class RefAttributeInfo {
        private final String name;
        private final List<String> toTypes;

        public RefAttributeInfo(String name, List<String> toTypes) {
            this.name = name;
            this.toTypes = toTypes;
        }

        public String getName() {
            return name;
        }

        public List<String> getToTypes() {
            return toTypes;
        }
    }

    /**
     * Schema relations reference definition cache.
     * This maps relationship types to their reference attributes.
     */
    private static final Map<String, List<RefAttribute>> relationsRefCache = new HashMap<>();

    /**
     * Initialize the reference mapping.
     * This method builds the mapping from STIX core relationships to their reference attributes.
     *
     * @return List of reference mapping entries
     */
    public static List<RefMappingEntry> buildSchemaRelationsRefTypesMapping() {
        List<RefMappingEntry> result = new ArrayList<>();

        // Build relationships refs from STIX core relationships
        Map<String, List<RefAttribute>> relationshipsRefs = new HashMap<>();

        // Get all STIX core relationship types
        List<String> stixCoreRelationships = getStixCoreRelationships();

        for (String relation : stixCoreRelationships) {
            List<RefAttribute> refs = getRelationsRef(relation);
            if (refs != null && !refs.isEmpty()) {
                relationshipsRefs.put(relation, refs);
            }
        }

        // Convert relationshipsRefs to RefMappingEntry
        for (Map.Entry<String, List<RefAttribute>> entry : relationshipsRefs.entrySet()) {
            List<RefAttributeInfo> values = new ArrayList<>();
            for (RefAttribute ref : entry.getValue()) {
                values.add(new RefAttributeInfo(ref.getName(), ref.getToTypes()));
            }
            result.add(new RefMappingEntry(entry.getKey(), values));
        }

        // Add cached relations refs
        for (Map.Entry<String, List<RefAttribute>> entry : relationsRefCache.entrySet()) {
            List<RefAttributeInfo> values = new ArrayList<>();
            for (RefAttribute ref : entry.getValue()) {
                values.add(new RefAttributeInfo(ref.getName(), ref.getToTypes()));
            }
            result.add(new RefMappingEntry(entry.getKey(), values));
        }

        return result;
    }

    /**
     * Get reference attributes for a specific relationship type.
     *
     * @param relationshipType The relationship type
     * @return List of reference attributes
     */
    public static List<RefAttribute> getRelationsRef(String relationshipType) {
        // This is a placeholder implementation.
        // In the actual implementation, this would query the schema definition.
        return relationsRefCache.getOrDefault(relationshipType, Collections.emptyList());
    }

    /**
     * Get all STIX core relationship types.
     *
     * @return List of relationship type names
     */
    private static List<String> getStixCoreRelationships() {
        // Return the list of STIX core relationship types
        return Arrays.asList(
            "related-to",
            "revoked-by",
            "derived-from",
            "duplicate-of",
            "uses",
            "targets",
            "attributed-to",
            "compromises",
            "originates-from",
            "investigates",
            "based-on",
            "identifies",
            "located-at",
            "indicates",
            "mitigates",
            "impersonates",
            "delivers",
            "exploits",
            "variant-of",
            "communicates-with",
            "beacons-to",
            "exfiltrates-to",
            "downloads",
            "drops",
            "has",
            "characterizes",
            "analysis-of",
            "static-analysis-of",
            "dynamic-analysis-of",
            "host-vm",
            "operating-system",
            "sample",
            "contains",
            "resolves-to",
            "belongs-to",
            "part-of",
            "observed-as",
            "remediates",
            "publishes",
            "cooperates-with",
            "participates-in"
        );
    }

    /**
     * Register a reference attribute for a relationship type.
     *
     * @param relationshipType The relationship type
     * @param refAttribute The reference attribute
     */
    public static void registerRefAttribute(String relationshipType, RefAttribute refAttribute) {
        relationsRefCache.computeIfAbsent(relationshipType, k -> new ArrayList<>()).add(refAttribute);
    }

    /**
     * Clear the reference cache.
     */
    public static void clearCache() {
        relationsRefCache.clear();
    }
}
