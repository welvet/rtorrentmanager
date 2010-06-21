package rtorrent.tracker;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.log4j.Logger;
import rtorrent.utils.LoggerSingleton;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * User: welvet
 * Date: 21.06.2010
 * Time: 20:52:20
 */
public class HttpHelper {
    protected String login;
    protected String password;
    protected Logger log = LoggerSingleton.getLogger();
    protected DefaultHttpClient httpClient = new DefaultHttpClient();
    protected static final String USER_AGENT = "Mozilla/5.0 (Windows; U; Windows NT 5.1; ru; rv:1.9.0.4) Gecko/2008102920 AdCentriaIM/1.7 Firefox/3.0.4\"";
    protected static final int TIMEOUT = 20000;
    protected Map<String, String> torrentsMap;
    protected File workDir;

    public HttpHelper(String login, String password, File workDir) {
        this.login = login;
        this.password = password;
        this.workDir = workDir;
        httpClient.addRequestInterceptor(new HttpRequestInterceptor() {
            public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
                httpRequest.setHeader(HTTP.USER_AGENT, USER_AGENT);
            }
        });
        //выставляем таймаут
        HttpParams params = httpClient.getParams();
        HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), TIMEOUT);
        HttpConnectionParams.setSoTimeout(httpClient.getParams(), TIMEOUT);
        httpClient.setParams(params);
    }
}
