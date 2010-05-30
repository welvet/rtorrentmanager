package rtorrent.web;

import sun.misc.BASE64Encoder;

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
        //todo в дальнейшем логин и пасс будут считываться с конфига
        if (request.getSession().getAttribute("auth") != null) {
            String secret = new BASE64Encoder().encodeBuffer("root:pass".getBytes());
            secret = "Basic " + secret.replaceAll("\r\n", "");
            if (!secret.equals(request.getHeader("Authorization"))) {
                response.setHeader("WWW-Authenticate", "Basic realm=\"Secure Area\"");
                response.sendError(401);
                request.getSession().setAttribute("auth", true);
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    public void init(FilterConfig filterConfig) throws ServletException {

    }
}
