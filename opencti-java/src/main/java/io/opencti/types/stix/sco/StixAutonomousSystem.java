package io.opencti.types.stix.sco;

import io.opencti.types.stix.common.*;

/**
 * STIX Autonomous System object.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sco.d.ts
 * Original type:
 * export interface StixAutonomousSystem extends StixCyberObject {
 *   number: number;
 *   name: string; // optional
 *   rir: string; // optional
 * }
 */
public class StixAutonomousSystem extends StixCyberObject {
    public static final String TYPE = "autonomous-system";

    private Integer number;
    private String name;
    private String rir;

    public StixAutonomousSystem() {
        this.type = TYPE;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRir() {
        return rir;
    }

    public void setRir(String rir) {
        this.rir = rir;
    }
}
