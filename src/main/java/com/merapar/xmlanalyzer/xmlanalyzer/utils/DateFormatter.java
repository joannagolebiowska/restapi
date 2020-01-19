package com.merapar.xmlanalyzer.xmlanalyzer.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class DateFormatter {

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS");

    public static Date getDateWithTimeZone(String dataToParse) throws ParseException {
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        return simpleDateFormat.parse(dataToParse);

    }

    public static String getStringFromDate(Date date){
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        return simpleDateFormat.format(date);
    }

}
