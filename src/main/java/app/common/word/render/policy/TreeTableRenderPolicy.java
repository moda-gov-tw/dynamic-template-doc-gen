package app.common.word.render.policy;

import com.deepoove.poi.policy.DynamicTableRenderPolicy;
import app.common.excel.ExcelPoint;
import app.common.excel.format.content.ContentColumnDefinition;
import app.common.excel.format.content.ContentColumnMetadata;
import app.common.excel.format.data.CellDataSource;
import app.common.excel.format.header.HeaderColumnDefinition;
import app.common.excel.format.header.HeaderColumnMetadata;
import app.common.word.WordTable;
import app.common.word.format.metadata.TreeTableMetadata;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * 動態表格
 */
public class TreeTableRenderPolicy extends DynamicTableRenderPolicy {

    private static final Logger LOG = LoggerFactory.getLogger(TreeTableRenderPolicy.class);

    private final TreeTableMetadata metadata;

    private ExcelPoint startPos;

    private WordTable table;

    public TreeTableRenderPolicy(final TreeTableMetadata metadata) {
        this.metadata = metadata;
        this.metadata.setupMetaData();
    }

    @Override
    public void render(final XWPFTable xwpfTable, final Object object) throws Exception {
        this.table = new WordTable(xwpfTable);
        this.startPos = new ExcelPoint(0, 0);
        // remove template row
        xwpfTable.removeRow(0);
        // header
        final HeaderColumnDefinition dataHeader = this.metadata.getDataHeader();
        final int headerRowCount = dataHeader.getMaxRowCount();
        for (HeaderColumnMetadata headerMetadata : dataHeader.getRoot().getSubColumns()) {
            this.renderRow(headerMetadata, headerRowCount);
        }
        IntStream.range(0, headerRowCount).forEach(i -> this.startPos = this.startPos.nextRow());

        // content
        final List<Map<String, Object>> tableData = (List<Map<String, Object>>) object;
        if (CollectionUtils.isNotEmpty(tableData)) {
            final ContentColumnDefinition<Map<String, Object>> dataContent = this.metadata.getDataContent();
            final int maxRowCount = dataContent.getMaxRowCount();

            for (final Map<String, Object> map : tableData) {
                for (final ContentColumnMetadata<Map<String, Object>> columnMetadata : dataContent.getRoot().getSubColumns()) {
                    this.renderRow(columnMetadata, maxRowCount, map);
                }
                IntStream.range(0, maxRowCount).forEach(j -> this.startPos = this.startPos.nextRow());
            }
        }
    }

    private void renderRow(
            final ContentColumnMetadata<Map<String, Object>> metadata,
            final int maxHeaderRow,
            final Map<String, Object> map
    ) {
        final int level = metadata.getLevel();
        final int leafSize = metadata.getLeafSize();
        final int startRow = this.startPos.getRow() + level - 1;
        final XWPFTableRow row = table.getTable().getRow(startRow);
        final int startCol = row == null ? 0 : row.getTableCells().size();

        final ExcelPoint start = new ExcelPoint(startRow, startCol);
        final ExcelPoint end = new ExcelPoint(
                start.getRow() + (metadata.isLeaf() ? maxHeaderRow - level : 0),
                start.getColumn() + leafSize - 1
        );
        final CellDataSource source = metadata.getSource();
        final String value = Objects.toString(source.asData(map), "");
        table.appendCell(start, end, value, metadata.cellFormat(), metadata.getWidth());

        if (!metadata.isLeaf()) {
            final List<ContentColumnMetadata<Map<String, Object>>> subColumns = metadata.getSubColumns();
            for (ContentColumnMetadata<Map<String, Object>> subColumn : subColumns) {
                this.renderRow(subColumn, maxHeaderRow, map);
            }
        }
    }

    private void renderRow(
            final HeaderColumnMetadata metadata,
            final int maxHeaderRow
    ) {
        final int level = metadata.getLevel();
        final int leafSize = metadata.getLeafSize();
        final int startRow = this.startPos.getRow() + level - 1;
        final XWPFTableRow row = table.getTable().getRow(startRow);
        final int startCol = row == null ? 0 : row.getTableCells().size();

        final ExcelPoint start = new ExcelPoint(startRow, startCol);
        final ExcelPoint end = new ExcelPoint(
                start.getRow() + (metadata.isLeaf() ? maxHeaderRow - level : 0),
                start.getColumn() + leafSize - 1
        );
        table.appendCell(start, end, metadata.getName(), metadata.getCellFormat(), metadata.getWidth());

        if (!metadata.isLeaf()) {
            for (HeaderColumnMetadata subColumn : metadata.getSubColumns()) {
                this.renderRow(subColumn, maxHeaderRow);
            }
        }
    }
}
