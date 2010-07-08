package rtorrent.notice.rss;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * User: welvet
 * Date: 26.06.2010
 * Time: 18:05:25
 */
public class RssNotice implements Serializable {
    public static SimpleDateFormat RFC822DATEFORMAT = new SimpleDateFormat("EEE', 'dd' 'MMM' 'yyyy' 'HH:mm:ss' 'Z", Locale.US);
    private String title;
    private String description;
    private Date pubDate;
    private String link;
    private String author;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String toString() {
        return "<item>" +
                "<title>" + title + " (" + RFC822DATEFORMAT.format(pubDate) + ")" + "</title>" +
                "<description>" + description + "</description>" +
                "<pubDate>" + RFC822DATEFORMAT.format(pubDate) + "</pubDate>" +
                "<link>" + link + "</link>" +
                "<author>" + author + "</author>" +
                "</item>";
    }
}
