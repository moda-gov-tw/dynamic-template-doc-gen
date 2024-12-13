package app.common.excel.format.metadata;

import app.common.excel.format.CellFormat;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * 樹狀欄位定義
 */
@Data
public abstract class TreeColumnMetadata<T extends TreeColumnMetadata> extends ColumnMetadata {

    /** 父節點 */
    protected T parent;
    /** 子節點 */
    protected List<T> subColumns;

    public TreeColumnMetadata(final CharSequence name, final CellFormat defaultFormat) {
        if (defaultFormat != null) {
            this.defaultFormat = defaultFormat.clone();
        }
        this.name = Objects.toString(name, "");
    }

    public TreeColumnMetadata(final CharSequence name) {
        this(name, null);
    }

    /** 是否為樹葉節點 */
    public boolean isLeaf() {
        return CollectionUtils.isEmpty(this.subColumns);
    }

    /** 取得樹葉節點個數 */
    public int getLeafSize() {
        if (this.isLeaf()) {
            return 1;
        }
        int leafSize = 0;
        for (final T child : this.subColumns) {
            leafSize += child.getLeafSize();
        }
        return leafSize;
    }

    /**
     * 新增子節點
     * @param title 表頭
     */
    public abstract T append(final CharSequence title);

    /**
     * 新增子節點
     * @param title 表頭
     * @param consumer 子節點 consumer
     */
    public T append(final CharSequence title, final Consumer<T> consumer) {
        final T column = this.append(title);
        consumer.accept(column);
        return column;
    }

    protected boolean add(final T column) {
        if (this.subColumns == null) {
            this.subColumns = new ArrayList<>();
        }
        if (column.parent == null) {
            column.parent = this;
            column.level = column.parent.level + 1;
            this.subColumns.add(column);
            return true;
        } else {
            return false;
        }
    }
}
