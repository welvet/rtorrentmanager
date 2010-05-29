package rtorrent.web;

import winstone.WinstoneResponse;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: welvet
 * Date: 30.05.2010
 * Time: 1:56:17
 */
public class EncodeFixFilter implements Filter {
    public void destroy() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ((WinstoneResponse) ((HttpServletResponse) servletResponse)).setCharacterEncoding("UTF8");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void init(FilterConfig filterConfig) throws ServletException {

    }
}
