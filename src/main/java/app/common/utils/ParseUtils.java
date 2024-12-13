package app.common.utils;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Objects;

/**
 *
 */
public final class ParseUtils {
    /**
     * constructor.
     */
    private ParseUtils() {
    }

    public static Instant toInstant(final Object rawData) {
        if (rawData == null) {
            return null;
        }
        if (rawData instanceof Instant) {
            return (Instant)rawData;
        }
        final String str = Objects.toString(rawData);
        try {
            final Date parseDate = DateUtils.parseDate(str, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss.SS", "yyyy-MM-dd HH:mm:ss.SSS");
            return new Timestamp(parseDate.getTime()).toInstant();
        } catch (final ParseException e) {
            throw new RuntimeException("指定查詢資料類別有誤");
        }
    }

    public static LocalDate toLocalDate(final Object rawData) {
        if (rawData == null) {
            return null;
        }
        if (rawData instanceof LocalDate) {
            return (LocalDate)rawData;
        }
        if (rawData instanceof Instant) {
            return LocalDate.ofInstant(toInstant(rawData), ZoneOffset.systemDefault());
        }
        if (rawData instanceof Timestamp) {
            return ((Timestamp) rawData).toLocalDateTime().toLocalDate();
        }
        if (rawData instanceof java.sql.Date) {
            return ((java.sql.Date) rawData).toLocalDate();
        }
        return LocalDate.parse(Objects.toString(rawData));
    }

    public static LocalDateTime toLocalDateTime(final Object rawData) {
        if (rawData == null) {
            return null;
        }
        if (rawData instanceof LocalDateTime) {
            return (LocalDateTime)rawData;
        }
        if (rawData instanceof Instant) {
            return LocalDateTime.ofInstant(toInstant(rawData), ZoneOffset.systemDefault());
        }
        final String str = Objects.toString(rawData);
        try {
            final Date parseDate = DateUtils.parseDate(str, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss.SS", "yyyy-MM-dd HH:mm:ss.SSS");
            return new Timestamp(parseDate.getTime()).toLocalDateTime();
        } catch (final ParseException e) {
            throw new RuntimeException("指定查詢資料類別有誤");
        }
    }

    public static BigDecimal defaultDecimal(final Object rawData) {
        final BigDecimal decimal = toDecimal(rawData);
        return ObjectUtils.defaultIfNull(decimal, BigDecimal.ZERO);
    }

    public static BigDecimal toDecimal(final Object rawData) {
        if (rawData == null) {
            return null;
        }
        if (rawData instanceof BigDecimal) {
            return (BigDecimal) rawData;
        }
        if (rawData instanceof Double) {
            return BigDecimal.valueOf((Double) rawData);
        }
        if (rawData instanceof Number) {
            return BigDecimal.valueOf(((Number) rawData).doubleValue());
        }
        return new BigDecimal(Objects.toString(rawData));
    }

    public static String toString(final Object rawData) {
        if (rawData == null) {
            return null;
        }
        if (rawData instanceof String) {
            return (String) rawData;
        }
        return Objects.toString(rawData);
    }

    public static Integer toInteger(final Object str, final boolean primitive) {
        if (str == null) {
            return primitive ? 0 : null;
        }
        return NumberUtils.toInt(Objects.toString(str).trim());
    }

    public static Long toLong(final Object str, final boolean primitive) {
        if (str == null) {
            return primitive ? 0L : null;
        }
        return NumberUtils.toLong(Objects.toString(str).trim());
    }

    public static Float toFloat(final Object str, final boolean primitive) {
        if (str == null) {
            return primitive ? 0.0f : null;
        }
        return NumberUtils.toFloat(Objects.toString(str).trim());
    }

    public static Double toDouble(final Object str, final boolean primitive) {
        if (str == null) {
            return primitive ? 0.0 : null;
        }
        return NumberUtils.toDouble(Objects.toString(str).trim());
    }

    public static Boolean toBoolean(final Object str, final boolean primitive) {
        if (str == null) {
            return primitive ? false : null;
        }
        return Boolean.parseBoolean(Objects.toString(str).trim());
    }

    public static <T extends Enum<T>> T toEnum(final Class<T> enumClass, final Object str) {
        if (str == null) {
            return null;
        }
        return EnumUtils.getEnum(enumClass, str.toString());
    }

    public static String parseLocalDateToYYYMMDD(final LocalDate date, final String delimiter) {
        if (date == null) {
            return "";
        }
        return app.common.utils.DateUtils.parseLocalDateToYYYMMDD(date, delimiter);
    }

    public static String parseLocalDateToYYYMMDD(final LocalDate date) {
        return parseLocalDateToYYYMMDD(date, "");
    }

    public static String parseLocalDateToYYYMMDDBySlash(final LocalDate date) {
        return parseLocalDateToYYYMMDD(date, "/");
    }

    public static String numberFormat(final Number number, final String format) {
        if (number == null) {
            return "";
        }
        final DecimalFormat df = new DecimalFormat(format);
        return df.format(number);
    }

    public static Timestamp toTimestamp(final Instant instant) {
        if (instant == null) {
            return null;
        }
        return Timestamp.from(instant);
    }

    public static Number toNumber(final Object value, final Number defaultNumber) {
        if (value == null) {
            return defaultNumber;
        }
        if (value instanceof Number) {
            return (Number) value;
        }
        final String basicText = Objects.toString(value);
        final String numberText = StringUtils.replace(basicText, ",", "");
        if (NumberUtils.isParsable(numberText)) {
            return new BigDecimal(numberText);
        }
        return defaultNumber;
    }

    public static Number defaultNumber(final Object value) {
        return toNumber(value, 0);
    }

    public static Number toNumber(final Object value) {
        return toNumber(value, null);
    }
}
