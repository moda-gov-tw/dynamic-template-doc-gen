package app.common.excel.format;

/**
 * 水平對齊方式
 */
public enum AlignH {

    LEFT(0), //
    CENTER(1), //
    RIGHT(2), //
    JUSTIFY(3), //
    ;
    public final int code;

    AlignH(final int code) {
        this.code = code;
    }

    public static AlignH lookup(final int alignment) {
        switch (alignment) {
            default:
            case 0:
                return LEFT;
            case 1:
                return CENTER;
            case 2:
                return RIGHT;
            case 3:
                return JUSTIFY;
        }
    }
}
