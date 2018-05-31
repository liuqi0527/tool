package com.server.date;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;

/**
 * @author LiuQi - [Created on 2018-05-24]
 */
public class DateTimeUtil {

    public static final long MillisOfSecond = ChronoUnit.SECONDS.getDuration().toMillis();
    public static final long MillisOfMinute = ChronoUnit.MINUTES.getDuration().toMillis();
    public static final long MillisOfHour = ChronoUnit.HOURS.getDuration().toMillis();
    public static final long MillisOfDay = ChronoUnit.DAYS.getDuration().toMillis();
    public static final long MillisOfWeek = ChronoUnit.WEEKS.getDuration().toMillis();

    public static final long SecondsOfMinute = ChronoUnit.MINUTES.getDuration().getSeconds();
    public static final long SecondsOfHour = ChronoUnit.HOURS.getDuration().getSeconds();
    public static final long SecondsOfDay = ChronoUnit.DAYS.getDuration().getSeconds();
    public static final long SecondsOfWeek = ChronoUnit.WEEKS.getDuration().getSeconds();

    public static final long MinutesOfHour = ChronoUnit.HOURS.getDuration().toMinutes();
    public static final long MinutesOfDay = ChronoUnit.DAYS.getDuration().toMinutes();
    public static final long MinutesOfWeek = ChronoUnit.WEEKS.getDuration().toMinutes();

    public static final long HoursOfDay = ChronoUnit.DAYS.getDuration().toHours();
    public static final long HoursOfWeek = ChronoUnit.WEEKS.getDuration().toHours();

    public static final long DaysOfWeek = ChronoUnit.WEEKS.getDuration().toDays();


    public static String toString(LocalDate localDate) {
        return toString(localDate, TimeFormatterType.date);
    }

    public static String toString(LocalTime localTime) {
        return toString(localTime, TimeFormatterType.time);
    }

    public static String toString(LocalDateTime localDateTime) {
        return toString(localDateTime, TimeFormatterType.dateTime);
    }

    public static String toString(TemporalAccessor localDateTime, TimeFormatterType type) {
        return toString(localDateTime, type.getFormatter());
    }

    public static String toString(TemporalAccessor localDateTime, DateTimeFormatter formatter) {
        return formatter.format(localDateTime);
    }


    public static LocalDate ToLocalDate(String text) {
        return LocalDate.parse(text, TimeFormatterType.date.getFormatter());
    }

    public static LocalTime ToLocalTime(String text) {
        return LocalTime.parse(text, TimeFormatterType.time.getFormatter());
    }

    public static LocalDateTime ToLocalDateTime(String text) {
        return ToLocalDateTime(text, TimeFormatterType.dateTime);
    }

    public static LocalDateTime ToLocalDateTime(String text, TimeFormatterType type) {
        return ToLocalDateTime(text, type.getFormatter());
    }

    public static LocalDateTime ToLocalDateTime(String text, DateTimeFormatter formatter) {
        return LocalDateTime.parse(text, formatter);
    }

    public static LocalDateTime ToLocalDateTime(String text, String pattern) {
        return LocalDateTime.parse(text, DateTimeFormatter.ofPattern(pattern));
    }


    public static long toMillis(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static LocalDateTime toLocalDateTime(long millis) {
        return Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static long getDurationMillis(Temporal start, Temporal end) {
        return Duration.between(start, end).toMillis();
    }

    public static long getDurationDay(Temporal start, Temporal end) {
        return Duration.between(start, end).toDays();
    }

    public static long getDurationHour(Temporal start, Temporal end) {
        return Duration.between(start, end).toHours();
    }

    public static long getDurationMinute(Temporal start, Temporal end) {
        return Duration.between(start, end).toMinutes();
    }

    /**
     * <pre>
     *      格式化一个时间长度对象
     *      HH:mm:ss
     * </pre>
     */
    public static String formatDuration(final long millis) {
        Duration duration = Duration.ofMillis(millis);
        long days = duration.toDays();
        long hours = duration.toHours() - days * HoursOfDay;
        long minutes = duration.toMinutes() - days * MinutesOfDay - hours * MinutesOfHour;
        long second = duration.toMillis() / 1000 - days * SecondsOfDay - hours * SecondsOfHour - minutes * SecondsOfMinute;
        return leftPathZero(hours) + ":" + leftPathZero(minutes) + ":" + leftPathZero(second);
    }

    private static String leftPathZero(long value) {
        String result = String.valueOf(value);
        if (result.length() <= 1) {
            return "0" + result;
        }
        return result;
    }
}
