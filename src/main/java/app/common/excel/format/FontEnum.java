package app.common.excel.format;

import app.common.enums.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FontEnum implements CodeEnum<String> {

    PMingLiU("0", "新細明體"),

    KaiU("1", "標楷體"),

    ;

    private final String code;
    private final String desc;
}
