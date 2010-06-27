package rtorrent.settings;

import javax.swing.*;
import java.awt.*;
import java.net.URL;


/**
 * User: welvet
 * Date: 27.06.2010
 * Time: 15:22:04
 */
public class ImagePanel extends JPanel {

    public ImagePanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
    }

    public ImagePanel(LayoutManager layout) {
        super(layout);
    }

    public ImagePanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    public ImagePanel() {
        
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(createImage("images/background.png", null), 0, 0, null);
    }

    protected static Image createImage(String path, String description) {
        URL imageURL = ImagePanel.class.getResource(path);

        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }
}
