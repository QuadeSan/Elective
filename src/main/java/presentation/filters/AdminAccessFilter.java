package presentation.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Special filter configured in web.xml descriptor
 * The filter denies access to the admin tools pages for non-admin users
 * when they try to connect to those tools using a direct link.
 */
@WebFilter
public class AdminAccessFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(AdminAccessFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.debug("AdminAccessFilter in work");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();

        logger.debug("Trying to obtain role");
        String userRole = (String) session.getAttribute("userRole");
        if (userRole == null || !userRole.equals("Admin")) {
            logger.debug("Not admin, access denied");
            session.setAttribute("errorMessage", "Access denied! " +
                    "You have no permission to visit that page");
            resp.sendRedirect("error");
        } else {
            logger.debug("Admin role, you are welcome");
            chain.doFilter(request, response);
        }
    }
}
