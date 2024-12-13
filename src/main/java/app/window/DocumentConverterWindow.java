package app.window;

import app.service.DataConverterService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DocumentConverterWindow {

    private static final float FONT_SIZE = 16f;

    @Getter
    private JFrame jFrame;

    private JTabbedPane tabbedPane = new JTabbedPane();

    private JTextArea inputTextArea;

    private JFileChooser fileChooser;

    private JButton button;

    private JTextField textField;

    private JComboBox<String> formatComboBox;


    private final DataConverterService converter;

    public DocumentConverterWindow(DataConverterService converter) {
        this.converter = converter;
        this.setupJFrame();
        this.createTab1();

        this.setFont(tabbedPane);

        final Container container = this.jFrame.getContentPane();
        container.setLayout(new FlowLayout(FlowLayout.LEFT));
        container.add(tabbedPane);

        this.jFrame.pack();
    }

    private void setupJFrame() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);

        final Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

        // 獲取螢幕解析度
        this.jFrame = new JFrame("WordGeneratorWindow");
        this.jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 設定視窗大小佔螢幕四分之一
//        this.jFrame.setSize(dimension.width / 2, dimension.height / 2);
        this.jFrame.setSize(500, 500);
        this.jFrame.setResizable(false);

        final int x = (int) ((dimension.getWidth() - this.jFrame.getWidth()) / 2);
        final int y = (int) ((dimension.getHeight() - this.jFrame.getHeight()) / 2);
        this.jFrame.setLocation(x, y);
    }

    private void createTab1() {
        final List<JPanel> panels = new ArrayList<JPanel>();
        this.createFormatBlock(panels);
        this.createWordButton(panels);

        final JPanel jp = new JPanel(new GridLayout(panels.size(), 1));
        for (final JPanel panel : panels) {
            jp.add(panel);
        }

        //將多行文字框，加到滾動捲軸面板中
        final JScrollPane jScrollPane = new JScrollPane(jp);
        tabbedPane.add("generator", jScrollPane);
    }

    private void createFormatBlock(final List<JPanel> panels) {

        final JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING));

        final JLabel label = new JLabel("word文件範例：");
        this.setFont(label);
        panel.add(label);

        final String[] fileNames = {"帳號權限審查表(範本)", "活動計畫基本資料(範本)"};
//        final String[] fileNames = {"帳號權限審查表(範本)", "圖片範本", "範本3"};

        this.formatComboBox = new JComboBox<>(fileNames);
        this.setFont(this.formatComboBox);
        panel.add(this.formatComboBox);
        panels.add(panel);
    }

    private void createWordButton(final List<JPanel> panels) {
            final JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING));

        final JLabel label = new JLabel("  ");

        this.button = new JButton("產Word檔");
        this.setFont(this.button);
        this.button.addActionListener(event -> {
            try {
                if (StringUtils.equals("帳號權限審查表(範本)", (String) formatComboBox.getSelectedItem())) {
                    this.converter.accountPermission();
                }
                if (StringUtils.equals("活動計畫基本資料(範本)", (String) formatComboBox.getSelectedItem())) {
                    this.converter.projectInformation();
                }
                log.info("done");
                JOptionPane.showMessageDialog(DocumentConverterWindow.this.jFrame, "處理成功");
            } catch (final Exception e) {
                JOptionPane.showMessageDialog(DocumentConverterWindow.this.jFrame, e.getMessage());
            }
        });
        this.addEnterKeyListener(this.button);

        panel.add(label);
        panel.add(this.button);
        panels.add(panel);
    }

    private void addEnterKeyListener(final JButton button) {
        button.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(final KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    button.doClick();
                }
            }
        });
    }

    /**
     * 設定字大小
     */
    private void setFont(final Component c) {
        c.setFont(c.getFont().deriveFont(FONT_SIZE));
    }
}
