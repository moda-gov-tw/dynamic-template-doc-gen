package app.common.word.format.metadata;

import app.common.excel.format.data.CellDataSource;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class TableContentMetadata extends TemplateContentMetadata {

    private final List<String> keys = new ArrayList<>();

    @Override
    public TemplateColumnMetadata add(final String templateName, final CellDataSource source) {
        throw new UnsupportedOperationException("未支援此操作");
    }

    public TemplateColumnMetadata addColumn(final CellDataSource source) {
        final int size = this.getColumns().size();
        return super.add(String.valueOf(size), source);
    }

    public TemplateColumnMetadata addGroupColumn(final CellDataSource source) {
        final int size = this.getColumns().size();
        final String key = String.valueOf(size);
        this.keys.add(key);
        return super.add(key, source);
    }
}
