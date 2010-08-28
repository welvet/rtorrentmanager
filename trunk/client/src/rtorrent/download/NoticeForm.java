package rtorrent.download;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.*;
import rtorrent.ConfigSingleton;
import rtorrent.notice.client.ClientNotice;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * User: welvet
 * Date: 28.08.2010
 * Time: 0:16:18
 */
public class NoticeForm implements Runnable
{
    private Display display;
    private Shell shell;
    private Point oldLocation;
    private static final int RANGE = 60;
    private List<ViewForm> forms = new ArrayList<ViewForm>();
    private List<ClientNotice> noticeList;
    private Integer iterator = 0;
    private static final int PER_PAGE = 4;
    private int iterMax;

    public NoticeForm(List<ClientNotice> list)
    {
        noticeList = list;
        iterMax = ((list.size() - 1) / PER_PAGE) * PER_PAGE;
        iterator = iterMax;
    }

    private void initialize()
    {
        display = new Display();
        shell = new Shell(display, SWT.NO_TRIM | SWT.ON_TOP | SWT.SHADOW_NONE);
        shell.setAlpha(240);

        Listener l = new Listener()
        {
            Point origin;

            public void handleEvent(Event e)
            {
                switch (e.type)
                {
                    case SWT.MouseDown:
                        origin = new Point(e.x, e.y);
                        break;
                    case SWT.MouseUp:
                        origin = null;
                        break;
                    case SWT.MouseMove:
                        if (origin != null)
                        {
                            Point p = display.map(shell, null, e.x, e.y);
                            shell.setLocation(p.x - origin.x, p.y - origin.y);
                        }
                        break;
                }
            }
        };
        shell.addListener(SWT.MouseDown, l);
        shell.addListener(SWT.MouseUp, l);
        shell.addListener(SWT.MouseMove, l);
        shell.setSize(200, 300);

        InputStream stream = NoticeForm.class.getResourceAsStream("images/wall.png");
        Image image = new Image(display, stream);
        shell.setBackgroundImage(image);

        Button ok = new Button(shell, SWT.PUSH);
        ok.setText("Настройки");
        ok.setSize(80, 25);

        Button X = new Button(shell, SWT.PUSH);
        X.setText("X");
        X.setSize(20, 25);
        X.setLocation(180, 0);
        X.addListener(SWT.Selection, new Listener()
        {
            public void handleEvent(Event event)
            {
                shell.dispose();
            }
        });

        shell.setSize(200, 300);

        Button prev = new Button(shell, SWT.None);
        prev.setText("Prev");
        prev.setSize(40, 20);
        prev.setLocation(0, 280);
        prev.addListener(SWT.Selection, new Listener()
        {
            public void handleEvent(Event event)
            {
                if (iterator >= PER_PAGE)
                {
                    iterator = iterator - PER_PAGE;
                    buildList();
                }
            }
        });

        Button next = new Button(shell, SWT.None);
        next.setText("Next");
        next.setSize(40, 20);
        next.setLocation(160, 280);
        next.addListener(SWT.Selection, new Listener()
        {
            public void handleEvent(Event event)
            {
                if (iterator < iterMax )
                {
                    iterator = iterator + PER_PAGE;
                    buildList();
                }
            }
        });
        

        Point location = ConfigSingleton.getLocation();
        shell.setLocation(location);
        oldLocation = location;

        buildList();

        shell.open();
    }

    private void buildList()
    {
        for (ViewForm form : forms)
            form.dispose();

        int nodeIter = 0;
        for (int i = iterator; (i < iterator + PER_PAGE) && (i < noticeList.size()); i++)
        {
            ClientNotice notice = noticeList.get(i);
            createNode(nodeIter++, notice.getTorrentName(), "");
        }
    }

    private void createNode(int id, String titleText, String stateText)
    {
        ViewForm sash = new ViewForm(shell, SWT.NO_BACKGROUND);
        Label title = new Label(sash, SWT.NONE);
        title.setText(titleText);
        title.setLocation(0, 0);
        title.setSize(160, 20);

        Label state = new Label(sash, SWT.NONE);
        state.setText(stateText);
        state.setLocation(0, 20);
        state.setSize(40, 20);

        Label link = new Label(sash, SWT.NONE);
        link.setText("(link)");
        link.setLocation(130, 20);
        link.setSize(40, 20);
        link.addListener(SWT.MouseDown, new Listener()
        {
            public void handleEvent(Event event)
            {
                //todo open browser
                System.out.println("asd");
            }
        });

        sash.setSize(160, 40);
        sash.setLocation(18, 40 + (id * RANGE));

        forms.add(sash);
    }

    private void process()
    {
        while (!shell.isDisposed())
            if (!display.readAndDispatch())
            {
                Point location = shell.getLocation();
                if (!location.equals(oldLocation))
                {
                    ConfigSingleton.setLocation(location);
                    ConfigSingleton.update();
                    oldLocation = location;
                }
                display.sleep();
            }
    }

    public void run()
    {
        initialize();
        process();
    }


}
