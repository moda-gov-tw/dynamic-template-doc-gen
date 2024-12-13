package app.report;

import app.common.enums.DecimalFormatEnum;
import app.common.excel.format.data.CellDataSource;
import app.common.word.AbstractWordGenerator;
import app.common.word.data.PictureSource;
import app.common.word.format.metadata.TemplateContentMetadata;

import java.util.HashMap;
import java.util.Map;

public class ProjectInformationReport extends AbstractWordGenerator<Map<String, Object>> {

    public ProjectInformationReport(final Map<String, Object> reportData) {
        super(reportData);
    }

    @Override
    protected void setupContent(final TemplateContentMetadata metadata) {
        //壹、計畫基本資料
        metadata.add("BDATE", dateSource("BDATE"));
        metadata.add("EDATE", dateSource("EDATE"));
        metadata.add("PLNAMT", numberSource("PLNAMT", DecimalFormatEnum.moneyFormat));

        //貳、工作基本資料
        this.table2(metadata);
        //肆、各月累積預定執行經費
        this.table3(metadata);
        //五、上傳圖片
        this.table4(metadata);
    }

    private void table2(final TemplateContentMetadata metadata) {
        final Map<String, CellDataSource> tableMap = new HashMap<>();
        tableMap.put("SEQ", dataSource("SEQ"));
        tableMap.put("PRJNO", dataSource("PRJNO"));
        tableMap.put("NAME", dataSource("NAME"));
        tableMap.put("TOTAMT", numberSource("TOTAMT", DecimalFormatEnum.moneyFormat));
        tableMap.put("BDATE", dateSource("BDATE"));
        tableMap.put("EDATE", dateSource("EDATE"));
        tableMap.put("PRJKIND", dataSource("PRJKIND"));
        tableMap.put("CONTAIN", dataSource("CONTAIN"));
        metadata.add("table2", rowTableSource("table2", tableMap));
    }

    private void table3(final TemplateContentMetadata metadata) {
        final Map<String, CellDataSource> tableMap = new HashMap<>();
        tableMap.put("SEQ", dataSource("SEQ"));
        tableMap.put("SYRMNTH", dataSource("SYRMNTH"));
        tableMap.put("PEXP", numberSource("PEXP", DecimalFormatEnum.moneyFormat));
        tableMap.put("MEMO", dataSource("MEMO"));
        metadata.add("table4", rowTableSource("table3", tableMap));
    }

    private void table4(final TemplateContentMetadata metadata) {
        final Map<String, CellDataSource> tableMap = new HashMap<>();
        tableMap.put("SEQ", dataSource("SEQ"));
        PictureSource file = pictureSource("FILE");
        file.fitSize();
        tableMap.put("FILE", file);
        tableMap.put("CONTENT", dataSource("CONTENT"));
        metadata.add("table5", rowTableSource("table4", tableMap));
    }

    @Override
    public String getTemplateName() {
        return "projectInformation-Template.docx";
    }
}
