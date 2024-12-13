package app.common.word.data;

import com.deepoove.poi.policy.RenderPolicy;
import app.common.excel.format.data.AbstractDataSource;
import lombok.Getter;

@Getter
public abstract class AbstractTableSource extends AbstractDataSource {

    private final RenderPolicy policy;

    protected AbstractTableSource(final String key, final Object defaultValue, final RenderPolicy policy) {
        super(key, defaultValue);
        this.policy = policy;
    }
}
