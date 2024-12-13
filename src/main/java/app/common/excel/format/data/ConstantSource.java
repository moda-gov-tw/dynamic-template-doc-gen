package app.common.excel.format.data;

import java.util.function.Supplier;

public class ConstantSource<T> implements CellDataSource {

    private final T value;

    public ConstantSource(final T value) {
        this.value = value;
    }

    public ConstantSource(final Supplier<T> supplier) {
        this.value = supplier.get();
    }

    @Override
    public Object asData(final Object obj) {
        return value;
    }
}
