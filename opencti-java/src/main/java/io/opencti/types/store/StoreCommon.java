package io.opencti.types.store;

/**
 * Store Common interface.
 * Original file: opencti-platform/opencti-graphql/src/types/store.d.ts
 * Common interface for all store objects.
 */
public interface StoreCommon {
    /**
     * Get entity type
     * @return entity type
     */
    String getEntityType();

    /**
     * Get internal ID
     * @return internal ID
     */
    String getInternalId();

    /**
     * Get standard ID
     * @return standard ID
     */
    String getStandardId();
}
