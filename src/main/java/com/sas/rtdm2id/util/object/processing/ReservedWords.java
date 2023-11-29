package com.sas.rtdm2id.util.object.processing;

public enum ReservedWords {
	___KPLIST,
	_ALL_,
	_N_,
	_NEW_,
	_NULL_,
	_RC,
	_ROWSET_,
	_TEMPORARY_,
	_THREADID_,
	ABORT,
	AND,
	AS,
	ASM,
	BIGINT,
	BINARY,
	BY,
	CALL,
	CATALOG,
	CHAR,
	CHARACTER,
	COMMIT,
	CONTINUE,
	DATA,
	DATE,
	DCL,
	DECIMAL,
	DECLARE,
	DELETE,
	DESCENDING,
	DIM,
	DO,
	DOUBLE,
	DROP,
	DS2_OPTIONS,
	ELIF,
	ELSE,
	ENCRYPT,
	END,
	ENDDATA,
	ENDMODULE,
	ENDPACKAGE,
	ENDSTAGE,
	ENDTABLE,
	ENDTHREAD,
	EQ,
	ERROR,
	ESCAPE,
	FALSE,
	FILE,
	FILENAME,
	FLOAT,
	FORMAT,
	FORTRAN,
	FORWARD,
	FROM,
	FUNCTION,
	GE,
	GLOBAL,
	GOTO,
	GT,
	HAVING,
	IDENTITY,
	IF,
	IN,
	IN_OUT,
	INDSNAME,
	INFILE,
	INFORMAT,
	INLINE,
	INPUT,
	INT,
	INTEGER,
	INVARCHAR,
	KEEP,
	LABEL,
	LE,
	LEAVE,
	LIBNAME,
	LIKE,
	LIST,
	LT,
	MERGE,
	METHOD,
	MISSING,
	MODIFY,
	MODULE,
	NATIONAL,
	NCHAR,
	NE,
	NG,
	NL,
	NOT,
	NOTIN,
	NOTLIKE,
	NULL,
	NUMERIC,
	NVARCHAR,
	ODS,
	OR,
	OTHER,
	OTHERWISE,
	OUTPUT,
	OVERWRITE,
	PACKAGE,
	PRECISION,
	PRIVATE,
	PROGRAM,
	PUT,
	REAL,
	REMOVE,
	RENAME,
	REPLACE,
	REQUIRE,
	RETAIN,
	RETURN,
	RETURNS,
	ROLLBACK,
	SAS_ENCRYPT,
	SELECT,
	SET,
	SMALLINT,
	SQLSUB,
	STAGE,
	STOP,
	STORED,
	SUBSTR,
	SYSTEM,
	T_UDF,
	TABLE,
	THEN,
	THIS,
	THIS_ENTITY_PRIMARY_KEY,
	THIS_TRANSACTION_DTTM,
	THREAD,
	THREADS,
	TIME,
	TIMESTAMP,
	TINYINT,
	TO,
	TRANSACTION,
	TRUE,
	TSPL_OPTIONS,
	UNTIL,
	UPDATE,
	VARARRAY,
	VARBINARY,
	VARCHAR,
	VARLIST,
	VARYING,
	WHEN,
	WHERE,
	WHILE;
	
	public static boolean isReservedWord(String value) {
		if ( value != null) {
			try {
				ReservedWords.valueOf(value.toUpperCase());
				return true;
			} catch(IllegalArgumentException e) {
                // failed
			}
		}
		return false;
	}
}
