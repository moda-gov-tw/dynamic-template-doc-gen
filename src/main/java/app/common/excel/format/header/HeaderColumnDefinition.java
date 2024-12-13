package app.common.excel.format.header;

import app.common.excel.format.CellFormat;
import app.common.excel.format.metadata.ColumnDefinition;

public class HeaderColumnDefinition extends ColumnDefinition<HeaderColumnMetadata> {

    public HeaderColumnDefinition() {
        this(null);
    }

    public HeaderColumnDefinition(final CellFormat cellFormat) {
        super(new HeaderColumnMetadata("", cellFormat));
    }

}
