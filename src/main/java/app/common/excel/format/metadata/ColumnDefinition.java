package app.common.excel.format.metadata;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class ColumnDefinition<T extends TreeColumnMetadata> {
    /** 根節點 */
    protected T root;

    public ColumnDefinition(final T root) {
        this.root = root;
    }

    public T getRoot() {
        return this.root;
    }

    /** 取得最大層數 */
    public int getMaxRowCount() {
        final List<T> subColumns = this.root.getSubColumns();
        if (CollectionUtils.isNotEmpty(subColumns)) {
            return findMaxLevel(this.root);
        }
        return 0;
    }

    /** 取得最大層數 */
    private int findMaxLevel(final T columnMetadata) {
        if (columnMetadata.isLeaf()) {
            return columnMetadata.getLevel();
        }
        int max = 0;
        final List<T> subColumns = columnMetadata.getSubColumns();
        for (T child : subColumns) {
            final int level = findMaxLevel(child);
            if (level > max) {
                max = level;
            }
        }
        return max;
    }

    /** 取得樹葉數量 */
    public List<T> getLeafColumns() {
        final List<T> subColumns = this.root.getSubColumns();
        if (CollectionUtils.isNotEmpty(subColumns)) {
            return this.getLeaf(this.root);
        }
        return Collections.EMPTY_LIST;
    }

    /** 取得所有樹葉節點 */
    private List<T> getLeaf(T columnMetadata) {
        if (columnMetadata.isLeaf()) {
            return Arrays.asList(columnMetadata);
        }
        final List<T> leafColumns = new ArrayList<>();
        final List<T> subColumns = columnMetadata.getSubColumns();
        for (T subColumn : subColumns) {
            leafColumns.addAll(getLeaf(subColumn));
        }
        return leafColumns;
    }
}
