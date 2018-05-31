package com.server.date;

import java.time.format.DateTimeFormatter;

/**
 * @author LiuQi - [Created on 2018-05-24]
 */
public enum TimeFormatterType {

    date("yyyy-MM-dd"),
    time("hh:mm:ss"),
    dateTime("yyyy-MM-dd hh:mm:ss"),
    dateTimeMills("yyyy-MM-dd hh:mm:ss.SSS"),
    dateTimeMillZone("yyyy-MM-dd hh:mm:ss.SSS Z")

    ;

    private DateTimeFormatter formatter;

    TimeFormatterType(String pattern) {
        this.formatter = DateTimeFormatter.ofPattern(pattern);
    }

    public DateTimeFormatter getFormatter() {
        return formatter;
    }
}
