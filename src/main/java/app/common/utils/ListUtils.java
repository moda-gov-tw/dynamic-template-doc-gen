package app.common.utils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * List 工具類
 **/
public final class ListUtils {

    private ListUtils() {
        throw new AssertionError();
    }

    /**
     * 取得 List 中指定位置元素，若超過List大小則回傳null。
     * @param list List物件
     * @param index 指定的index
     **/
    public static <T> T get(final List<T> list, final int index) {
        return getOrDefault(list, index, null);
    }

    /**
     * 取得 List 中指定位置元素，或回傳預設值。
     * @param list List物件
     * @param index 指定的index
     * @param defaultValue 預設值
     **/
    public static <T> T getOrDefault(
            final List<T> list,
            final int index,
            final T defaultValue
    ) {
        if (list == null || list.size() <= index || index < 0) {
            return defaultValue;
        }
        return list.get(index);
    }

    /**
     * 取得 List 中指定位置元素，或回傳預設值。
     * @param list List物件
     * @param index 指定的index
     * @param defaultValueSupplier 預設值
     **/
    public static <T> T getOrSupplier(
            final List<T> list,
            final int index,
            final Supplier<T> defaultValueSupplier
    ) {
        if (list == null || list.size() <= index || index < 0) {
            return defaultValueSupplier.get();
        }
        return list.get(index);
    }

    /**
     * List 是否為空，或List中所有元素皆為空。
     * @param list List物件
     **/
    public static <K, V> boolean isEmpty(final List<Map<K, V>> list) {
        if (CollectionUtils.isEmpty(list)) {
            return true;
        }
        for (Map<K, V> map : list) {
            if (MapUtils.isNotEmpty(map)) {
                return false;
            }
        }
        return true;
    }

    /**
     * List 是否不為空，或List中所有元素皆不為空。
     * @param list List物件
     **/
    public static <K, V> boolean isNotEmpty(final List<Map<K, V>> list) {
        return !isEmpty(list);
    }

}
