package app.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public final class DateUtils {
    private final static String [] enMonth = {"", "January", "February", "March", "April", "May", "June", "July", "August",
            "September", "October", "November", "December"};

    public DateUtils() {
    }

    /**
     * 日期格式轉換，從report Formatter搬來的
     */
    public static String parseLocalDateToYYYMMDD(LocalDate localDate) {
        String rocString = null;
        if (localDate != null) {
            rocString = String.format("%03d", localDate.getYear() - 1911) + "年"
                    + String.format("%02d", localDate.getMonthValue()) + "月"
                    + String.format("%02d", localDate.getDayOfMonth()) + "日";
        }
        return rocString;
    }

    public static String parseLocalDateToYYYMMDD(LocalDate localDate, String delimiter) {
        String rocString = null;
        if (localDate != null) {
            rocString = String.format("%03d", localDate.getYear() - 1911) + delimiter
                    + String.format("%02d", localDate.getMonthValue()) + delimiter
                    + String.format("%02d", localDate.getDayOfMonth());
        }
        return rocString;
    }

    public static String parseLocalDateToYYYMM(LocalDate localDate) {
        String rocString = null;
        if (localDate != null) {
            rocString = String.format("%03d", localDate.getYear() - 1911) + "年度"
                    + String.format("%02d", localDate.getMonthValue()) + "月份";
        }
        return rocString;
    }

    public static String parseLocalDateToYYYMM(LocalDate localDate, String delimiter) {
        String rocString = null;
        if (localDate != null) {
            rocString = String.format("%03d", localDate.getYear() - 1911) + delimiter
                    + String.format("%02d", localDate.getMonthValue());
        }
        return rocString;
    }

    public static String parseLocalDateTime2String(final LocalDateTime localDateTime) {
        final String rocYear = String.format("%03d", localDateTime.getYear() - 1911);
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("/MM/dd HH:mm:ss");
        return rocYear + formatter.format(localDateTime);
    }

    public static String parseLocalDateTime2ShortString(final LocalDateTime localDateTime) {
        final String rocYear = String.format("%03d", localDateTime.getYear() - 1911);
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("/MM/dd HH:mm");
        return rocYear + formatter.format(localDateTime);
    }

    public static String getRocString(LocalDate localDate) {
        return parseLocalDateToYYYMMDD(localDate, "/");
    }

    public static String getRocStringYYYMMDD(LocalDate localDate) {
        return parseLocalDateToYYYMMDD(localDate, "");
    }

    public static String getRocZhString(LocalDate localDate) {
        if (localDate != null) {
            return "民國" + parseLocalDateToYYYMMDD(localDate);
        }
        return "民國000年00月00日";
    }

    public static LocalDate getLastDayOfMonth(Integer year, Integer month) {
        Integer lastDayOfMonth = LocalDate.of(year, month, 1).with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
        return LocalDate.of(year, month, lastDayOfMonth);
    }

    public static String convertToTwDate(final String adDate) {
        if (adDate == null || adDate.length() <= 4) {
            return adDate;
        }

        int adYr = Integer.valueOf(adDate.substring(0, adDate.length() - 4));
        if (adYr < 1911) {
            return adDate;
        }

        String result = String.valueOf(adYr - 1911) + adDate.substring(adDate.length() - 4);

        return result.length() >= 7 ? result : "0" + result;
    }

    public static String convertToTwDate(final LocalDateTime adDate) {
        if (adDate == null) {
            return null;
        }

        String year = String.format("%03d", adDate.getYear() - 1911);
        DateTimeFormatter monthDateformatter = DateTimeFormatter.ofPattern("MMdd");

        return year + adDate.format(monthDateformatter);
    }

    public static LocalDate rocToLocalDate(String rocDate) {
        if (StringUtils.isEmpty(rocDate) || rocDate.length() < 7 || "0000000".equals(rocDate)) {
            rocDate = parseLocalDateToYYYMMDD(LocalDate.now(), "");
        }

        String adDate = "";
        if (rocDate.length() == 7) {
            if (!isValidRocDate(rocDate)) {
                rocDate = parseLocalDateToYYYMMDD(LocalDate.now(), "");
            }
            int yyy = Integer.parseInt(rocDate.substring(0, 3));

            adDate = (yyy + 1911) + "-" +
                    rocDate.substring(3, 5) + "-" +
                    rocDate.substring(5, 7);
        } else if (rocDate.length() == 8) {
            //西元年
            if (!isValidDate(rocDate)) {
                rocDate = rocToAdDate(parseLocalDateToYYYMMDD(LocalDate.now(), ""));
            }
            adDate = rocDate.substring(0, 4) + "-" +
                    rocDate.substring(4, 6) + "-" +
                    rocDate.substring(6, 8);
        }

        System.out.println("roc to ad = " + adDate);
        return LocalDate.parse(adDate);
    }

    public static String rocToAdDate(String rocDate) {
        int yyy = Integer.parseInt(rocDate.substring(0, 3));
        return (yyy + 1911) +  rocDate.substring(3, 5) + rocDate.substring(5, 7);
    }

    private static boolean isValidDate(String date) {
        try {
            return date.equals(new SimpleDateFormat("yyyyMMdd").format(new SimpleDateFormat("yyyyMMdd").parse(date)));
        } catch (ParseException e) {
            return false;
        }
    }

    private static boolean isValidRocDate(String date) {
        try {
            date = StringUtils.leftPad(date, 7, "0");
            date =
                    (Integer.valueOf(date.substring(0, 3)) + 1911) + date.substring(3, 5)
                            + date.substring(5, 7);

            return date.equals(new SimpleDateFormat("yyyyMMdd").format(new SimpleDateFormat(
                    "yyyyMMdd").parse(date)));
        } catch (ParseException e) {
            return false;
        }
    }

    public static Instant localDateToInstant(LocalDate localDate) {
        return  localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
    }

    public static String formatTwDateString(String dateStr) {
        if (dateStr == null || dateStr.length() < 6) {
            return dateStr;
        }

        return dateStr.substring(0, dateStr.length() - 4) + "/" +  dateStr.substring(dateStr.length() - 4, dateStr.length() - 2) + "/" +  dateStr.substring(dateStr.length() - 2);
    }

    /**
     * LocalDate 轉 Date
     */
    public static Date asDate(final LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * LocalDateTime 轉 Date
     */
    public static Date asDate(final LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Date 轉 LocalDate
     */
    public static LocalDate asLocalDate(final Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Date 轉 LocalDateTime
     */
    public static LocalDateTime asLocalDateTime(final Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }


    public static String convertToTwDateWithDelimiter(final LocalDateTime adDate, String delimiter) {
        if (adDate == null) {
            return null;
        }

        String year = String.format("%03d", adDate.getYear() - 1911);
        DateTimeFormatter monthDateformatter = DateTimeFormatter.ofPattern("MM" + delimiter + "dd");

        return year + delimiter + adDate.format(monthDateformatter);
    }

    public static LocalDate getSeasonDateStart(final int yyyy, final int season) {
        switch (season) {
            case 1:
                return LocalDate.of(yyyy, 1, 1);
            case 2:
                return LocalDate.of(yyyy, 4, 1);
            case 3:
                return LocalDate.of(yyyy, 7, 1);
            case 4:
                return LocalDate.of(yyyy, 10, 1);
            default:
                return null;
        }
    }

    public static LocalDate getSeasonDateEnd(final int yyyy, final int season) {
        switch (season) {
            case 1:
                return LocalDate.of(yyyy, 3, 31);
            case 2:
                return LocalDate.of(yyyy, 6, 30);
            case 3:
                return LocalDate.of(yyyy, 9, 30);
            case 4:
                return LocalDate.of(yyyy, 12, 31);
            default:
                return null;
        }
    }

    public static String convertToEnDate(LocalDate localDate){
        if(localDate == null){
            return "";
        }

        String year = String.format("%03d", localDate.getYear() - 1911);
        String month = String.format("%02d", localDate.getMonthValue());
        String day = String.format("%02d", localDate.getDayOfMonth());

        String result = enMonth[(parseInt(month))] + " " + parseInt(day) + ", " + (parseInt(year) + 1911);

        return result;
    }



    public static List<LocalDate> getFolderDates(String folderPath) throws IOException {
        Path path = Paths.get(folderPath);

        List<LocalDate> folderDates = Files.list(path)
                .filter(Files::isDirectory)
                .map(folder -> folder.getFileName().toString())
                .map(dateString -> LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        return folderDates;
    }
}
