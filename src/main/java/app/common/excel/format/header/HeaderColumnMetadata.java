package app.common.excel.format.header;

import app.common.excel.format.CellFormat;
import app.common.excel.format.metadata.TreeColumnMetadata;

/**
 * 表頭欄位定義
 */
public class HeaderColumnMetadata extends TreeColumnMetadata<HeaderColumnMetadata> {

    public HeaderColumnMetadata(final CharSequence name, final CellFormat defaultFormat) {
        super(name, defaultFormat);
    }

    public HeaderColumnMetadata(final CharSequence name) {
        this(name, null);
    }

    /**
     * 新增子節點
     * @param title 表頭
     */
    @Override
    public HeaderColumnMetadata append(final CharSequence title) {
        final CellFormat defaultFormat = this.getDefaultFormat();
        final HeaderColumnMetadata column = new HeaderColumnMetadata(title, defaultFormat);
        this.add(column);
        return column;
    }
}
