package app.common.excel.format.data;

import java.util.function.Function;

public class DynamicSource implements CellDataSource {

    private final Function<Object, Object> function;

    public DynamicSource(final Function<Object, Object> function) {
        this.function = function;
    }

    @Override
    public Object asData(final Object obj) {
        return function.apply(obj);
    }

    @Override
    public String getKey() {
        return CellDataSource.super.getKey();
    }
}
