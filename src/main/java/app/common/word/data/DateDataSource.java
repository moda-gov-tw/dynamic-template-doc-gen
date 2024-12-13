package app.common.word.data;

import app.common.excel.format.DateTimeFormatEnum;
import app.common.excel.format.data.AbstractDataSource;
import app.common.utils.DateUtils;
import app.common.utils.ParseUtils;

public class DateDataSource extends AbstractDataSource {

    private final DateTimeFormatEnum dateTimeFormat;

    public DateDataSource(final String key, final Object defaultValue, final DateTimeFormatEnum dateTimeFormat) {
        super(key, defaultValue);
        this.dateTimeFormat = dateTimeFormat;
    }

    public DateDataSource(final String key, final DateTimeFormatEnum dateTimeFormat) {
        this(key, "", dateTimeFormat);
    }

    public DateDataSource(final String key) {
        this(key, "", DateTimeFormatEnum.rocDate);
    }

    @Override
    public Object asData(final Object obj) {
        final Object date = super.readProperty(obj);
        if (date == null) {
            return this.defaultValue;
        }
        switch (this.dateTimeFormat) {
            default:
            case raw:
            case rocDate:
                return DateUtils.getRocString(ParseUtils.toLocalDate(date));
            case rocDateTime:
                return DateUtils.parseLocalDateTime2String(ParseUtils.toLocalDateTime(date));
            case rocDateHourSec:
                return DateUtils.parseLocalDateTime2ShortString(ParseUtils.toLocalDateTime(date));
            case adDate:
            case adDateTime:
                // TODO
                return date;
        }
    }
}
