package app.common.excel.format;

import com.google.gson.Gson;
import lombok.Data;

import java.util.Objects;

/**
 * 字體
 */
@Data
public class FontType implements Cloneable {
    /**
     * 名稱
     */
    private FontEnum font;
    /**
     * 大小
     */
    private int size;
    /**
     * 顏色
     */
    private ExcelColor color;
    /**
     * 是否粗體
     */
    private boolean bold;
    /**
     * 是否斜體
     */
    private boolean italic;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FontType fontType = (FontType) o;
        return size == fontType.size
                && bold == fontType.bold
                && italic == fontType.italic
                && font == fontType.font
                && Objects.equals(color, fontType.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(font, size, color, bold, italic);
    }

    @Override
    public FontType clone() {
        final Gson gson = new Gson();
        return gson.fromJson(gson.toJson(this), FontType.class);
    }

    public void setColor(final EColor color) {
        this.color = ExcelColor.excelColor(color);
    }

    public void setColor(final String hexColor) {
        this.color = ExcelColor.hexColor(hexColor);
    }

    public FontType size(final int size) {
        this.size = size;
        return this;
    }

    public FontType font(final FontEnum font) {
        this.font = font;
        return this;
    }

    public FontType color(final EColor color) {
        this.setColor(color);
        return this;
    }

    public FontType color(final String hexColor) {
        this.setColor(hexColor);
        return this;
    }

    public FontType bold(final boolean bold) {
        this.bold = bold;
        return this;
    }

    public FontType italic(final boolean italic) {
        this.italic = italic;
        return this;
    }
}
