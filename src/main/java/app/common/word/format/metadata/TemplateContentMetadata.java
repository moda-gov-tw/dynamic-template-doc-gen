package app.common.word.format.metadata;

import app.common.excel.format.*;
import app.common.excel.format.data.CellDataSource;
import lombok.Getter;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.util.Map;

@Getter
public class TemplateContentMetadata {

    private final Map<String, TemplateColumnMetadata> columns = new LinkedCaseInsensitiveMap<>();

    private final CellFormat defaultFormat;

    public TemplateContentMetadata() {
        final FontType fontType = new FontType();
        fontType.setFont(FontEnum.KaiU);
        fontType.setSize(12);
        fontType.setColor(EColor.black);
        fontType.setBold(false);
        fontType.setItalic(false);

        this.defaultFormat = new CellFormat()
                .setFontType(fontType)
                .setBackgroundColor(EColor.white)
                .setBorder(Border.BOX)
                .setAlign(AlignH.LEFT, AlignV.MIDDLE)
                .setNumberFormat(NumberFormatEnum.raw)
                .setDateTimeFormat(DateTimeFormatEnum.rocDate);
    }

    public TemplateContentMetadata(final CellFormat defaultFormat) {
        this.defaultFormat = defaultFormat;
    }

    /**
     * 加入模板欄位定義
     * @param templateName 模板名稱，一般欄位模板文件定義成{{templateName}}：圖片{{@templateName}}
     * @param source 欄位資料來源，使用{@link app.common.word.data.WordDataSourceHelper} 建立
     */
    public TemplateColumnMetadata add(final String templateName, final CellDataSource source) {
        if (columns.containsKey(templateName)) {
            throw new IllegalArgumentException("templateName:" + templateName + " duplicate definition");
        }

        final TemplateColumnMetadata metadata = new TemplateColumnMetadata(templateName, source, this.defaultFormat.clone());
        this.columns.put(templateName, metadata);
        return metadata;
    }
}
