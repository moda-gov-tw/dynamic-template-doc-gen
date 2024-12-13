package app.common.word;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.ConfigureBuilder;
import com.deepoove.poi.policy.RenderPolicy;
import app.common.excel.format.data.CellDataSource;
import app.common.word.data.*;
import app.common.word.format.metadata.TemplateColumnMetadata;
import app.common.word.format.metadata.TemplateContentMetadata;
import org.apache.commons.collections4.map.CaseInsensitiveMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public abstract class AbstractWordGenerator<T extends Map<String, Object>> implements WordGenerator, WordDataSourceHelper {

    private final Logger log = LoggerFactory.getLogger(AbstractWordGenerator.class);

    private final T reportData;

    private final TemplateContentMetadata contentMetadata = new TemplateContentMetadata();

    protected AbstractWordGenerator(final T reportData) {
        this.reportData = reportData;
    }

    @Override
    public final Map<String, Object> generateWordContent(final WordDocument doc) {
        this.setupContent(this.contentMetadata);
        final Map<String, TemplateColumnMetadata> columns = this.contentMetadata.getColumns();
        final Map<String, Object> contents = new CaseInsensitiveMap<>();
        final Set<String> templateKeys = columns.keySet();
        final Map<String, RenderPolicy> renderPolicyMap = new LinkedHashMap<>();
        for (final String templateKey : templateKeys) {
            final TemplateColumnMetadata metadata = columns.get(templateKey);
            final CellDataSource source = metadata.getSource();
            contents.put(templateKey, source.asData(reportData));

            if (source instanceof AbstractTableSource) {
                renderPolicyMap.put(templateKey, ((AbstractTableSource) source).getPolicy());
            }
        }
        // 加入未設定metadata欄位
        reportData.keySet()
                .stream()
                .filter(key -> !contents.containsKey(key))
                .forEach(key -> {
                    final Object value = reportData.get(key);
                    if (value != null) {
                        contents.put(key, value);
                    }
                });

        this.createTemplate(doc, renderPolicyMap);
        return contents;
    }

    private void createTemplate(final WordDocument doc, final Map<String, RenderPolicy> renderPolicyMap) {
        if (renderPolicyMap.isEmpty()) {
            doc.template = XWPFTemplate.compile(doc.getTemplateFile());
        } else {
            final ConfigureBuilder builder = Configure.builder();
            renderPolicyMap.forEach(builder::bind);
            final Configure configure = builder.build();
            doc.template = XWPFTemplate.compile(doc.getTemplateFile(), configure);
        }
    }

    protected abstract void setupContent(final TemplateContentMetadata metadata);

    public final byte[] generateWord() {
        final File templateFile = this.getTemplateFile();
        final WordDocument document = new WordDocument(templateFile);
        this.process(document);
        return document.outputBytes();
    }
}
