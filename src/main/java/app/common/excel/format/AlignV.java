package app.common.excel.format;

/**
 * 垂直對齊方式
 */
public enum AlignV {

    TOP(4),
    MIDDLE(5),
    BOTTOM(6),
    ;

    public final int code;

    AlignV(final int code) {
        this.code = code;
    }

    public static AlignV lookup(final int alignment) {
        switch (alignment) {
            case 4:
                return TOP;
            default:
            case 5:
                return MIDDLE;
            case 6:
                return BOTTOM;
        }
    }
}
