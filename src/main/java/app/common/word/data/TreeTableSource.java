package app.common.word.data;

import app.common.word.format.metadata.TreeTableMetadata;
import app.common.word.render.policy.TreeTableRenderPolicy;

public class TreeTableSource extends AbstractTableSource {

    public TreeTableSource(final String key, final TreeTableMetadata metadata) {
        super(key, null, new TreeTableRenderPolicy(metadata));
    }

    @Override
    public Object asData(Object obj) {
        return super.readProperty(obj);
    }
}
