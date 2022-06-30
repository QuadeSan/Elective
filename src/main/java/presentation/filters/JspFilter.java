package presentation.filters;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Special filter
 * The filter denies access to all jsp pages for non-admin users
 * when they try to connect to those tools using a direct link.
 */
@WebFilter(urlPatterns = "*.jsp")
public class JspFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(JspFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        logger.debug("JspFilter noticed us");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();

        logger.debug("Trying to obtain role");
        String userRole = (String) session.getAttribute("userRole");
        if (userRole == null || !userRole.equals("Admin")) {
            logger.debug("Not admin, access denied");
            session.setAttribute("errorMessage", "Access denied! " +
                    "You have no permission to visit that page");
            req.getRequestDispatcher("error.jsp").forward(request, response);
        } else {
            logger.debug("Admin role, you are welcome");
            chain.doFilter(request, response);
        }
    }
}
