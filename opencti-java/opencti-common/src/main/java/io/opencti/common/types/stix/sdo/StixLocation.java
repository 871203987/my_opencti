package io.opencti.common.types.stix.sdo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.common.types.stix.StixDomainObject;

/**
 * STIX Location type.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sdo.d.ts
 * Original type:
 * export interface StixLocation extends StixDomainObject {
 *   name: string;
 *   description: string;
 *   latitude: number | undefined;
 *   longitude: number | undefined;
 *   precision: number | undefined;
 *   region: string | undefined;
 *   country: string | undefined;
 *   administrative_area: string | undefined;
 *   city: string | undefined;
 *   street_address: string;
 *   postal_code: string;
 * }
 */
public class StixLocation extends StixDomainObject {
    public static final String TYPE = "location";

    private String name;
    private String description;
    private Double latitude;
    private Double longitude;
    private Double precision;
    private String region;
    private String country;
    @JsonProperty("administrative_area")
    private String administrativeArea;
    private String city;
    @JsonProperty("street_address")
    private String streetAddress;
    @JsonProperty("postal_code")
    private String postalCode;

    public StixLocation() {
        this.type = TYPE;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getPrecision() {
        return precision;
    }

    public void setPrecision(Double precision) {
        this.precision = precision;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAdministrativeArea() {
        return administrativeArea;
    }

    public void setAdministrativeArea(String administrativeArea) {
        this.administrativeArea = administrativeArea;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
