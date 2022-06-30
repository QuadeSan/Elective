package presentation.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet sets current user attribute to null value
 * and user role attribute to guest value
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(LogoutServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("doGet of /logout with redirect to main.jsp");

        HttpSession session = req.getSession();
        session.setAttribute("currentUser", null);
        session.setAttribute("userRole", "guest");

        resp.sendRedirect("main");
    }
}
