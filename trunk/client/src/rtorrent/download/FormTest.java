package rtorrent.download;

/**
 * User: welvet
 * Date: 28.08.2010
 * Time: 0:18:39
 */
public class FormTest
{
    public static void main(String[] args) throws InterruptedException
    {
//        LastDownloadControler lastDownloadControler = LastDownloadControler.instance();
//        lastDownloadControler.run();

        LastDownloadControler.instance().showForm();
        Thread.sleep(2000);
        LastDownloadControler.instance().showForm();
    }
}
