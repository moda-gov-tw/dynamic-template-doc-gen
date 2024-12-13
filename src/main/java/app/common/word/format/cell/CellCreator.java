package app.common.word.format.cell;

import com.deepoove.poi.data.CellRenderData;
import com.deepoove.poi.data.Cells;
import com.deepoove.poi.data.ParagraphRenderData;
import com.deepoove.poi.data.Paragraphs;
import com.deepoove.poi.data.style.Style;
import app.common.excel.format.AlignV;
import app.common.excel.format.CellFormat;

public class CellCreator {

    public static CellRenderData createCell(final String text, final CellFormat cellFormat) {
        final int size = cellFormat.getFontType().getSize();
        final Style style = Style.builder()
                .buildFontSize(size)
                .build();
        final ParagraphRenderData paragraph = Paragraphs.of().addText(text).defaultTextStyle(style).create();
        final Cells.CellBuilder cellBuilder = Cells.of().addParagraph(paragraph);
        switch (cellFormat.getAlignH()) {
            case LEFT:
                cellBuilder.horizontalLeft();
                break;
            case RIGHT:
                cellBuilder.horizontalRight();
                break;
            case CENTER:
            case JUSTIFY:
                cellBuilder.horizontalCenter();
                break;
        }

        if (cellFormat.getAlignV() == AlignV.MIDDLE) {
            cellBuilder.verticalCenter();
        }
        return cellBuilder.create();
    }
}
