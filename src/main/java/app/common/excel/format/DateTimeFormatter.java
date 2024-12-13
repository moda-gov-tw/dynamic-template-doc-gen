package app.common.excel.format;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public final class DateTimeFormatter {

    private DateTimeFormatter() {
        throw new AssertionError();
    }

    public static LocalDate asLocalDate(final Object value, final CellFormat cellFormat) {
        return (LocalDate) value;
    }

    public static Date asDate(final Object value, final CellFormat cellFormat) {
        return (Date) value;
    }

    public static LocalDateTime asLocalDateTime(final Object value, final CellFormat cellFormat) {
        if (value instanceof LocalDateTime) {
            return (LocalDateTime) value;
        }
        if (value instanceof Instant) {
            return LocalDateTime.ofInstant(((Instant) value), ZoneOffset.systemDefault());
        }
        return null;
    }
}
