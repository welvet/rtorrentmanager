package rtorrent.tracker.rutracker;

import org.apache.http.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.log4j.Logger;
import rtorrent.utils.LoggerSingleton;
import rtorrent.utils.XpathUtils;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * User: welvet
 * Date: 14.06.2010
 * Time: 0:45:20
 */
public class RuTrackerHelper {
    private String login;
    private String password;
    private Logger log = LoggerSingleton.getLogger();
    private DefaultHttpClient httpClient = new DefaultHttpClient();
    private static final String USER_AGENT = "Mozilla/5.0 (Windows; U; Windows NT 5.1; ru; rv:1.9.0.4) Gecko/2008102920 AdCentriaIM/1.7 Firefox/3.0.4\"";
    private static final String loginUrl = "http://login.rutracker.org/forum/login.php";
    private static final String HOST = "http://rutracker.org/";
    private static final String THEME = "http://rutracker.org/forum/viewtopic.php?t=";
    private static final String DOWNLOAD = "http://dl.rutracker.org/forum/dl.php?t=";

    public RuTrackerHelper(String login, String password) {
        this.login = login;
        this.password = password;
        //выставл€ем агента
        httpClient.addRequestInterceptor(new HttpRequestInterceptor() {
            public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
                httpRequest.setHeader(HTTP.USER_AGENT, USER_AGENT);
            }
        });

        HttpParams params = httpClient.getParams();
        HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 10000);
        HttpConnectionParams.setSoTimeout(httpClient.getParams(), 10000);
        httpClient.setParams(params);
    }

    public void auth() {
        try {
            HttpPost httpPost = new HttpPost();
            httpPost.setURI(new URI(loginUrl));

            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            formparams.add(new BasicNameValuePair("login_username", login));
            formparams.add(new BasicNameValuePair("login_password", password));
            formparams.add(new BasicNameValuePair("autologin", "on"));
            //не€сно зачем им это строка, но отправл€ем "????" (¬ход)
            formparams.add(new BasicNameValuePair("login", "%C2%F5%EE%E4"));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "windows-1251");

            httpPost.setEntity(entity);
            httpPost.addHeader("Referer", loginUrl);

            HttpResponse response = httpClient.execute(httpPost);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            //считываем ответ
            while (true) {
                String ln = reader.readLine();
                if (ln == null)
                    break;
            }
        } catch (Exception e) {
            log.warn(e);
        }
    }

    public void setRemoteCookie(String cookie) {
        BasicClientCookie netscapeCookie = new BasicClientCookie("bb_data", cookie);
        netscapeCookie.setVersion(0);
        netscapeCookie.setDomain(".rutracker.org");
        netscapeCookie.setPath("/");
        httpClient.getCookieStore().addCookie(netscapeCookie);
    }

    public String getCookie() {
        for (Cookie cookie : httpClient.getCookieStore().getCookies()) {
            if (cookie.getName().equals("bb_data"))
                return cookie.getValue();
        }
        return null;
    }

    public Boolean checkAuth() {
        try {
            HttpGet httpGet = new HttpGet(new URI(HOST + "forum/index.php"));
            BasicHttpResponse response = (BasicHttpResponse) httpClient.execute(httpGet);
            //создаем страничку
            XpathUtils utils = new XpathUtils(response.getEntity().getContent());

            if (login.equals(utils.doXPath("//DIV[@class=\"topmenu\"]//B[@class=\"med\"]/text()")))
                return true;
        } catch (Exception e) {
            log.warn(e);
        }
        return false;
    }

    public Boolean checkTorrent(String url) {
        try {
            HttpGet httpGet = new HttpGet(new URI(THEME + url));
            BasicHttpResponse response = (BasicHttpResponse) httpClient.execute(httpGet);
            XpathUtils utils = new XpathUtils(response.getEntity().getContent());
            //todo свер€ем дату с сохраненной в файле
            System.out.println(utils.doXPath("//DIV[@id=\"tor-reged\"]//SPAN[@title=\"«арегистрирован\"]/text()"));

        } catch (Exception e) {
            log.warn(e);
        }
        return false;
    }

    public File downloadFile(String url) {
        try {
            BasicClientCookie netscapeCookie = new BasicClientCookie("bb_dl", url);
            netscapeCookie.setVersion(0);
            netscapeCookie.setDomain(".rutracker.org");
            netscapeCookie.setPath("/");
            httpClient.getCookieStore().addCookie(netscapeCookie);

            HttpGet httpGet = new HttpGet(new URI(DOWNLOAD + url));
            BasicHttpResponse response = (BasicHttpResponse) httpClient.execute(httpGet);

            File file = new File("torrent.torrent");
            OutputStream oStream = new FileOutputStream(file);
            response.getEntity().writeTo(oStream);
            oStream.close();

        } catch (Exception e) {
            log.warn(e);
        }
        return null;
    }
}
