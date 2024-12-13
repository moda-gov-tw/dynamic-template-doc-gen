package app.common.word.data;

import com.deepoove.poi.data.HyperlinkTextRenderData;
import app.common.excel.format.data.ConstantSource;

import java.util.Objects;

public class HyperlinkSource extends ConstantSource<String> {

    private String linkText;

    public HyperlinkSource(final String linkText, final String value) {
        super(value);
        this.linkText = linkText;
    }

    @Override
    public Object asData(final Object obj) {
        return new HyperlinkTextRenderData(this.linkText, Objects.toString(super.asData(obj)));
    }
}
