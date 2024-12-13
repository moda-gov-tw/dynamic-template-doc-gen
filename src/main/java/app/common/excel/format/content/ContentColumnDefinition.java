package app.common.excel.format.content;

import app.common.excel.format.CellFormat;
import app.common.excel.format.metadata.ColumnDefinition;

public class ContentColumnDefinition<T> extends ColumnDefinition<ContentColumnMetadata<T>> {

    public ContentColumnDefinition(final CellFormat cellFormat, final CellFormat headerFormat) {
        super(new ContentColumnMetadata<>("", cellFormat, headerFormat));
    }
}
