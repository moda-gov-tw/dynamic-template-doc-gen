package app.report;

import app.common.word.AbstractWordGenerator;
import app.common.word.format.metadata.TemplateContentMetadata;

import java.util.Map;

public class AccountPermissionReport extends AbstractWordGenerator<Map<String, Object>> {

    public AccountPermissionReport(Map<String, Object> reportData) {
        super(reportData);
    }

    @Override
    protected void setupContent(TemplateContentMetadata metadata) {
        metadata.add("unit", dataSource("UNIT"));
        // 帳號權限審查表
        metadata.add("table1", rowTableSource("objectives"));
    }

    @Override
    public String getTemplateName() {
        return "accountPermission-Template.docx";
    }
}
