package app.common.excel.format.data;

import app.common.enums.CodeEnum;
import app.common.enums.EnumUtil;
import app.common.utils.PropertyUtils;

import java.util.Objects;

public class CodeTableSource<T extends CodeEnum<?>> extends AbstractDataSource implements CellDataSource {

    private final Class<T> enumClass;

    public CodeTableSource(final String key, final Class<T> enumClass) {
        this(key, enumClass, null);
    }

    public CodeTableSource(final String key, final Class<T> enumClass, final String defaultValue) {
        super(key, defaultValue);
        this.enumClass = enumClass;
    }

    @Override
    public Object asData(final Object obj) {
        final Object code = PropertyUtils.readProperty(obj, this.key);
        return EnumUtil.lookup(code, enumClass)
                .map(CodeEnum::getDesc)
                .orElseGet(
                        () -> null == defaultValue
                                ? Objects.toString(code, "")
                                : Objects.toString(defaultValue, "")
                );
    }
}
