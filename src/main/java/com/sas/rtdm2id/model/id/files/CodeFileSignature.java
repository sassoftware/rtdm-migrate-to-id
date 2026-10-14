package com.sas.rtdm2id.model.id.files;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * The signature of an existing Viya code file ({@code GET /decisions/codeFiles/{id}}), read leniently: ID derives the
 * terms from the code itself (e.g. an SQL code file's output data grid is named after the code file), so a step must
 * map exactly these names. Types and directions are kept as text so values not modelled elsewhere do not fail parsing.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CodeFileSignature {
    private String id;
    private String name;
    private List<Term> signature = new ArrayList<>();

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Term {
        private String name;
        private String dataType;
        private String direction;
    }
}
