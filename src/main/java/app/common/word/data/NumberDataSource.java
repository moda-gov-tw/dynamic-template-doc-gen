package app.common.word.data;

import app.common.enums.DecimalFormatEnum;
import app.common.excel.format.data.AbstractDataSource;
import app.common.utils.ParseUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Objects;

public class NumberDataSource extends AbstractDataSource {

    private final DecimalFormatEnum decimalFormat;

    public NumberDataSource(final String key, final Object defaultValue, final DecimalFormatEnum decimalFormat) {
        super(key, defaultValue);
        this.decimalFormat = decimalFormat;
    }

    public NumberDataSource(final String key, final DecimalFormatEnum decimalFormat) {
        this(key, "", decimalFormat);
    }

    public NumberDataSource(final String key) {
        this(key, "", DecimalFormatEnum.raw);
    }

    @Override
    public Object asData(Object obj) {
        final Object value = super.readProperty(obj);
        if (value == null) {
            return this.defaultValue;
        }
        if (DecimalFormatEnum.raw.equals(this.decimalFormat)) {
            return Objects.toString(value, "");
        }
        if (NumberUtils.isParsable(Objects.toString(value))) {
            final Number number = ParseUtils.toNumber(value);
            return ParseUtils.numberFormat(number, this.decimalFormat.getCode());
        }
        return value;
    }
}
