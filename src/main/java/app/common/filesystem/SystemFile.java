package app.common.filesystem;

import lombok.Data;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.nio.file.Path;

@Data
public class SystemFile {

    /** 根目錄. */
    private final File root;

    /** 實體檔案. */
    private final File file;

    /** 檔案位置. */
    private final String location;

    public SystemFile(final FileLocation fileLocation, final File root, final String... paths) {
        this(fileLocation.getLocation(), root, paths);
    }

    private SystemFile(final String location, final File root, final String... paths) {
        this.location = location;
        this.root = root;
        final String[] add = ArrayUtils.addFirst(paths, "");
        final File realFile = new File(root, FilenameUtils.normalize(StringUtils.join(add, File.separator)));
        final File parentFile = realFile.getParentFile();
        if (!parentFile.exists()) {
            if (!parentFile.mkdirs()) {
                throw new RuntimeException("can not create " + parentFile);
            }
        }
        this.file = realFile.getAbsoluteFile();
    }

    public boolean exists() {
        return this.file.exists();
    }

    public boolean delete() {
        return this.file.delete();
    }

    public Path toPath() {
        return this.file.toPath();
    }

}
