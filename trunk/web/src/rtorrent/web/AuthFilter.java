package rtorrent.web;

import redstone.xmlrpc.util.Base64;
import rtorrent.config.Config;
import rtorrent.config.ConfigManager;
import rtorrent.utils.ContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: welvet
 * Date: 30.05.2010
 * Time: 1:56:17
 */
public class AuthFilter implements Filter {
    public void destroy() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = ((HttpServletRequest) servletRequest);
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        response.setCharacterEncoding("UTF-8");

        //проверяем авторизацию
        ConfigManager manager = (ConfigManager) ContextUtils.lookup("rconfig");
        Config config = manager.getConfig("server");

        if ((Boolean) config.getFieldValue("auth")) {
            if (request.getSession().getAttribute("auth") == null) {
                String login = (String) config.getFieldValue("login");
                String pass = (String) config.getFieldValue("pass");
                String secret = String.valueOf(Base64.encode((login + ":" + pass).getBytes()));  
                secret = "Basic " + secret;
                if (!secret.equals(request.getHeader("Authorization"))) {
                    response.setHeader("WWW-Authenticate", "Basic realm=\"Secure Area\"");
                    response.sendError(401);
                    return;
                }
                request.getSession().setAttribute("auth", true);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void init(FilterConfig filterConfig) throws ServletException {

    }
}
