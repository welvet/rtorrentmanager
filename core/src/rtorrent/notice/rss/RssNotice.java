package rtorrent.notice.rss;

import java.io.Serializable;
import java.util.Date;

/**
 * User: welvet
 * Date: 26.06.2010
 * Time: 18:05:25
 */
public class RssNotice implements Serializable{
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
                "<title>"+title+"</title>" +
                "<description>"+description+"</description>" +
                "<pubDate>"+pubDate.toString()+"</pubDate>" +
                "<link>"+link+"</link>" +
                "<author>"+author+"</author>" +
                "</item>";
    }
}
