package app.common.excel.format.data;

import app.common.utils.PropertyUtils;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractDataSource implements CellDataSource {

    protected final String key;

    protected final Object defaultValue;

    protected Object readProperty(final Object obj) {
        return PropertyUtils.readProperty(obj, this.key);
    }

    @Override
    public String getKey() {
        return this.key;
    }
}
