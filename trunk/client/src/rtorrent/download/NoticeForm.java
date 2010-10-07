package rtorrent.download;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.*;
import rtorrent.ConfigSingleton;
import rtorrent.init.Initialize;
import rtorrent.notice.client.ClientNotice;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * User: welvet
 * Date: 28.08.2010
 * Time: 0:16:18
 */
public class NoticeForm implements Runnable
{
    private static Display display;
    private Shell shell;
    private Point oldLocation;
    private static final int RANGE = 60;
    private List<ViewForm> forms = new ArrayList<ViewForm>();
    private List<ClientNotice> noticeList;
    private Integer iterator = 0;
    private static final int PER_PAGE = 4;
    private int iterMax;
    private static final int MAX_LEN = 22;
    private boolean rebuild = true;

    public NoticeForm(List<ClientNotice> list)
    {
        setList(list);
    }

    private void setList(List<ClientNotice> list)
    {

        noticeList = list;
        iterMax = ((list.size() - 1) / PER_PAGE) * PER_PAGE;
        iterator = iterMax;
    }

    private synchronized void initialize()
    {
        //иницилизация шела
        display = Initialize.display;
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

        //кнопка Настройки
        Button ok = new Button(shell, SWT.PUSH);
        ok.setText("Настройки");
        ok.setSize(80, 25);
        ok.addListener(SWT.Selection, new Listener()
        {
            public void handleEvent(Event event)
            {
                new NoticesSettingsDialog();
            }
        });

        //кнопка закрыть
        Button X = new Button(shell, SWT.PUSH);
        X.setText("X");
        X.setSize(MAX_LEN, 25);
        X.setLocation(180, 0);
        X.addListener(SWT.Selection, new Listener()
        {
            public void handleEvent(Event event)
            {
                shell.dispose();
            }
        });

        //кнопка назад
        Button prev = new Button(shell, SWT.None);
        prev.setText("Prev");
        prev.setSize(40, MAX_LEN);
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

        //кнопка вперед
        Button next = new Button(shell, SWT.None);
        next.setText("Next");
        next.setSize(40, MAX_LEN);
        next.setLocation(160, 280);
        next.addListener(SWT.Selection, new Listener()
        {
            public void handleEvent(Event event)
            {
                if (iterator < iterMax)
                {
                    iterator = iterator + PER_PAGE;
                    buildList();
                }
            }
        });


        Point location = ConfigSingleton.getLocation();
        shell.setLocation(location);
        oldLocation = location;

        shell.open();

        this.notify();
    }

    /**
     * Очистить старый лист и построить новый
     */
    private void buildList()
    {
        for (ViewForm form : forms)
            form.dispose();

        SimpleDateFormat format = new SimpleDateFormat("hh:mm dd.MM");

        int nodeIter = 0;
        for (int i = iterator; (i < iterator + PER_PAGE) && (i < noticeList.size()); i++)
        {
            ClientNotice notice = noticeList.get(i);

            String titleText = notice.getTorrentName();
            if (titleText.length() > MAX_LEN + 2)
            {
                titleText = titleText.substring(0, MAX_LEN) + "..";
            }

            String execute = null;
            if (notice.getLink() != null)
            {
                String s = notice.getLink();
                execute = s.replace(ConfigSingleton.getToReplace(), ConfigSingleton.getReplace());
            }

            createNode(nodeIter++, titleText, format.format(notice.getDate()), execute, notice);
        }
    }

    private void createNode(int id, String titleText, String stateText, final String execute, final ClientNotice clientNotice)
    {
        final ViewForm sash = new ViewForm(shell, SWT.NO_BACKGROUND);
        Label title = new Label(sash, SWT.NONE);
        title.setText(titleText);
        title.setLocation(0, 0);
        title.setSize(135, MAX_LEN);
        Label state = new Label(sash, SWT.NONE);
        state.setText(stateText);
        state.setLocation(0, MAX_LEN);
        state.setSize(90, MAX_LEN);

        if (execute != null)
        {
            Label link = new Label(sash, SWT.NONE);
            link.setText("(link)");
            link.setLocation(130, MAX_LEN);
            Color color = new Color(display, 0, 0, 255);
            link.setForeground(color);
            link.setSize(40, MAX_LEN);
            link.addListener(SWT.MouseDown, new Listener()
            {
                public void handleEvent(Event event)
                {
                    try                          
                    {
                        Runtime.getRuntime().exec(new String[] {ConfigSingleton.getCommand(), execute});
                    } catch (IOException e)
                    {
                        e.printStackTrace(); 
                    }
                }
            });
        }

        Label remove = new Label(sash, SWT.NONE);
        remove.setText("(X)");
        remove.setLocation(145, 0);
        remove.setSize(15, MAX_LEN);
        Color color = new Color(display, 0, 0, 255);
        remove.setForeground(color);
        remove.addListener(SWT.MouseDown, new Listener()
        {
            public void handleEvent(Event event)
            {
                noticeList.remove(clientNotice);
                ConfigSingleton.removeFromList(clientNotice);

                sash.setVisible(false);

                iterMax = ((noticeList.size() - 1) / PER_PAGE) * PER_PAGE;
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
                //если лист обновился, то отрисовываем его заного
                if (rebuild)
                {
                    rebuild = false;
                    buildList();
                }

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

    public Shell getShell()
    {
        return shell;
    }

    public static Display getDisplay()
    {
        return display;
    }

    /**
     * Обновить форму новым листом
     * @param clientNoticeList - лист
     */
    public void update(List<ClientNotice> clientNoticeList)
    {
        setList(clientNoticeList);
        rebuild = true;
    }
}
