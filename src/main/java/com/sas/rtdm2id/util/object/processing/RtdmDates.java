/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.util.object.processing;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

/**
 * Parses the date/time texts RTDM writes into extracts and renders them as SAS constants for ID expressions.
 * <p>
 * RTDM formats dates with the Java US locale, e.g. filter Values {@code 9/7/26, 7:25 AM} and FormattedValues
 * {@code Sep 7, 2026}. Recent JDKs put a narrow no-break space (U+202F) before AM/PM, so spacing is normalized before
 * parsing. ID expects SAS constants: {@code '07SEP2026'd} for dates and {@code '07SEP2026:07:25:00'dt} for datetimes.
 */
final class RtdmDates {
    private static final DateTimeFormatter SHORT_DATE_TIME = DateTimeFormatter.ofPattern("M/d/yy, h:mm a", Locale.US);
    private static final DateTimeFormatter SHORT_DATE_TIME_NO_COMMA = DateTimeFormatter.ofPattern("M/d/yy h:mm a", Locale.US);
    private static final DateTimeFormatter SHORT_DATE = DateTimeFormatter.ofPattern("M/d/yy", Locale.US);
    private static final Set<DateTimeFormatter> TWO_DIGIT_YEAR_FORMATS = Set.of(SHORT_DATE_TIME, SHORT_DATE_TIME_NO_COMMA, SHORT_DATE);

    private static final List<DateTimeFormatter> DATE_TIME_FORMATS = List.of(
            DateTimeFormatter.ofPattern("yyyy-M-d'T'H:mm:ss[.SSS]", Locale.US),
            DateTimeFormatter.ofPattern("yyyy-M-d H:mm:ss[.SSS]", Locale.US),
            SHORT_DATE_TIME,
            SHORT_DATE_TIME_NO_COMMA,
            DateTimeFormatter.ofPattern("MMM d, yyyy, h:mm:ss a", Locale.US),
            DateTimeFormatter.ofPattern("MMM d, yyyy h:mm:ss a", Locale.US));
    private static final List<DateTimeFormatter> DATE_FORMATS = List.of(
            DateTimeFormatter.ofPattern("yyyy-M-d", Locale.US),
            SHORT_DATE,
            DateTimeFormatter.ofPattern("M/d/yyyy", Locale.US),
            DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.US));
    private static final DateTimeFormatter SAS_DATETIME = DateTimeFormatter.ofPattern("ddMMMyyyy:HH:mm:ss", Locale.US);
    private static final DateTimeFormatter SAS_DATE = DateTimeFormatter.ofPattern("ddMMMyyyy", Locale.US);

    private RtdmDates() {
    }

    /** Parses a date/time text; date-only texts become midnight. */
    static Optional<LocalDateTime> parseDateTime(String value) {
        if (value == null) {
            return Optional.empty();
        }
        String text = normalize(value);
        for (DateTimeFormatter format : DATE_TIME_FORMATS) {
            try {
                return Optional.of(adjustTwoDigitYear(LocalDateTime.parse(text, format), format));
            } catch (DateTimeParseException ignored) {
                // try next format
            }
        }
        for (DateTimeFormatter format : DATE_FORMATS) {
            try {
                return Optional.of(adjustTwoDigitYear(LocalDate.parse(text, format).atStartOfDay(), format));
            } catch (DateTimeParseException ignored) {
                // try next format
            }
        }
        return Optional.empty();
    }

    /**
     * java.time reads "yy" as 2000-2099, so a two-digit 70 would be 2070. Like java.text.SimpleDateFormat (which RTDM
     * used to write these values), a two-digit year is placed within 80 years before and 20 years after today.
     */
    private static LocalDateTime adjustTwoDigitYear(LocalDateTime dateTime, DateTimeFormatter format) {
        if (TWO_DIGIT_YEAR_FORMATS.contains(format) && dateTime.getYear() > LocalDate.now().getYear() + 20) {
            return dateTime.minusYears(100);
        }
        return dateTime;
    }

    static String sasDateTimeLiteral(LocalDateTime dateTime) {
        return "'" + dateTime.format(SAS_DATETIME).toUpperCase(Locale.US) + "'dt";
    }

    static String sasDateLiteral(LocalDate date) {
        return "'" + date.format(SAS_DATE).toUpperCase(Locale.US) + "'d";
    }

    static boolean isSasDateLiteral(String value) {
        return value != null && value.trim().matches("(?i)'[^']*'dt?");
    }

    private static String normalize(String value) {
        return value.replace(' ', ' ').replace(' ', ' ').trim().replaceAll("\\s+", " ");
    }
}
