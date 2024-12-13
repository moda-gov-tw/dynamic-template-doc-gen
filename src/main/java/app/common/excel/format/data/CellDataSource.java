package app.common.excel.format.data;

/**
 * 資料來源介面
 */
public interface CellDataSource {

    Object asData(final Object obj);

    default String getKey() {
        return "";
    }
}
