/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.util.model;

public class RTDM2IDConstants {
    public static final String OUTPUT_DIRECTION = "output";
    public static final String INPUT_DIRECTION = "input";
    public static final String NONE_DIRECTION = "none";

    public static final String IN_OUT_DIRECTION = "inOut";
    public static final String STRING_CONSTANT = "string";
    public static final String BOOLEAN_LIST_CONSTANT = "boolean list";
    public static final String DATE_LIST_CONSTANT = "date list";
    public static final String DOUBLE_LIST_CONSTANT = "double list";
    public static final String INTEGER_LIST_CONSTANT = "integer list";
    public static final String STRING_LIST_CONSTANT = "string list";
    public static final String DATAGRID_CONSTANT = "data grid";
    public static final String DATAGRID_CONSTANT_SID = "dataGrid";
    public static final String BOOLEAN_CONSTANT = "boolean";
    public static final String CHAR_CONSTANT = "char";
    public static final String DATE_CONSTANT = "date";
    public static final String DATETIME_CONSTANT = "datetime";
    public static final String INTEGER_CONSTANT = "integer";
    public static final String DECIMAL_CONSTANT = "decimal";
    public static final String DOUBLE_CONSTANT = "double";
    public static final String NUMERIC_CONSTANT = "numeric";
    public static final String DECISION_DS2_CODE_FILE_CONSTANT = "decisionDS2CodeFile";
    public static final String REGEXP = "\\s*,\\s*";
    public static final String CUSTOM_CONSTANT = "custom";
    public static final String BUSINESS_RULES_CONSTANT = "rules group";
    public static final String GROOVY_CONSTANT = "Java code";
    public static final String MODEL_CONSTANT = "model";
    public static final String WEB_SERVICE_CONSTANT = "web service";
    public static final String READ_DATA_CONSTANT = "read data";
    public static final String UPDATE_DATA_CONSTANT = "updated date";
    public static final String INSERT_DATA_CONSTANT = "insert data";

    public static final String CALCULATED_DATA_TYPE_CHARACTER = "0";
    public static final String CALCULATED_DATA_TYPE_NUMERIC_NOMINAL = "3";
    public static final String CALCULATED_DATA_TYPE_NUMERIC_INTERVAL = "4";
    public static final String CALCULATED_DATA_TYPE_DATE = "1";
    public static final String CALCULATED_DATA_TYPE_TIMESTAMP = "2";
    public static final String CALCULATED_DATA_TYPE_BOOLEAN = "5";
    public static final String CALCULATED_DATA_TYPE_DATAGRID = "7";

    // DS2 Constants
    public static final String DS2_SPACE = " ";
    public static final String DS2_DATAGRID = "datagrid";
    public static final String DS2_INT_CONSTANT = "int" + DS2_SPACE;
    public static final String DS2_DOUBLE_CONSTANT = DOUBLE_CONSTANT + DS2_SPACE;
    public static final String DS2_HASH_CONSTANT = "hash" + DS2_SPACE;
    public static final String DS2_LOGGER_CONSTANT = "logger" + DS2_SPACE;

    public static final String PACKAGE_TAP_DATETIME_CONSTANT = "package tap_datetime" + DS2_SPACE;
    public static final String PACKAGE_TAP_HASH_CONSTANT = "package tap_hash" + DS2_SPACE;
    public static final String PACKAGE_TAP_LOGGER_CONSTANT = "package tap_logger" + DS2_SPACE;
    public static final String PACKAGE_TAP_SQLTABLE_CONSTANT = "package tap_sqltable" + DS2_SPACE;

    public static final String TAP_FLOAT_ARRAY_CONSTANT = "tap_float_array";
    public static final String TAP_STRING_ARRAY_CONSTANT = "tap_string_array";
    public static final String TAP_INT_ARRAY_CONSTANT = "tap_int_array";
    public static final String TAP_DATETIME_ARRAY_CONSTANT = "tap_datetime_array";
    public static final String TAP_BOOLEAN_ARRAY_CONSTANT = "tap_boolean_array";

    public static final String SCALAR_CONSTANT = "scalar";

    public static final String STAGED_TREATMENTS = "StagedTreatments";

    public static final String CH_AND_RH_NODE = "CHandRHNode";

    public static final String ASSIGNMENT_NODE = "AssignmentNode";

    public static final String REMOVE_STAGED_TREATMENTS = "RemoveStagedTreatments";

    // RTDM Variable Source (i.e. data picker categories)
    public static final String SOURCE_NODE = "Node";
	public static final String SOURCE_EVENT = "Request";
	public static final String SOURCE_PROCESS = "Process";
	public static final String SOURCE_START = "StartNode";
	public static final String SOURCE_GLOBAL = "Global";
	public static final String SOURCE_IDENTIFIER = "Identifier";
	public static final String SOURCE_TODAYS_DATE_AND_TIME = "TodaysDateAndTime";
	public static final String SOURCE_CALCULATED_DATA_ITEM = "CalculatedDataItem";
	public static final String SOURCE_CAMPAIGN = "Campaign";
	public static final String SOURCE_CELL = "Cell";
	public static final String SOURCE_REPLY = "Reply";
	public static final String SOURCE_PACKAGE = "Package";
	public static final String SOURCE_CAMPAIGN_TAG = "CampaignTag";
	public static final String SOURCE_PACKAGE_TAG = "PackageTag";
	public static final String SOURCE_REPLY_TAG = "ReplyTag";
	public static final String SOURCE_RUNTIME = "Runtime";

