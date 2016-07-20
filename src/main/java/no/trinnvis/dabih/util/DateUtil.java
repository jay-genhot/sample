package no.trinnvis.dabih.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
    private static final String TIME_ZONE = "Europe/Oslo";
    public static final TimeZone timeZone = TimeZone.getTimeZone(TIME_ZONE);
    private static final DateUtil instance = new DateUtil();
    private static final String DEFAULT_FORMAT = "dd.MM.yyyy";
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public static DateUtil getInstance() {
        return DateUtil.instance;
    }

    public static LocalDateTime parseIsoDateTime(String dateTimeString) {
        LocalDateTime designedTimestamp = null;
        try {
            designedTimestamp = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } catch (Exception e) {
            designedTimestamp = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        }

        return designedTimestamp;
    }

    public static String todayAsString() {
        Date d = new Date();
        SimpleDateFormat format = new SimpleDateFormat(DEFAULT_FORMAT);

        return format.format(d);
    }

    public static String format(Date date) {
        return formatDateTime(date, DATE_FORMAT);
    }

    public static String formatDateTime(Date date, String format) {
        if (date == null)
            return null;
        try {
            DateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static LocalDate getMonday(LocalDate date) {

        int d = date.getDayOfWeek().getValue() - 1;

        return date.minusDays(d);

    }

    public static Date getIat() {
        return new Date(Instant.now().getEpochSecond() * 1000);
    }

    public static LocalDateTime getIatTime() {
        return LocalDateTime.now(ZoneId.of(TIME_ZONE)).withNano(0);
    }

    /**
     * convert date to local date time
     * @param date
     * @return
     */
    public static LocalDateTime convertFromDate(Date date) {
        if (date == null) {
            return null;
        }

        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.of(TIME_ZONE));
    }

    /**
     * convert date from local date time
     * @param localDateTime
     * @return
     */
    public static Date convertFromLocalDateTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return Date.from(localDateTime.atZone(ZoneId.of(TIME_ZONE)).toInstant());
    }
}
