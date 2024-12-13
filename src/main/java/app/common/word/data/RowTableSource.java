package app.common.word.data;

import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import app.common.excel.format.data.CellDataSource;
import app.common.utils.PropertyUtils;
import lombok.Getter;
import org.apache.commons.collections4.MapUtils;

import java.util.List;
import java.util.Map;

@Getter
public class RowTableSource extends AbstractTableSource {

    private final Map<String, CellDataSource> cellDataSources;

    public RowTableSource(final String dataKey, final Map<String, CellDataSource> cellDataSources) {
        super(dataKey, null, new LoopRowTableRenderPolicy());
        this.cellDataSources = cellDataSources;
    }

    public RowTableSource(final String dataKey) {
        this(dataKey, null);
    }

    @Override
    public Object asData(final Object obj) {
        final Object details = super.readProperty(obj);
        if (MapUtils.isEmpty(cellDataSources)) {
            return details;
        }
        if (details instanceof List) {
            final List<?> results = (List<?>) details;
            results.forEach(element -> {
                this.cellDataSources.forEach((key, cellDataSource) -> {
                    PropertyUtils.setProperty(element, key, cellDataSource.asData(element));
                });
            });
        }
        return details;
    }
}
