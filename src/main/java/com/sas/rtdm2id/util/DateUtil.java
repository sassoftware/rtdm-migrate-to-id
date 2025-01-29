/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    public static String getInformateDate(String dateValueString) {
        /**The date/time value from RTDM is transfered as a fixed formatted string value with
        *format = new SimpleDateFormat("yyyy-M-d'T'H:mm:ss", Locale.US).
        */
        String PATTERN = "yyyy-M-d'T'H:mm:ss";
        Locale LOCALE = Locale.US;
        DateFormat DATE_FORMAT = new SimpleDateFormat(PATTERN, LOCALE);
        try {
            Date date = DATE_FORMAT.parse(dateValueString);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            Integer year = cal.get(Calendar.YEAR);
            Integer day_month = cal.get(Calendar.DAY_OF_MONTH);
            Integer hour = cal.get(Calendar.HOUR_OF_DAY);
            Integer minute = cal.get(Calendar.MINUTE);
            
            String dateTimeFormatted = day_month < 10 ? "0"+day_month.toString():day_month.toString()
                                      +getMonthAbbr(date) + year.toString() +":"
                                      + (hour<10? "0"+hour.toString(): hour.toString()) +":" 
                                      + (minute<10?"0"+minute.toString():minute.toString());
            return "inputn('"+ dateTimeFormatted + "','datetime21.2')";

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getMonthAbbr(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM",Locale.US);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return sdf.format(date).toUpperCase();
    }



}