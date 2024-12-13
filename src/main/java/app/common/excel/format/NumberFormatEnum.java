package app.common.excel.format;

import app.common.constant.Constant;
import app.common.enums.CodeEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@RequiredArgsConstructor
public enum NumberFormatEnum implements CodeEnum<String> {

    raw("", "原始數值", false),
    bidMoney(Constant.MONEY_FORMAT, "三位分節小數三位", true),
    threeDigit(Constant.THREE_DIGIT_FORMAT, "三位分節無小數", true),
    twoDecimal("#,###.##", "三位分節小數兩位", true),
    percent1Digit("0.0%", "百分比一位小數", true),
    percent2Digit("0.00%", "百分比兩位小數", true),
    custom("", "自訂格式", true),

    ;

    private final String code;
    private final String desc;
    private final boolean defaultZero;

    public Number toNumber(final Object value) {
        if (value instanceof Number) {
            return (Number) value;
        } else if (value == null) {
            return defaultZero ? 0 : null;
        }
        final String basicText = Objects.toString(value);
        final String numberText = StringUtils.replace(basicText, ",", "");
        if (NumberUtils.isDigits(numberText)) {
            return new BigDecimal(numberText);
        } else {
            return defaultZero ? 0 : null;
        }
    }
}
