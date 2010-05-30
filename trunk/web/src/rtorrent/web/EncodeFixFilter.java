package rtorrent.web;

import javax.servlet.*;
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
        servletResponse.setCharacterEncoding("UTF-8");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void init(FilterConfig filterConfig) throws ServletException {

    }
}
