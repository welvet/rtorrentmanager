package rtorrent.settings;

import com.sun.awt.AWTUtilities;
import rtorrent.ConfigSingleton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
 * User: welvet
 * Date: 27.06.2010
 * Time: 14:18:14
 */
public class SettingsDialog extends JDialog {

    private JPanel panel1;
    private JTextField hostFiled;
    private JTextField loginField;
    private JButton okButton;
    //utf8 ftw
    private JButton отменаButton;
    private JCheckBox stopTorrent;
    private JPasswordField passField;


    @Override
    public void paintComponents(Graphics g) {

    }

    public SettingsDialog() {
        //настройки окна
        super();
        setTitle("Настройки");
        setSize(450, 250);
        setVisible(true);
        setResizable(false);
        AWTUtilities.setWindowOpacity(this, 0.9f);

        hostFiled.setText(ConfigSingleton.getAddres());
        loginField.setText(ConfigSingleton.getLogin());
        passField.setText(ConfigSingleton.getPass());
        stopTorrent.setSelected(ConfigSingleton.getNeedStop());

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

        stopTorrent.setOpaque(false);
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

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ConfigSingleton.setAddres(hostFiled.getText());
                ConfigSingleton.setLogin(loginField.getText());
                ConfigSingleton.setPass(passField.getText());
                ConfigSingleton.setNeedStop(stopTorrent.isSelected());
                ConfigSingleton.update();
                dialog.dispose();
            }
        });

        отменаButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
    }
}
