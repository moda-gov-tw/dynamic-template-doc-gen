package app.common.enums;

import app.common.constant.Constant;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DecimalFormatEnum implements CodeEnum<String> {

    // #,##0.000
    moneyFormat(Constant.MONEY_FORMAT, "三位分節小數三位"),
    threeDigitFormat(Constant.THREE_DIGIT_FORMAT, "三位分節"),
    threeDigit("#,###", "三位分節"),
    twoDecimal("#,###.##", "三位分節小數兩位"),
    raw("", "原始格式"),
    percent1Digit("0.0%", "百分比一位小數"),
    percent2Digit("0.00%", "百分比兩位小數"),

    ;
    private final String code;
    private final String desc;

}
