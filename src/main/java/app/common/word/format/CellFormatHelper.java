package app.common.word.format;

import app.common.excel.format.CellFormat;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;

public final class CellFormatHelper {

    //================================================
    //== word XWPFParagraph
    //================================================

    public static ParagraphAlignment getParagraphAlignH(final CellFormat cellFormat) {
        switch (cellFormat.getAlignH()) {
            default:
            case LEFT:
                return ParagraphAlignment.LEFT;
            case RIGHT:
                return ParagraphAlignment.RIGHT;
            case CENTER:
            case JUSTIFY:
                return ParagraphAlignment.CENTER;
        }
    }

    //================================================
    //== word XWPFTableCell Vertical Alignment
    //================================================

    public static XWPFTableCell.XWPFVertAlign getVerticalAlignment(final CellFormat cellFormat) {
        switch (cellFormat.getAlignV()) {
            default:
            case TOP:
                return XWPFTableCell.XWPFVertAlign.TOP;
            case MIDDLE:
                return XWPFTableCell.XWPFVertAlign.CENTER;
            case BOTTOM:
                return XWPFTableCell.XWPFVertAlign.BOTTOM;
        }
    }
}
