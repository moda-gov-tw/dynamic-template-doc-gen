package app.common.word.format.metadata;

import app.common.excel.format.*;
import app.common.excel.format.content.ContentColumnDefinition;
import app.common.excel.format.content.ContentColumnMetadata;
import app.common.excel.format.header.HeaderColumnDefinition;
import app.common.excel.format.header.HeaderColumnMetadata;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Map;

@Getter
public abstract class TreeTableMetadata {

    /** 資料表頭格式 */
    protected CellFormat headerFormat;

    protected HeaderColumnDefinition dataHeader;

    protected ContentColumnDefinition<Map<String, Object>> dataContent;

    public TreeTableMetadata() {
        this.init();
    }

    private void init() {
        final FontType fontType = new FontType();
        fontType.setSize(12);
        fontType.setFont(FontEnum.KaiU);
        fontType.setColor(EColor.black);

        this.headerFormat = new CellFormat(AlignV.MIDDLE, AlignH.CENTER);
        this.headerFormat.setBorder(Border.BOX);
        this.headerFormat.setFontType(fontType);
        this.dataHeader = new HeaderColumnDefinition(this.headerFormat);

        final CellFormat contentFormat = new CellFormat()
                .setFontType(fontType)
                .setBackgroundColor(EColor.white)
                .setBorder(Border.BOX)
                .setAlign(AlignH.LEFT, AlignV.MIDDLE)
                .setNumberFormat(NumberFormatEnum.raw)
                .setDateTimeFormat(DateTimeFormatEnum.rocDate);
        this.dataContent = new ContentColumnDefinition<>(contentFormat, this.headerFormat);
    }

    public final void setupMetaData() {
        this.setupDataContent(dataContent.getRoot());
        this.setupDataHeader(dataHeader.getRoot());
    }

    protected void setupDataHeader(final HeaderColumnMetadata metadata) {
        final List<ContentColumnMetadata<Map<String, Object>>> subColumns = this.dataContent.getRoot().getSubColumns();
        if (CollectionUtils.isNotEmpty(subColumns)) {
            for (ContentColumnMetadata<Map<String, Object>> column : subColumns) {
                this.append(metadata, column);
            }
        }
    }

    private void append(final HeaderColumnMetadata metadata, final ContentColumnMetadata<Map<String, Object>> column) {
        final HeaderColumnMetadata subColumnMetadata = metadata.append(column.getName());
        subColumnMetadata.setCellFormat(column.getHeaderFormat());

        if (!column.isLeaf()) {
            final List<ContentColumnMetadata<Map<String, Object>>> subColumns = column.getSubColumns();
            for (ContentColumnMetadata<Map<String, Object>> subColumn : subColumns) {
                this.append(subColumnMetadata, subColumn);
            }
        }
    }

    protected abstract void setupDataContent(final ContentColumnMetadata<Map<String, Object>> metadata);
}
