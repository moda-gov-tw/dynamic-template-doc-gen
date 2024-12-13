package app.common.word;

import com.deepoove.poi.util.TableTools;
import app.common.excel.ExcelPoint;
import app.common.excel.format.CellFormat;
import app.common.excel.format.ColorType;
import app.common.excel.format.ExcelColor;
import app.common.word.format.CellFormatHelper;
import lombok.Getter;
import org.apache.poi.xwpf.usermodel.*;

import java.util.stream.IntStream;

@Getter
public class WordTable {

    private final XWPFTable table;

    public WordTable(final XWPFTable table) {
        this.table = table;
        this.table.setWidth("100%");
        this.table.setCellMargins(100, 100, 100, 100);
        this.table.setWidthType(TableWidthType.PCT);
        this.table.setTableAlignment(TableRowAlign.CENTER);
    }

    public void appendCell(
            final ExcelPoint startPos,
            final ExcelPoint stopPos,
            final String value,
            final CellFormat cellFormat
    ) {
        this.appendCell(startPos, stopPos, value, cellFormat, -1);
    }

    public void appendCell(
            final ExcelPoint startPos,
            final ExcelPoint stopPos,
            final String value,
            final CellFormat cellFormat,
            final int width
    ) {
        final XWPFTableCell cell = this.createCell(startPos, stopPos, cellFormat);
        if (width > 0) {
            cell.setWidth(width + "%");
            cell.setWidthType(TableWidthType.PCT);
        }

        cell.removeParagraph(0);
        final XWPFParagraph paragraph = cell.addParagraph();
        paragraph.setAlignment(CellFormatHelper.getParagraphAlignH(cellFormat));

        final XWPFRun run = paragraph.createRun();
        run.setText(value);
    }

    private XWPFTableCell createCell(
            final ExcelPoint startPos,
            final ExcelPoint stopPos,
            final CellFormat cellFormat
    ) {
        final int rowIndex = startPos.getRow();
        final int colIndex = startPos.getColumn();
        final XWPFTableCell cell = this.createCell(startPos);
        cell.setVerticalAlignment(CellFormatHelper.getVerticalAlignment(cellFormat));
        final ExcelColor backgroundColor = cellFormat.getBackgroundColor();
        if (backgroundColor != null && ColorType.hexColor == backgroundColor.getType()) {
            cell.setColor(cellFormat.getBackgroundColor().getHex());
        }

        if (!startPos.equals(stopPos)) {
            final int rowIndex2 = stopPos.getRow();
            final int colIndex2 = stopPos.getColumn();
            final int firstRow = Math.min(rowIndex, rowIndex2);
            final int lastRow = Math.max(rowIndex, rowIndex2);
            final int firstCol = Math.min(colIndex, colIndex2);
            final int lastCol = Math.max(colIndex, colIndex2);

            if (firstCol != lastCol) {
                IntStream.range(firstCol, lastCol).forEach(i -> this.getRow(firstRow).createCell());
                TableTools.mergeCellsHorizonal(table, firstRow, firstCol, lastCol);
            }

            if (firstRow != lastRow) {
                IntStream.rangeClosed(firstRow, lastRow).forEach(i -> {
                    final XWPFTableRow row = this.getRow(i);
                    IntStream.rangeClosed(firstCol, lastCol).forEach(j -> {
                        final XWPFTableCell cell1 = row.getCell(j);
                        if (cell1 == null) {
                            row.createCell();
                        }
                    });
                });
                TableTools.mergeCellsVertically(table, firstCol, firstRow, lastRow);
            }
        }
        return cell;
    }

    public XWPFTableCell createCell(final ExcelPoint startPos) {
        final int rowIndex = startPos.getRow();
        final int colIndex = startPos.getColumn();
        final XWPFTableRow row = getRow(rowIndex);
        return row.createCell();
    }

    public XWPFTableRow getRow(final int index) {
        final XWPFTableRow row = this.table.getRow(index);
        if (row == null) {
            return this.createRow(index);
        }
        return row;
    }

    private XWPFTableRow createRow(final int rowIndex) {
        return this.table.insertNewTableRow(rowIndex);
    }
}
