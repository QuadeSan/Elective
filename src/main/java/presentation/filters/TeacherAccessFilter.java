package presentation.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebFilter("/journal")
public class TeacherAccessFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(TeacherAccessFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        logger.debug("TeacherAccessFilter in work");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();

        logger.debug("Trying to obtain role");
        String userRole = (String) session.getAttribute("userRole");
        if (!userRole.equals("Teacher")) {
            logger.debug("Not teacher, access denied");
            session.setAttribute("errorMessage", "Access denied! " +
                    "You have no permission to visit that page");
            req.getRequestDispatcher("error.jsp").forward(request, response);
        } else {
            logger.debug("Teacher role, you are welcome");
            chain.doFilter(request, response);
        }
    }
}
