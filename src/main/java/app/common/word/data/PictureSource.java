package app.common.word.data;

import com.deepoove.poi.data.PictureType;
import com.deepoove.poi.data.Pictures;
import app.common.excel.format.data.AbstractDataSource;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class PictureSource extends AbstractDataSource {

    private enum ImageSourceType {
        data,
        bufferedImage,
        file
    }

    private final ImageSourceType imageSourceType;

    private int width;

    private int height;

    private BufferedImage bufferedImage;

    private PictureType pictureType;

    private File file;

    public PictureSource(final String key) {
        super(key, null);
        this.imageSourceType = ImageSourceType.data;
    }

    public PictureSource(final BufferedImage bufferedImage, final PictureType pictureType) {
        super(null, null);
        this.bufferedImage = bufferedImage;
        this.pictureType = pictureType;
        this.imageSourceType = ImageSourceType.bufferedImage;
    }

    public PictureSource(final File file, final PictureType pictureType) {
        super(null, null);
        this.file = file;
        this.pictureType = pictureType;
        this.imageSourceType = ImageSourceType.file;
    }

    public PictureSource size(final int width, final int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public PictureSource fitSize() {
        this.width = -1;
        this.height = -1;
        return this;
    }

    @Override
    public Object asData(final Object obj) {
        Pictures.PictureBuilder builder = null;
        switch (this.imageSourceType) {
            case data:
                try {
                    final String path = Objects.toString(super.readProperty(obj));
                    final String realPath = FilenameUtils.normalize(path);
                    builder = Pictures.ofBytes(Files.readAllBytes(Paths.get(realPath)));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case bufferedImage:
                builder = Pictures.ofBufferedImage(this.bufferedImage, this.pictureType);
                break;
            case file:
                try {
                    builder = Pictures.ofBytes(Files.readAllBytes(this.file.toPath()), this.pictureType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
        if (this.width < 0 && this.height < 0) {
            builder.fitSize();
        }
        return builder.create();
    }
}
