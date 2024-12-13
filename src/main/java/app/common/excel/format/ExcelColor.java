package app.common.excel.format;

import app.common.excel.CellFormatHelper;
import lombok.Getter;

import java.awt.*;
import java.util.Objects;

@Getter
public class ExcelColor {

    private final ColorType type;

    private final EColor color;

    private final String hex;

    private ExcelColor(ColorType type, EColor color, String hex) {
        this.type = type;
        this.color = color;
        this.hex = hex;
    }

    public static ExcelColor hexColor(final String hex) {
        return new ExcelColor(ColorType.hexColor, null, hex);
    }

    public static ExcelColor excelColor(final EColor color) {
        return new ExcelColor(ColorType.excelColor, color, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExcelColor that = (ExcelColor) o;
        return type == that.type && color == that.color && Objects.equals(hex, that.hex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, color, hex);
    }

    public Color getAwtColor() {
        switch (this.type) {
            case hexColor:
                final int[] rgb = new int[3];
                for (int i = 0; i < 3; i++) {
                    rgb[i] = Integer.parseInt(this.hex.substring(i * 2, i * 2 + 2), 16);
                }
                return new Color(rgb[0], rgb[1], rgb[2]);
            case excelColor:
                return CellFormatHelper.getColor(this.color);
        }
        return null;
    }
}
