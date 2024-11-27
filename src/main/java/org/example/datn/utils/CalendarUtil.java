package org.example.datn.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

import static java.time.LocalTime.MAX;
import static java.time.LocalTime.MIN;

public class CalendarUtil {

    private CalendarUtil() {
        throw new IllegalStateException("Utility class");
    }

    private static final ZoneId ZONE_ID_HCM = ZoneId.of("Asia/Ho_Chi_Minh");

    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String DATE_PATTERN2 = "dd/MM/yyyy";
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String MONTH_PATTERN = "MM/yyyy";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
    public static final DateTimeFormatter DATE_FORMATTER2 = DateTimeFormatter.ofPattern(DATE_PATTERN2);
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
    public static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormatter.ofPattern(MONTH_PATTERN);

    public static class DateTimeUtils {

        public DateTimeUtils() {
            throw new IllegalStateException("Utility class");
        }

        public static LocalDateTime now() {
            return LocalDateTime.now(ZONE_ID_HCM);
        }

        public static Long toMillis(LocalDateTime value) {
            return value.atZone(ZONE_ID_HCM).toInstant().toEpochMilli();
        }

        public static LocalDateTime of(Long value) {
            return Instant.ofEpochMilli(value).atZone(ZONE_ID_HCM).toLocalDateTime();
        }

        public static LocalDateTime of(Date date) {
            return LocalDateTime.ofInstant(date.toInstant(), ZONE_ID_HCM);
        }

        public static Date toJavaUtilDate(LocalDateTime value) {
            return Date.from(value.atZone(ZONE_ID_HCM).toInstant());
        }

        public static long toSeconds(LocalDateTime localDateTime) {
            return localDateTime.atZone(ZONE_ID_HCM).toEpochSecond();
        }

        public static LocalDateTime atStartOfDay(Long value) {
            return of(value).with(MIN);
        }

        public static LocalDateTime atStartOfDay(LocalDate val) {
            return val.atTime(MIN).atZone(ZONE_ID_HCM).toLocalDateTime();
        }

        public static LocalDateTime atStartOfDay() {
            return now().with(MIN);
        }

        public static Long atStartOfDayMillis(LocalDate val) {
            return toMillis(atStartOfDay(val));
        }

        public static String atStartOfDayWithFormat(LocalDate val) {
            return format(atStartOfDay(val));
        }

        public static LocalDateTime atStartOfDay(LocalDate val, Short dayOfMonth) {
            return val.withDayOfMonth(dayOfMonth).atTime(MIN).atZone(ZONE_ID_HCM).toLocalDateTime();
        }

        public static LocalDateTime atEndOfDay(Long value) {
            return of(value).with(MAX);
        }

        public static LocalDateTime atEndOfDay() {
            return now().with(MAX);
        }

        public static LocalDateTime atEndOfDay(LocalDate val, Short dayOfMonth) {
            return val.withDayOfMonth(dayOfMonth).atTime(MAX).atZone(ZONE_ID_HCM).toLocalDateTime();
        }

        public static LocalDateTime atEndOfDay(LocalDate val) {
            return val.atTime(MAX).atZone(ZONE_ID_HCM).toLocalDateTime();
        }

        public static LocalDateTime atStartOfMonth() {
            var yearMonth = YearMonth.from(now());
            return yearMonth.atDay(1).atStartOfDay(ZONE_ID_HCM).toLocalDateTime();
        }

        public static LocalDateTime atStartOfMonth(LocalDate date) {
            return date.withDayOfMonth(1).atTime(MIN).atZone(ZONE_ID_HCM).toLocalDateTime();
        }

        public static LocalDateTime atStartOfMonth(LocalDateTime date) {
            var yearMonth = YearMonth.from(date);
            return yearMonth.atDay(1).atStartOfDay(ZONE_ID_HCM).toLocalDateTime();
        }

        public static LocalDateTime atEndOfMonth() {
            var yearMonth = YearMonth.from(now());
            return  yearMonth.atEndOfMonth().atTime(MAX).atZone(ZONE_ID_HCM).toLocalDateTime();
        }

        public static LocalDateTime atEndOfMonth(LocalDate date) {
            return date.withDayOfMonth(date.lengthOfMonth()).atTime(MAX).atZone(ZONE_ID_HCM).toLocalDateTime();
        }

        public static boolean isValid(String date) {
            try {
                DATE_TIME_FORMATTER.parse(date);
                return true;
            } catch (DateTimeParseException ex) {
                return false;
            }
        }

        /**
         *
         * @param time
         * @return string date with format yyyy-MM-dd HH:mm:ss
         */
        public static String format(LocalDateTime time) {
            if (time == null) {
                return "";
            }
            return DATE_TIME_FORMATTER.format(time);
        }

        /**
         *
         * @param time
         * @return string date with format dd/MM/yyyy
         */
        public static String format2(LocalDateTime time) {
            if (time == null) {
                return "";
            }
            return DATE_FORMATTER2.format(time);
        }

        /**
         *
         * @param time
         * @return string date with format MM/yyyy
         */
        public static String format3(LocalDateTime time) {
            if (time == null) {
                return "";
            }
            return MONTH_FORMATTER.format(time);
        }
    }
}
