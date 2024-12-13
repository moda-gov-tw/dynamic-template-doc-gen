package app.common.word.data;

import app.common.enums.DecimalFormatEnum;
import app.common.excel.format.DateTimeFormatEnum;
import com.deepoove.poi.data.PictureType;
import app.common.excel.format.data.CellDataSource;
import app.common.excel.format.data.DataSourceHelper;
import app.common.word.format.metadata.TableContentMetadata;
import app.common.word.format.metadata.TreeTableMetadata;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;
import java.util.function.Consumer;

public interface WordDataSourceHelper extends DataSourceHelper {

    //================================================
    //== Date 日期欄位
    //================================================

    /**
     * 日期欄位屬性
     * @param key 資料欄位 key
     */
    default CellDataSource dateSource(final String key) {
        return new DateDataSource(key);
    }

    /**
     * 日期欄位屬性
     * @param key 資料欄位 key
     * @param dateTimeFormat 日期格式
     */
    default CellDataSource dateSource(final String key, final DateTimeFormatEnum dateTimeFormat) {
        return new DateDataSource(key, dateTimeFormat);
    }

    /**
     * 日期欄位屬性
     * @param key 資料欄位 key
     * @param defaultValue 預設值
     * @param dateTimeFormat 日期格式
     */
    default CellDataSource dateSource(final String key, final Object defaultValue, final DateTimeFormatEnum dateTimeFormat) {
        return new DateDataSource(key, defaultValue, dateTimeFormat);
    }

    //================================================
    //== 數值欄位
    //================================================

    /**
     * 數值欄位屬性
     * @param key 資料欄位 key
     */
    default CellDataSource numberSource(final String key) {
        return new NumberDataSource(key);
    }

    /**
     * 數值欄位屬性
     * @param key 資料欄位 key
     * @param decimalFormat 數值格式
     */
    default CellDataSource numberSource(final String key, final DecimalFormatEnum decimalFormat) {
        return new NumberDataSource(key, decimalFormat);
    }

    /**
     * 三位分節數值欄位屬性
     * @param key 資料欄位 key
     */
    default CellDataSource threeDigitSource(final String key) {
        return new NumberDataSource(key, DecimalFormatEnum.threeDigitFormat);
    }

    /**
     * 數值欄位屬性
     * @param key 資料欄位 key
     * @param defaultValue 預設值
     * @param decimalFormat 數值格式
     */
    default CellDataSource numberSource(final String key, final Object defaultValue, final DecimalFormatEnum decimalFormat) {
        return new NumberDataSource(key, defaultValue, decimalFormat);
    }

    //================================================
    //== 超連結欄位
    //================================================

    /**
     * 超連結欄位
     * @param hyperlink 超連結
     */
    default CellDataSource hyperlinkSource(final String linkText, final String hyperlink) {
        return new HyperlinkSource(linkText, hyperlink);
    }

    //================================================
    //== 圖片欄位
    //================================================

    /**
     * 圖片欄位
     * @param key 資料欄位 key
     */
    default PictureSource pictureSource(final String key) {
        return new PictureSource(key);
    }

    /**
     * 圖片欄位
     * @param bufferedImage 圖片 BufferedImage
     * @param pictureType 圖片種類
     */
    default PictureSource pictureSource(final BufferedImage bufferedImage, final PictureType pictureType) {
        return new PictureSource(bufferedImage, pictureType);
    }

    /**
     * 圖片欄位
     * @param file 圖片檔案
     * @param pictureType 圖片種類
     */
    default PictureSource pictureSource(final File file, final PictureType pictureType) {
        return new PictureSource(file, pictureType);
    }

    //================================================
    //== 表格欄位
    //================================================

    /**
     * 表格欄位
     * @param dataKey 第一層 Map key
     * @param cellDataSources 要轉換資料內容的欄位
     */
    default RowTableSource rowTableSource(final String dataKey, final Map<String, CellDataSource> cellDataSources) {
        return new RowTableSource(dataKey, cellDataSources);
    }

    /**
     * 表格欄位
     * @param dataKey 第一層 Map key
     */
    default RowTableSource rowTableSource(final String dataKey) {
        return new RowTableSource(dataKey);
    }

    /**
     * 表格欄位
     * @param dataKey 第一層 Map key
     * @param headerRows 表頭列數
     * @param consumer consumer
     */
    default RowGroupTableSource rowGroupTableSource(final String dataKey, final int headerRows, final Consumer<TableContentMetadata> consumer) {
        final TableContentMetadata metadata = new TableContentMetadata();
        consumer.accept(metadata);
        return new RowGroupTableSource(dataKey, headerRows, metadata);
    }

    /**
     * 動態表格欄位
     * @param dataKey 第一層 Map key
     * @param metadata 表格metadata
     */
    default TreeTableSource treeTableSource(final String dataKey, final TreeTableMetadata metadata) {
        return new TreeTableSource(dataKey, metadata);
    }
}
