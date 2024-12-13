package app.common.excel;


import java.util.Objects;
import java.awt.*;

public class ExcelPoint {

    private int row;

    private int column;

    public ExcelPoint(final Point point) {
        this(point.y, point.x);
    }

    public ExcelPoint(final int row, final int col) {
        this.row = row;
        this.column = col;
    }

    public int getColumn() {
        return this.column;
    }

    public int getRow() {
        return this.row;
    }

    public ExcelPoint add(final int rowCount, final int colCount) {
        return new ExcelPoint(this.row + rowCount, this.column + colCount);
    }

    public ExcelPoint increaseCol() {
        return new ExcelPoint(this.row, this.column + 1);
    }

    public ExcelPoint increaseRow() {
        return new ExcelPoint(this.row + 1, this.column);
    }

    public ExcelPoint nextRow() {
        return new ExcelPoint(this.row + 1, 0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExcelPoint that = (ExcelPoint) o;
        return row == that.row && column == that.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
