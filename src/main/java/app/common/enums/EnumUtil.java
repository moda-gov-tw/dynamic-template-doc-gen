package app.common.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Optional;

public class EnumUtil {
    public static <T extends CodeEnum<Integer>> T getByCode(Integer code, Class<T> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants())
                .filter(e -> code.equals(e.getCode()))
                .findAny()
                .orElse(null);
    }

    public static <T extends CodeEnum<String>> T getByCode(String code, Class<T> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants())
                .filter(e -> code.equals(e.getCode()))
                .findAny()
                .orElse(null);
    }

    public static <T extends CodeEnum<?>> Optional<T> lookup(final Object code, final Class<T> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants())
                .filter(e -> e.getCode().equals(code))
                .findFirst();
    }

    public static <T, E extends CodeEnum<T>> Optional<E> lookupByName(final String name, final Class<E> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants())
                .filter(e -> StringUtils.equals(name, e.toString()))
                .findFirst();
    }
}
