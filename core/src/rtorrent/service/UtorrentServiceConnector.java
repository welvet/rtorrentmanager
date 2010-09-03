package rtorrent.service;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import redstone.xmlrpc.util.Base64;
import rtorrent.utils.XpathUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

/**
 * User: welvet
 * Date: 03.09.2010
 * Time: 23:03:01
 */
public class UtorrentServiceConnector
{
    protected static final int TIMEOUT = 20000;

    private String host;
    private String port;
    private String login;
    private String pass;
    private String guiPath;


    private DefaultHttpClient httpClient = new DefaultHttpClient();
    private String reqPath;
    private String token;

    public UtorrentServiceConnector(String host, String port, final String login, final String pass, String guiPath)
    {
        this.host = host;
        this.port = port;
        this.login = login;
        this.pass = pass;
        this.guiPath = guiPath;

        HttpParams params = httpClient.getParams();
        HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), TIMEOUT);
        HttpConnectionParams.setSoTimeout(httpClient.getParams(), TIMEOUT);
        httpClient.setParams(params);

        httpClient.addRequestInterceptor(new HttpRequestInterceptor()
        {
            public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException
            {
                char[] chars = Base64.encode((login + ":" + pass).getBytes());
                httpRequest.setHeader("Authorization", "Basic " + String.valueOf(chars));
            }
        });

        reqPath = "http://" + this.host + ":" + this.port + "/" + this.guiPath + "/";
    }

    public void authAndRefreshToken() throws Exception
    {
        HttpGet get = new HttpGet();
        get.setURI(URI.create(reqPath + "token.html"));
        BasicHttpResponse response = (BasicHttpResponse) httpClient.execute(get);
        XpathUtils utils = new XpathUtils(response.getEntity().getContent());
        token = utils.doXPath("//DIV/text()");
    }

    public InputStreamReader execute(String request, String params) throws Exception
    {
        authAndRefreshToken();
        HttpGet get = new HttpGet();
        get.setURI(URI.create(reqPath + request + "?token=" + token + params));

        BasicHttpResponse response = (BasicHttpResponse) httpClient.execute(get);
        //создаем страничку
        InputStreamReader content = new InputStreamReader(response.getEntity().getContent());

        return content;
    }

}
