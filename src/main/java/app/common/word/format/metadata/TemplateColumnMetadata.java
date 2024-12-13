package app.common.word.format.metadata;

import app.common.excel.format.CellFormat;
import app.common.excel.format.data.CellDataSource;
import app.common.excel.format.metadata.ColumnMetadata;
import lombok.Getter;

import java.util.Objects;

@Getter
public class TemplateColumnMetadata extends ColumnMetadata {

    /** 資料來源 */
    private final CellDataSource source;

    public TemplateColumnMetadata(final CharSequence name, final CellDataSource source) {
        this(name, source, null);
    }

    public TemplateColumnMetadata(final CharSequence name, final CellDataSource source, final CellFormat defaultFormat) {
        this.source = source;
        this.defaultFormat = defaultFormat;
        this.name = Objects.toString(name, "");
    }
}
