package ru.javawebinar.topjava.util;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS.SSS");
    public static final ZoneOffset ZONE_OFFSET = ZoneId.of("Europe/Moscow").getRules().getOffset(Instant.now());

    // HSQLDB doesn't support LocalDate.MIN/MAX
    private static final LocalDate MIN_DATE = LocalDate.of(1, 1, 1);
    private static final LocalDate MAX_DATE = LocalDate.of(3000, 1, 1);

    private DateTimeUtil() {
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }


    public static LocalDateTime getStartInclusive(LocalDate localDate) {
        return startOfDay(localDate != null ? localDate : MIN_DATE);
    }

    public static LocalDateTime getEndExclusive(LocalDate localDate) {
        return startOfDay(localDate != null ? localDate.plus(1, ChronoUnit.DAYS) : MAX_DATE);
    }

    private static LocalDateTime startOfDay(LocalDate localDate) {
        return LocalDateTime.of(localDate, LocalTime.MIN);
    }
}

