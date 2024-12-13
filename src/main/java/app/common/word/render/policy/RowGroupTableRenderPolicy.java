package app.common.word.render.policy;

import com.deepoove.poi.data.CellRenderData;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.Rows;
import com.deepoove.poi.policy.DynamicTableRenderPolicy;
import com.deepoove.poi.policy.TableRenderPolicy;
import com.deepoove.poi.util.TableTools;
import app.common.word.format.cell.CellCreator;
import app.common.word.format.metadata.TableContentMetadata;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 列群組表格
 */
public class RowGroupTableRenderPolicy extends DynamicTableRenderPolicy {

    private final int headerRows;

    private final TableContentMetadata metadata;

    public RowGroupTableRenderPolicy(final int headerRows, final TableContentMetadata metadata) {
        this.headerRows = headerRows;
        this.metadata = metadata;
    }

    @Override
    public void render(final XWPFTable xwpfTable, final Object object) throws Exception {
        final List<Map<String, Object>> tableData = (List<Map<String, Object>>) object;
        if (CollectionUtils.isEmpty(tableData)) {
            return;
        }
        // 資料分組
        final Map<String, List<Map<String, Object>>> groupMap = tableData.stream()
                .collect(Collectors.groupingBy(
                        map -> this.metadata.getKeys()
                                .stream()
                                .map(key -> Objects.toString(this.metadata.getColumns().get(key).getSource().asData(map)))
                                .collect(Collectors.joining(":")),
                        LinkedHashMap::new,
                        Collectors.toList()
                ));

        final int columnSize = this.metadata.getColumns().size();
        int rowIndex = headerRows + 1;
        for (List<Map<String, Object>> list : groupMap.values()) {
            final int size = list.size();
            if (size == 1) {
                final XWPFTableRow row = this.createRow(xwpfTable, columnSize, rowIndex);
                this.renderRow(row, this.wrapRowData(list.get(0)));
                rowIndex++;
            } else {
                // create rows
                for (int i = 0; i < size; i++) {
                    this.createRow(xwpfTable, columnSize, rowIndex + i);
                }

                // merge rows
                for (String key : this.metadata.getKeys()) {
                    final int columnIndex = Integer.parseInt(key);
                    TableTools.mergeCellsVertically(xwpfTable, columnIndex, rowIndex, rowIndex + size - 1);
                }

                // render data
                for (Map<String, Object> map : list) {
                    final RowRenderData rowData = this.wrapRowData(map);
                    this.renderRow(xwpfTable.getRow(rowIndex), rowData);
                    rowIndex++;
                }
            }
        }
    }

    private XWPFTableRow createRow(final XWPFTable xwpfTable, final int columnSize, final int rowIndex) {
        final XWPFTableRow row = xwpfTable.insertNewTableRow(rowIndex);
        IntStream.range(0, columnSize).forEach(i -> row.createCell());
        return row;
    }

    private RowRenderData wrapRowData(final Map<String, Object> map) {
        final CellRenderData[] results = this.metadata.getColumns()
                .values()
                .stream()
                .map(columnMetadata -> {
                    final String text = Objects.toString(columnMetadata.getSource().asData(map));
                    return CellCreator.createCell(text, columnMetadata.getCellFormat());
                })
                .collect(Collectors.toList())
                .toArray(CellRenderData[]::new);
        return Rows.of(results).create();
    }

    private void renderRow(final XWPFTableRow row, final RowRenderData rowData) {
        try {
            TableRenderPolicy.Helper.renderRow(row, rowData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
