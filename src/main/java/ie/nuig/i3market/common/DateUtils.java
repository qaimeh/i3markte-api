package ie.nuig.i3market.common;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public final class DateUtils {

    public static final String DEFAULT_TIME_ZONE = "GMT";
    public static final String DATE_TIME_WITH_ZONE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.S'Z'";

    private DateUtils() {
    }

    public static DateTimeFormatter getISO8601DateFormat() {

        return DateTimeFormatter
                .ofPattern(DATE_TIME_WITH_ZONE_FORMAT)
                .withZone(ZoneId.of(DEFAULT_TIME_ZONE));
    }
}
