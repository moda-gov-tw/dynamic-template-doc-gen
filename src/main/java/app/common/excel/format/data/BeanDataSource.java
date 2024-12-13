package app.common.excel.format.data;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * BEAN 資料來源
 */
public class BeanDataSource extends AbstractDataSource implements CellDataSource {

    public BeanDataSource(final String key) {
        this(key, null);
    }

    public BeanDataSource(final String key, final Object defaultValue) {
        super(key, defaultValue);
    }

    @Override
    public Object asData(final Object obj) {
        final Object value = readProperty(obj);
        if (value instanceof String && StringUtils.isBlank(Objects.toString(value, null))) {
            return this.defaultValue;
        }
        return ObjectUtils.defaultIfNull(value, this.defaultValue);
    }
}
