package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static boolean isBetween(LocalDate ld, LocalDate startDate, LocalDate endDate) {
        return ld.compareTo(startDate) >= 0 && ld.compareTo(endDate) <=0;
    }

    public static LocalDate getStartDate(String date) {
        try {
            return LocalDate.parse(date);
        } catch (Exception e) {
            return LocalDate.MIN;
        }
    }

    public static LocalDate getEndDate(String date) {
        try {
            return LocalDate.parse(date);
        } catch (Exception e) {
            return LocalDate.MAX;
        }
    }

    public static LocalTime getStartTime(String time) {
        try {
            return LocalTime.parse(time);
        } catch (Exception e) {
            return LocalTime.MIN;
        }
    }

    public static LocalTime getEndTime(String time) {
        try {
            return LocalTime.parse(time);
        } catch (Exception e) {
            return LocalTime.MAX;
        }
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

