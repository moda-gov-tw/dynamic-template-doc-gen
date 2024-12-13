package app.common.word;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public interface WordGenerator {

    default void generateWord(final File file) {
        if (file == null) {
            throw new RuntimeException("File is null");
        }
        final String name = file.getName();
        if (StringUtils.endsWithIgnoreCase(name, ".docx")) {
            final File templateFile = this.getTemplateFile();
            try (WordDocument doc = new WordDocument(file, templateFile)) {
                this.process(doc);
            }
        }
    }

    default File getTemplateFile() {
        try {
            // 使用 ClassPathResource 來加載資源
            ClassPathResource resource = new ClassPathResource("template-word/" + this.getTemplateName());

            // 確保資源存在
            if (!resource.exists()) {
                throw new RuntimeException("Template resource not found: " + resource.getPath());
            }

            // 將資源寫入臨時文件
            InputStream inputStream = resource.getInputStream();
            File tempFile = Files.createTempFile("template", ".docx").toFile();
            tempFile.deleteOnExit(); // 程式結束後自動刪除臨時文件

            try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
                inputStream.transferTo(outputStream);
            }

            return tempFile;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load template file", e);
        }
    }

    default void process(final WordDocument doc) {
        final Map<String, Object> contents = this.generateWordContent(doc);
        doc.getTemplate().render(contents);
    }

    Map<String, Object> generateWordContent(final WordDocument doc);

//    String[] getTemplatePaths();

    String getTemplateName();
}
