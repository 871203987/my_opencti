package io.opencti.types.stix.sdo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.opencti.types.stix.common.StixDomainObject;
import io.opencti.types.stix.common.StixId;

import java.util.List;

/**
 * STIX Note type.
 * Original file: opencti-platform/opencti-graphql/src/types/stix-2-1-sdo.d.ts
 * Original type:
 * export interface StixNote extends StixContainer {
 *   abstract: string;
 *   content: string;
 *   authors: Array<string>;
 *   note_types: Array<string>;
 *   likelihood: number;
 *   extensions: {
 *     [STIX_EXT_OCTI]: StixNoteExtension;
 *   };
 * }
 */
public class StixNote extends StixContainer {
    public static final String TYPE = "note";

    private String abstractText;
    private String content;
    private List<String> authors;
    @JsonProperty("note_types")
    private List<String> noteTypes;
    private Integer likelihood;
    private StixNoteExtension noteExtension;

    public StixNote() {
        this.type = TYPE;
    }

    @JsonProperty("abstract")
    public String getAbstractText() {
        return abstractText;
    }

    public void setAbstractText(String abstractText) {
        this.abstractText = abstractText;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public List<String> getNoteTypes() {
        return noteTypes;
    }

    public void setNoteTypes(List<String> noteTypes) {
        this.noteTypes = noteTypes;
    }

    public Integer getLikelihood() {
        return likelihood;
    }

    public void setLikelihood(Integer likelihood) {
        this.likelihood = likelihood;
    }

    public StixNoteExtension getNoteExtension() {
        return noteExtension;
    }

    public void setNoteExtension(StixNoteExtension noteExtension) {
        this.noteExtension = noteExtension;
    }
}

