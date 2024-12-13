package app.common.excel.format.data;

import app.common.utils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 多欄位資料來源
 */
public class MultiKeySource implements CellDataSource {

    private String[] keys;

    private CharSequence prefix;

    private CharSequence delimiter;

    private CharSequence suffix;

    public MultiKeySource(final String[] keys, final CharSequence prefix, final CharSequence delimiter, final CharSequence suffix) {
        this.keys = keys;
        this.prefix = prefix;
        this.delimiter = delimiter;
        this.suffix = suffix;
    }

    public MultiKeySource(final String[] keys, final CharSequence delimiter) {
        this(keys, "", delimiter, "");
    }

    public MultiKeySource(final String[] keys, final CharSequence prefix, final CharSequence delimiter) {
       this(keys, prefix, delimiter, "");
    }

    @Override
    public Object asData(final Object obj) {
        final String[] values = new String[this.keys.length];
        for (int i = 0; i < this.keys.length; i++) {
            final Object value = PropertyUtils.readProperty(obj, this.keys[i]);
            values[i] = Objects.toString(value, "");
        }
        return Arrays.stream(values)
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.joining(this.delimiter, this.prefix, this.suffix));
    }
}
