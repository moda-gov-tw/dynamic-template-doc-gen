package app;

import app.service.DataConverterService;
import app.window.DocumentConverterWindow;
import org.apache.poi.util.IOUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.awt.*;

@SpringBootApplication
public class DynamicTemplateDocGenApplication {

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");

        final ConfigurableApplicationContext context = SpringApplication.run(DynamicTemplateDocGenApplication.class, args);
        final DataConverterService converter = context.getBean(DataConverterService.class);

        EventQueue.invokeLater(() -> {
            final DocumentConverterWindow window = new DocumentConverterWindow(converter);
            window.getJFrame().setVisible(true);
        });
    }

}
