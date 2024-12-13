package app.common.excel.format.data;

import app.common.enums.CodeEnum;

import java.util.function.Function;
import java.util.function.Supplier;

public interface DataSourceHelper {
    /**
     * 單一欄位屬性
     * @param key 資料欄位 key
     */
    default CellDataSource dataSource(String key) {
        return new BeanDataSource(key);
    }

    /**
     * 單一欄位屬性
     * @param key 資料欄位 key
     * @param defaultValue 預設值
     */
    default CellDataSource dataSource(String key, Object defaultValue) {
        return new BeanDataSource(key, defaultValue);
    }

    /**
     * 多欄位組成
     * @param delimiter 串接字串
     * @param keys 資料欄位 key
     */
    default CellDataSource multiKeySource(String delimiter, String... keys) {
        return new MultiKeySource(keys, delimiter);
    }

    /**
     * 多欄位組成
     * @param prefix 開頭字串
     * @param delimiter 串接字串
     * @param keys 資料欄位 key
     */
    default CellDataSource multiKeySource(final CharSequence prefix, final CharSequence delimiter, final String[] keys) {
        return new MultiKeySource(keys, prefix, delimiter);
    }

    /**
     * 多欄位組成
     * @param prefix 開頭字串
     * @param delimiter 串接字串
     * @param suffix 結尾字串
     * @param keys 資料欄位 key
     */
    default CellDataSource multiKeySource(final CharSequence prefix, final CharSequence delimiter, final CharSequence suffix, final String[] keys) {
        return new MultiKeySource(keys, prefix, delimiter, suffix);
    }

    /**
     * 常數欄位屬性
     * @param value 常數
     */
    default <T> CellDataSource constSource(final T value) {
        return new ConstantSource<>(value);
    }

    /**
     * 常數欄位屬性
     * @param supplier 常數 supplier
     */
    default <T> CellDataSource constSource(final Supplier<T> supplier) {
        return new ConstantSource<>(supplier);
    }

    /**
     * 列舉欄位屬性
     * @param key 資料欄位 key
     * @param enumClass 列舉 class
     * @param defaultValue 預設值
     */
    default <T extends CodeEnum<?>> CellDataSource codeSource(String key, Class<T> enumClass, String defaultValue) {
        return new CodeTableSource<>(key, enumClass, defaultValue);
    }

    /**
     * 列舉欄位屬性
     * @param key 資料欄位 key
     * @param enumClass 列舉 class
     */
    default <T extends CodeEnum<?>> CellDataSource codeSource(String key, Class<T> enumClass) {
        return new CodeTableSource<>(key, enumClass);
    }

    /**
     * 自訂欄位屬性
     * @param function 自訂 function
     */
    default CellDataSource dynamicSource(final Function<Object, Object> function) {
        return new DynamicSource(function);
    }

}
