package app.common.excel.format.content;

import app.common.excel.format.CellFormat;
import app.common.excel.format.data.CellDataSource;
import app.common.excel.format.metadata.TreeColumnMetadata;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Consumer;

/**
 * 資料內容欄位定義
 */
@Getter
@Setter
public class ContentColumnMetadata<T> extends TreeColumnMetadata<ContentColumnMetadata<T>> {

    /** 資料來源 */
    private CellDataSource source;
    /** 預設表頭格式 */
    private CellFormat defaultHeaderFormat;
    /** 表頭格式 */
    private CellFormat headerFormat;
    /** 合併欄位 */
    private int columnSpan = 1;

    public ContentColumnMetadata(final CharSequence name, final CellFormat defaultFormat, final CellFormat defaultHeaderFormat) {
        super(name, defaultFormat);
        this.defaultHeaderFormat = defaultHeaderFormat;
    }

    /**
     * 新增子節點
     * @param title 表頭
     */
    @Override
    public ContentColumnMetadata<T> append(final CharSequence title) {
        final CellFormat defaultFormat = this.getDefaultFormat();
        final CellFormat headerFormat = this.getHeaderFormat();
        final ContentColumnMetadata<T> column = new ContentColumnMetadata<>(title, defaultFormat, headerFormat);
        this.add(column);
        return column;
    }

    /**
     * 新增子節點
     * @param title 表頭
     * @param source 資料來源
     */
    public ContentColumnMetadata<T> append(final CharSequence title, final CellDataSource source) {
        final ContentColumnMetadata<T> metadata = this.append(title);
        metadata.setSource(source);
        return metadata;
    }

    /**
     * 新增子節點
     * @param columnSpan 合併欄位
     * @param title 表頭
     * @param source 資料來源
     */
    public ContentColumnMetadata<T> append(final int columnSpan, final CharSequence title, final CellDataSource source) {
        final ContentColumnMetadata<T> metadata = this.append(title, source);
        metadata.columnSpan = columnSpan;
        return metadata;
    }

    /**
     * 新增子節點
     * @param source 資料來源
     */
    public ContentColumnMetadata<T> append(final CellDataSource source) {
        return this.append("", source);
    }

    /**
     * 新增子節點
     * @param columnSpan 合併欄位
     * @param source 資料來源
     */
    public ContentColumnMetadata<T> append(final int columnSpan, final CellDataSource source) {
        return this.append(columnSpan, "", source);
    }

    /**
     * 新增子節點
     * @param title 表頭
     * @param source 資料來源
     * @param width 欄位寬度
     */
    public ContentColumnMetadata<T> append(final CharSequence title, final CellDataSource source, final int width) {
        final ContentColumnMetadata<T> metadata = this.append(title, source);
        metadata.setWidth(width);
        return metadata;
    }

    /**
     * 新增子節點
     * @param source 資料來源
     * @param width 欄位寬度
     */
    public ContentColumnMetadata<T> append(final CellDataSource source, final int width) {
        return this.append("", source, width);
    }

    /**
     * 新增子節點
     * @param columnSpan 合併欄位
     * @param source 資料來源
     * @param width 欄位寬度
     */
    public ContentColumnMetadata<T> append(final int columnSpan, final CellDataSource source, final int width) {
        final ContentColumnMetadata<T> metadata = this.append("", source, width);
        metadata.columnSpan = columnSpan;
        return metadata;
    }

    /**
     * 新增子節點
     * @param title 表頭
     * @param source 資料來源
     * @param consumer 子節點 consumer
     */
    public ContentColumnMetadata<T> append(final CharSequence title, final CellDataSource source, final Consumer<ContentColumnMetadata<T>> consumer) {
        final ContentColumnMetadata<T> column = this.append(title, source);
        consumer.accept(column);
        return column;
    }

    /**
     * 新增子節點
     * @param source 資料來源
     * @param consumer 子節點 consumer
     */
    public ContentColumnMetadata<T> append(final CellDataSource source, final Consumer<ContentColumnMetadata<T>> consumer) {
        return this.append("", source, consumer);
    }

    /**
     * 新增子節點
     * @param title 表頭
     * @param source 資料來源
     * @param width 欄位寬度
     * @param consumer 子節點 consumer
     */
    public ContentColumnMetadata<T> append(
            final CharSequence title,
            final CellDataSource source,
            final int width,
            final Consumer<ContentColumnMetadata<T>> consumer
    ) {
        final ContentColumnMetadata<T> column = this.append(title, source, width);
        consumer.accept(column);
        return column;
    }

    /**
     * 新增子節點
     * @param source 資料來源
     * @param width 欄位寬度
     * @param consumer 子節點 consumer
     */
    public ContentColumnMetadata<T> append(final CellDataSource source, final int width, final Consumer<ContentColumnMetadata<T>> consumer) {
        return this.append("", source, width, consumer);
    }

    /** 取得表頭格式 */
    public CellFormat getHeaderFormat() {
        if (headerFormat == null && defaultHeaderFormat != null) {
            headerFormat = defaultHeaderFormat.clone();
        }
        return headerFormat;
    }

    /** 取得表頭格式 */
    public CellFormat headerFormat() {
        return this.getHeaderFormat();
    }

}
