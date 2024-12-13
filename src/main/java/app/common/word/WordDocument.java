package app.common.word;

import com.deepoove.poi.XWPFTemplate;
import lombok.Getter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

@Getter
public class WordDocument implements AutoCloseable {

    protected XWPFTemplate template;

    private final File exporgFile;

    private final File templateFile;

    public WordDocument(final File exporgFile, final File templateFile) {
        this.exporgFile = exporgFile;
        this.templateFile = templateFile;
//        this.template = XWPFTemplate.compile(templateFile.getFile());
    }

    public WordDocument(final File templateFile) {
        this(null, templateFile);
    }

    @Override
    public void close() {
        try (final OutputStream out = Files.newOutputStream(this.exporgFile.toPath())) {
            this.template.writeAndClose(out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] outputBytes() {
        try (final ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            this.template.writeAndClose(out);
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
