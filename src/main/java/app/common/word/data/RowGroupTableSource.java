package app.common.word.data;

import app.common.word.format.metadata.TableContentMetadata;
import app.common.word.render.policy.RowGroupTableRenderPolicy;

public class RowGroupTableSource extends AbstractTableSource {

    public RowGroupTableSource(final String key, final int headerRows, final TableContentMetadata metadata) {
        super(key, null, new RowGroupTableRenderPolicy(headerRows, metadata));
    }

    @Override
    public Object asData(final Object obj) {
        return super.readProperty(obj);
    }
}
