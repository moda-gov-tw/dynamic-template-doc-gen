package app.common.excel.format;

import app.common.enums.CodeEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DateTimeFormatEnum implements CodeEnum<String> {
    raw("", "不指定"),
    // 民國
    rocDate("[$-404]e/MM/dd", "民國格式"),
    rocDateTime("[$-404]e/MM/dd HH:mm:ss", "民國格式"),
    rocDateHourSec("[$-404]e/MM/dd HH:mm", "民國格式"),
    // 西元
    adDate("yyyy/MM/dd", "西元格式"),
    adDateTime("yyyy/MM/dd HH:mm:ss", "西元格式"),

    ;
    private final String code;
    private final String desc;
}
