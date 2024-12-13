package app.common.excel.format.metadata;

import app.common.excel.format.CellFormat;
import lombok.Data;

/**
 * 欄位定義
 */
@Data
public abstract class ColumnMetadata {

    /** 名稱 */
    protected String name = "";
    /** 層數 */
    protected int level = 0;
    /** 預設格式 */
    protected CellFormat defaultFormat;
    /** 實際格式 */
    protected CellFormat cellFormat;
    /** 欄寬 */
    protected int width = -1;

    public CellFormat getCellFormat() {
        if (cellFormat == null && defaultFormat != null) {
            cellFormat = defaultFormat.clone();
        }
        return cellFormat;
    }

    public CellFormat cellFormat() {
        return this.getCellFormat();
    }
}
