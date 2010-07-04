package rtorrent.addtorrent;

import com.sun.awt.AWTUtilities;
import rtorrent.client.AddTorrentMessage;
import rtorrent.client.RequestManager;
import rtorrent.settings.ImagePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * User: welvet
 * Date: 29.06.2010
 * Time: 23:12:22
 */
public class AddTorrent extends JDialog {
    private JButton отменаButton;
    private JPanel panel1;
    private JButton okButton;
    private JCheckBox watchCheckBox;
    private JComboBox trackerBox;
    private JTextField urlField;
    private JLabel fileLabel;
    private JButton обзорButton;
    private RequestManager manager = new RequestManager();
    private AddTorrent me;
    private File f;

    public AddTorrent(final File file) {
        //настройки окна
        super();
        me = this;
        
        if (file != null)
            setFile(file);

        setTitle("Добавить торрент");
        setSize(450, 250);
        setVisible(true);
        setResizable(false);
        AWTUtilities.setWindowOpacity(this, 0.9f);

        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;

        double width = getSize().getWidth() + screenWidth;
        double height = getSize().getHeight() + screenHeight;
        setLocation(new Long(Math.round(width / 4)).intValue(),
                new Long(Math.round(height / 4)).intValue());

        ImagePanel imagePanel = new ImagePanel(new BorderLayout());
        this.add(imagePanel);

        watchCheckBox.setOpaque(false);
        trackerBox.setOpaque(false);
        panel1.setOpaque(false);
        imagePanel.add(panel1);

        final JDialog dialog = this;

        addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        dialog.dispose();
                    }
                }
        );

        обзорButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                int returnVal = fc.showOpenDialog(me);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    setFile(fc.getSelectedFile());
                }
            }
        });

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (f == null) {
                        JOptionPane.showMessageDialog(me, "Выберите файл");
                        return;
                    }
                    AddTorrentMessage message = new AddTorrentMessage();
                    message.setWatched(watchCheckBox.isSelected());
                    message.setTracker((String) trackerBox.getSelectedItem());
                    message.setUrl(urlField.getText());
                    BufferedInputStream stream = new BufferedInputStream(new FileInputStream(f));
                    byte[] bytes = new byte[(int) f.length()];
                    stream.read(bytes);
                    message.setBytes(bytes);
                    String response = manager.addTorrent(message);
                    if (response != null) {
                        JOptionPane.showMessageDialog(me, response);
                        return;
                    }
                    dialog.dispose();
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(me, e1.getMessage());
                }
            }
        });

        отменаButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        //Заполнить параметры
        try {
            for (String s : manager.getTorrents()) {
                trackerBox.addItem(s);
            }
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }

    }

    public AddTorrent() {
        this(null);
    }

    public void setFile(File file) {
        f = file;
        fileLabel.setText(file.getName());
    }
}

