package rtorrent.tracker.lostfilm;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import rtorrent.tracker.HttpHelper;
import rtorrent.tracker.TrackerException;
import rtorrent.tracker.WorkSaver;
import rtorrent.utils.XpathUtils;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * User: welvet
 * Date: 21.06.2010
 * Time: 20:54:26
 */
public class LostFilmHelper extends HttpHelper {
    private static final String LOSTFILM_TV = "lostfilm.tv";
    private static final String HOST = "http://www." + LOSTFILM_TV + "/";
    private static final String HOSTA = "http://www." + LOSTFILM_TV;
    private static final String THEME = HOSTA + "/browse.php?cat=";
    private static String loginUrl = HOST + "useri.php";
    private WorkSaver saver;

    public LostFilmHelper(String login, String password, File workDir) {
        super(login, password, workDir);
        saver = new WorkSaver(new File(workDir.getAbsolutePath() + "/lostfilm.dat"));
        torrentsMap = saver.load();
    }


    public void setRemoteCookie(LostFilmCookie cookie) {
        BasicClientCookie netscapeCookie = new BasicClientCookie("uid", cookie.getUid());
        netscapeCookie.setVersion(0);
        netscapeCookie.setDomain("." + LOSTFILM_TV);
        netscapeCookie.setPath("/");
        httpClient.getCookieStore().addCookie(netscapeCookie);

        netscapeCookie = new BasicClientCookie("pass", cookie.getPass());
        netscapeCookie.setVersion(0);
        netscapeCookie.setDomain("." + LOSTFILM_TV);
        netscapeCookie.setPath("/");
        httpClient.getCookieStore().addCookie(netscapeCookie);
    }

    public boolean checkAuth() throws TrackerException {
        XpathUtils utils = null;
        try {
            HttpGet httpGet = new HttpGet(new URI(HOST));
            BasicHttpResponse response = (BasicHttpResponse) httpClient.execute(httpGet);
            //создаем страничку
            utils = new XpathUtils(response.getEntity().getContent());

            return !login.isEmpty() && utils.doXPath("//DIV[@class=\"prof\"]/SPAN[1]/text()").startsWith(login);
        } catch (Exception e) {
            log.warn("Не удалось авторизироваться на lostfilm.tv. Подробности в лог файле");
            if (utils != null) {
                try {
                    log.info(utils.getFile());
                } catch (Exception e1) {
                    throw new TrackerException(e);
                }
            }
            throw new TrackerException(e);
        }
    }

    public void auth() throws TrackerException {
        try {
            httpClient.getCookieStore().clear();
            HttpPost httpPost = new HttpPost();
            httpPost.setURI(new URI(loginUrl));
            //выставляем параметры для авторизации
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            formparams.add(new BasicNameValuePair("FormLogin", login));
            formparams.add(new BasicNameValuePair("FormPassword", password));
            formparams.add(new BasicNameValuePair("module", "1"));
            formparams.add(new BasicNameValuePair("repage", "user"));
            formparams.add(new BasicNameValuePair("act", "login"));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "windows-1251");
            //выставялем рефер
            httpPost.setEntity(entity);
            httpPost.addHeader("Referer", loginUrl);

            HttpResponse response = httpClient.execute(httpPost);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            //считываем ответ в null
            while (true) {
                String ln = reader.readLine();
                if (ln == null)
                    break;
            }
            log.debug("Попытка авторизации на lostfilm прошла успешно");
        } catch (Exception e) {
            throw new TrackerException(e);
        }
    }

    public LostFilmCookie getCookie() {
        LostFilmCookie filmCookie = new LostFilmCookie();
        for (Cookie cookie : httpClient.getCookieStore().getCookies()) {
            if (cookie.getName().equals("uid"))
                filmCookie.setUid(cookie.getValue());
            if (cookie.getName().equals("pass"))
                filmCookie.setPass(cookie.getValue());
        }
        return filmCookie;
    }

    public boolean checkTorrent(String url) throws TrackerException {
        try {
            HttpGet httpGet = new HttpGet(new URI(THEME + url));
            BasicHttpResponse response = (BasicHttpResponse) httpClient.execute(httpGet);
            XpathUtils utils = new XpathUtils(response.getEntity().getContent());

            String string = torrentsMap.get(url);
            String date = utils.doXPath("//A[@class=\"a_download\"]/@href");

            if (date == null) {
                log.warn("Не удалось проверить торрент с url:" + url + ". Подробности в лог файле");
                log.info(utils.getFile());
                throw new TrackerException("Ошибка проверки файла");
            }
            //обновляем мапу
            torrentsMap.put(url, date);
            saver.save(torrentsMap);

            if (string != null) {
                log.debug("Торрент с url " + url + " проверен");
                //если строки не совпадают, то нужно обновлять
                return !string.equals(date);
            }

            saver.save(torrentsMap);
            log.info("Торрент " + url + " не найден в списке");
            return true;
        } catch (Exception e) {
            throw new TrackerException(e);
        }
    }

    public File downloadFile(String url) throws TrackerException {
        try {
            String s = torrentsMap.get(url);
            s = s.replace(" ", "%20");
            HttpGet httpGet = new HttpGet(new URI(HOSTA + s));
            BasicHttpResponse response = (BasicHttpResponse) httpClient.execute(httpGet);

            File file = new File(workDir.getAbsolutePath() + "/" + System.currentTimeMillis() + url + ".torrent");
            OutputStream oStream = new FileOutputStream(file);
            response.getEntity().writeTo(oStream);
            oStream.close();

            return file;
        } catch (Exception e) {
            throw new TrackerException(e);
        }
    }
}