    // Constants used when populating the inbound data patterns (varInfoId)
    public static final String UPSTREAM_NODES_FOLDER = "upstreamNodes";
    public static final String EVENTS_FOLDER = "events";
    public static final String GLOBALS_FOLDER = "globals";
    public static final String IDENTIFIERS_FOLDER = "identifiers";
    public static final String CALCULATED_DATA_ITEMS_FOLDER = "calculatedDataItems";
    public static final String CAMPAIGN_FOLDER = "campaign";
    public static final String REPLY_FOLDER = "reply";
    public static final String CELL_FOLDER = "cell";
    public static final String PACKAGE_FOLDER = "package";
    public static final String CUSTOM_DETAILS_FOLDER = "customDetails";
    public static final String CUSTOM_DETAILS_LINK_FOLDER = "customDetailsLink";
    public static final String CAMPAIGN_TAGS_FOLDER = "campaignTags";
    public static final String PACKAGE_TAGS_FOLDER = "packageTags";
    public static final String REPLY_TAGS_FOLDER = "replyTags";
    public static final String CUSTOM_DETAILS_LINK_TAGS_FOLDER = "customDetailsLinkTags";
    public static final String DATA_AND_TIME_FOLDER = "dateAndTime";
    public static final String DATE_AND_TIME_TODAY = "DateAndTime.today";
    public static final String CODE_VARIABLE = "code";
    public static final String NAME_VARIABLE = "name";
    public static final String DESCRIPTION_VARIABLE = "description";
    public static final String TREATMENT_NAME_VARIABLE = "treatmentName";
    public static final String TREATMENT_CODE_VARIABLE = "treatmentCode";
    public static final String TREATMENT_DESCRIPTION_VARIABLE = "treatmentDescription";
    public static final String TREATMENT_START_DATE_VARIABLE = "treatmentStartDate";
    public static final String TREATMENT_END_DATE_VARIABLE = "treatmentEndDate";
    public static final String TREATMENT_STAGED_VARIABLE = "treatmentStaged";
    public static final String TREATMENT_ASSET_VARIABLE = "treatmentAsset";
    public static final String TREATMENT_TRACKING_CODE_VARIABLE = "treatmentTrackingCode";
    public static final String TREATMENT_COUNT = "treatmentCount";
    public static final String TREATMENT_SCORE = "treatmentScore";
    public static final String TREATMENT_ELIGIBILITY = "treatmentEligibility";
    public static final String TREATMENT_RANDOM_SCORE_FLAG = "treatmentRandomScoreFlag";
    public static final String PACKAGE_CODE_VARIABLE = "packageCode";
    public static final String RESPONSE_TRACKING_CODE_VARIABLE = "responseTrackingCode";
    public static final String TREATMENT_DETAILS_VARIABLE = "treatmentDetails";
    public static final String TREATMENT_CUSTOM_DETAILS_VARIABLE = "treatmentCustomDetails";
    public static final String TREATMENT_CUSTOM_LIST_DETAILS_VARIABLE = "treatmentCustomListDetails";
    public static final String RUNTIME_FOLDER = "runtime";
    public static final String RUNTIME_EVENT_IDENTITY = "runtime.eventIdentity";
    public static final String RUNTIME_MISSING = "runtime.missing";
    
    public static final String CAMPAIGN_BRIEF_CUSTOM_DETAILS_SOURCE = "Brief Custom Details";
    public static final String TAG_CONSTANT = "Tag";

    public static final String LESS_THAN_OR_EQUALS_TO = "lessThanEquals";
    public static final String GREATER_THAN_OR_EQUALS_TO = "greaterThanEquals";
    public static final String LIKE = "like";
    public static final String IS_MISSING = "isMissing";
    public static final String IN_LIST = "inList";

    // RTDM Process Type Constants
    public static final int PROCESS_TYPE_UNKNOWN = -1;
    public static final int PROCESS_TYPE_CUSTOM = 0;
    public static final int PROCESS_TYPE_MODEL = 1;
    public static final int PROCESS_TYPE_READ_DATA = 2;
    public static final int PROCESS_TYPE_UPDATE_DATA = 3;
    public static final int PROCESS_TYPE_INSERT_DATA = 4;
    public static final int PROCESS_TYPE_SDM = 5;
    public static final int PROCESS_TYPE_WEB_SERVICE = 6;
    public static final int PROCESS_TYPE_RULES_GROUP = 7;
    public static final int PROCESS_TYPE_CELEBRUS = 8;
    public static final int PROCESS_TYPE_JAVA = 9;
    public static final int PROCESS_TYPE_DSCODEACTIVITY = 10;
    public static final int PROCESS_TYPE_JAVACODEACTIVITY = 11;
    public static final int PROCESS_TYPE_SELF_LEARNER = 12;

    private RTDM2IDConstants() {

    }
}
