package rtorrent.download;

import com.sun.awt.AWTUtilities;
import rtorrent.ConfigSingleton;
import rtorrent.settings.ImagePanel;

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
public class NoticesSettingsDialog extends JDialog
{

    private JPanel panel1;
    private JTextField replaceFiled;
    private JButton okButton;
    //utf8 ftw
    private JButton отменаButton;
    private JCheckBox needCheck;
    private JTextField toReplaceField;
    private JComboBox noticesType;


    @Override
    public void paintComponents(Graphics g)
    {

    }

    public NoticesSettingsDialog()
    {
        //настройки окна
        super();
        setTitle("Настройки");
        setSize(450, 250);
        setVisible(true);
        setResizable(false);
        AWTUtilities.setWindowOpacity(this, 0.9f);

        replaceFiled.setText(ConfigSingleton.getReplace());
        toReplaceField.setText(ConfigSingleton.getToReplace());

        //TODO: hard code state
        noticesType.addItem("done");
        noticesType.addItem("update");

        noticesType.setSelectedItem(ConfigSingleton.getNoticesType());
        needCheck.setSelected(ConfigSingleton.getNeedCheck());

        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;

        double width = getSize().getWidth() + screenWidth;
        double height = getSize().getHeight() + screenHeight;

        ImagePanel imagePanel = new ImagePanel(new BorderLayout());
        this.add(imagePanel);

        needCheck.setOpaque(false);
        panel1.setOpaque(false);
        imagePanel.add(panel1);

        final JDialog dialog = this;

        addWindowListener(
                new WindowAdapter()
                {
                    public void windowClosing(WindowEvent e)
                    {
                        dialog.dispose();
                    }
                }
        );

        okButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {

                ConfigSingleton.setReplace(replaceFiled.getText());
                ConfigSingleton.setToReplace(toReplaceField.getText());
                ConfigSingleton.setNoticesType((String) noticesType.getSelectedItem());
                ConfigSingleton.setNeedCheck(needCheck.isSelected());
                ConfigSingleton.update();
                dialog.dispose();
            }
        });

        отменаButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                dialog.dispose();
            }
        });

        setLocation(new Long(Math.round(width / 4)).intValue(),
                new Long(Math.round(height / 4)).intValue());
    }
}