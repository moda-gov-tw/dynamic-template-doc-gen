package app.common.excel.format;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Objects;

public class CellFormat implements Cloneable {
    /** 字體. */
    private FontType fontType;
    /** 背景顏色. */
    private ExcelColor backgroundColor;
    /** 框線. */
    private Border border;
    /** 文字格式. */
    private String textFormat;
    /** 水平對齊方式. */
    private AlignH alignH;
    /** 垂直對齊方式. */
    private AlignV alignV;

    private NumberFormatEnum numberFormat = NumberFormatEnum.raw;

    private DateTimeFormatEnum dateTimeFormat = DateTimeFormatEnum.rocDate;

    public CellFormat() {
        // Default
    }

    public CellFormat(final AlignH alignH) {
        this.alignH = alignH;
    }

    public CellFormat(final AlignV alignV) {
        this.alignV = alignV;
    }

    public CellFormat(final AlignV alignV, final AlignH alignH) {
        this.alignV = alignV;
        this.alignH = alignH;
    }

    public CellFormat(final AlignH alignH, final AlignV alignV) {
        this.alignV = alignV;
        this.alignH = alignH;
    }

    public CellFormat(final Border border) {
        this.setBorder(border);
    }

    public FontType getFontType() {
        return fontType;
    }

    public FontType fontType() {
        return this.getFontType();
    }

    public CellFormat setFontType(FontType fontType) {
        this.fontType = fontType;
        return this;
    }

    public ExcelColor getBackgroundColor() {
        return backgroundColor;
    }

    public CellFormat setBackgroundColor(EColor color) {
        this.backgroundColor = ExcelColor.excelColor(color);
        return this;
    }

    public CellFormat setBackgroundColor(String hexColor) {
        this.backgroundColor = ExcelColor.hexColor(hexColor);
        return this;
    }

    public CellFormat backgroundColor(EColor color) {
        return this.setBackgroundColor(color);
    }

    public CellFormat backgroundColor(String hexColor) {
        return this.setBackgroundColor(hexColor);
    }

    public Border getBorder() {
        return border;
    }

    public CellFormat setBorder(Border border) {
        this.border = border;
        return this;
    }

    public CellFormat border(Border border) {
        return this.setBorder(border);
    }

    public String getTextFormat() {
        return textFormat;
    }

    public CellFormat setTextFormat(String textFormat) {
        this.textFormat = textFormat;
        return this;
    }

    public CellFormat textFormat(String textFormat) {
        return this.setTextFormat(textFormat);
    }

    public AlignH getAlignH() {
        return alignH;
    }

    public CellFormat setAlignH(AlignH alignH) {
        this.alignH = alignH;
        return this;
    }

    public CellFormat alignH(AlignH alignH) {
        return this.setAlignH(alignH);
    }

    public AlignV getAlignV() {
        return alignV;
    }

    public CellFormat setAlignV(AlignV alignV) {
        this.alignV = alignV;
        return this;
    }

    public CellFormat alignV(AlignV alignV) {
        return this.setAlignV(alignV);
    }

    public CellFormat setAlign(final AlignV alignV, final AlignH alignH) {
        this.setAlignH(alignH);
        this.setAlignV(alignV);
        return this;
    }

    public CellFormat setAlign(final AlignH alignH, final AlignV alignV) {
        this.setAlignH(alignH);
        this.setAlignV(alignV);
        return this;
    }

    public NumberFormatEnum getNumberFormat() {
        return numberFormat;
    }

    public CellFormat setNumberFormat(NumberFormatEnum numberFormat) {
        this.numberFormat = numberFormat;
        return this;
    }

    public CellFormat numberFormat(NumberFormatEnum numberFormat) {
        return this.setNumberFormat(numberFormat);
    }

    public DateTimeFormatEnum getDateTimeFormat() {
        return dateTimeFormat;
    }

    public CellFormat setDateTimeFormat(DateTimeFormatEnum dateTimeFormat) {
        this.dateTimeFormat = dateTimeFormat;
        return this;
    }

    public CellFormat dateTimeFormat(DateTimeFormatEnum dateTimeFormat) {
        return this.setDateTimeFormat(dateTimeFormat);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CellFormat that = (CellFormat) o;
        return java.util.Objects.equals(fontType, that.fontType)
                && Objects.equals(backgroundColor, that.backgroundColor)
                && border == that.border
                && Objects.equals(textFormat, that.textFormat)
                && numberFormat == that.numberFormat
                && dateTimeFormat == that.dateTimeFormat
                && alignH == that.alignH
                && alignV == that.alignV;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fontType, backgroundColor, border, textFormat, numberFormat, dateTimeFormat, alignH, alignV);
    }

    @Override
    public CellFormat clone() {
        final Gson gson = new GsonBuilder()
                .serializeNulls()
                .create();
        return gson.fromJson(gson.toJson(this), CellFormat.class);
    }
}
