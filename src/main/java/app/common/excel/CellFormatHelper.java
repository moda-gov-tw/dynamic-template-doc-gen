package app.common.excel;

import app.common.excel.format.Border;
import app.common.excel.format.CellFormat;
import app.common.excel.format.EColor;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import java.awt.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class CellFormatHelper {

    private static final Map<Color, HSSFColorPredefined> COLOR_MAP;

    static {
        final Map<Color, HSSFColorPredefined> map = new HashMap<>();
        map.put(Color.LIGHT_GRAY, HSSFColorPredefined.GREY_25_PERCENT);
        map.put(Color.RED, HSSFColorPredefined.RED);
        map.put(Color.BLUE, HSSFColorPredefined.BLUE);
        map.put(Color.YELLOW, HSSFColorPredefined.YELLOW);
        map.put(Color.GREEN, HSSFColorPredefined.BRIGHT_GREEN);
        map.put(Color.BLACK, HSSFColorPredefined.BLACK);
        map.put(Color.DARK_GRAY, HSSFColorPredefined.GREY_80_PERCENT);
        map.put(Color.GRAY, HSSFColorPredefined.GREY_50_PERCENT);
        map.put(Color.ORANGE, HSSFColorPredefined.GOLD);
        map.put(Color.WHITE, HSSFColorPredefined.WHITE);
        COLOR_MAP = Collections.unmodifiableMap(map);
    }

    public static HorizontalAlignment getAlignH(final CellFormat cellFormat) {
        switch (cellFormat.getAlignH()) {
            case LEFT:
                return HorizontalAlignment.LEFT;
            case CENTER:
                return HorizontalAlignment.CENTER;
            case RIGHT:
                return HorizontalAlignment.RIGHT;
            case JUSTIFY:
                return HorizontalAlignment.JUSTIFY;
            default:
                return HorizontalAlignment.GENERAL;
        }
    }

    public static VerticalAlignment getAlignV(final CellFormat cellFormat) {
        switch (cellFormat.getAlignV()) {
            case TOP:
                return VerticalAlignment.TOP;
            default:
            case MIDDLE:
                return VerticalAlignment.CENTER;
            case BOTTOM:
                return VerticalAlignment.BOTTOM;
        }
    }

    public static Border getBorder(final CellFormat cellFormat) {
        return Objects.requireNonNullElse(cellFormat.getBorder(), Border.BOX);
    }

    public static Color getColor(final EColor eColor) {
        switch (eColor) {
            case white:
                return Color.white;
            case lightGray:
                return Color.lightGray;
            case gray:
                return Color.gray;
            case darkGray:
                return Color.darkGray;
            default:
            case black:
                return Color.black;
            case red:
                return Color.red;
            case pink:
                return Color.pink;
            case orange:
                return Color.orange;
            case yellow:
                return Color.yellow;
            case green:
                return Color.green;
            case blue:
                return Color.blue;
        }
    }

    public static short lookupColorIndex(final Color color, final HSSFColorPredefined defaultColor) {
        final HSSFColorPredefined hColor = COLOR_MAP.get(color);
        if (hColor == null) {
            return defaultColor.getIndex();
        }
        return hColor.getIndex();
    }
}
