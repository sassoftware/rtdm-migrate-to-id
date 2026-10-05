/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.model.id.segmentation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sas.rtdm2id.model.id.decision.Link;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SegmentationTree {
    private String name;
    private String description;
    private List<SignatureTerm> signature;
    private String id;
    private String revisionId;
    private String revisionUri;
    private List<Link> links;
    private List<Outcome> outcomes;
    private List<LocalValueList> localValueLists;
    private List<Matrix> matrices;
    private List<BooleanExpression> booleanExpressions;
    private Node node;

    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SignatureTerm {
        private String name;
        private String dataType;
        private String direction;
        private String description;
        private Integer length;
        private String localValueListName;
        @JsonIgnore
        private String sourceVariableName;
        @JsonIgnore
        private String sourceDataType;
        @JsonIgnore
        private String derivationExpression;
    }

    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Outcome {
        private String name;
        private String description;
        private List<VariableAssignment> actions;
    }

    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class VariableAssignment {
        private String type;
        private String variableName;
        private String valueType;
        private Object value;
    }

    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LocalValueList {
        private String name;
        private List<Item> items;
        private Properties properties;

        @Getter
        @Setter
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Item {
            private String key;
            private String value;
            private Integer order;
        }

        @Getter
        @Setter
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Properties {
            private String dataType;
        }
    }

    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Matrix {
        private String name;
        private String description;
        private String downVariableName;
        private String downVariableLocalValueListName;
        private List<Object> downVariableValues;
        private List<String> downVariableLabels;
        private String acrossVariableName;
        private String acrossVariableLocalValueListName;
        private List<Object> acrossVariableValues;
        private List<String> acrossVariableLabels;
        private List<List<Object>> outcomes;
    }

    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class BooleanExpression {
        private String name;
        private String description;
        private String expression;
    }

    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Node {
        private String id;
        private String label;
        private String type;
        private String variableName;
        private String matrixName;
        private String booleanExpressionName;
        private List<ValueNode> valueNodes;
    }

    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ValueNode {
        private String id;
        private String label;
        private Object value;
        private String outcomeName;
        private Node next;
        private List<Object> variableAssignmentOutcomeActionOverrides;
    }
}