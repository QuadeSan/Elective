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
 * Servlet for editing personal account page
 * {@link #doGet(HttpServletRequest, HttpServletResponse) Get} method check
 * is user logged and if not, it redirects user to main page showing error message
 */
@WebServlet("/editaccount")
public class AccountEditPageServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(AccountEditPageServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("doGet of /editaccount with forward to edit.jsp");
        HttpSession session = req.getSession();
        String userRole = (String) session.getAttribute("userRole");
        if (userRole == null || userRole.equals("guest")) {
            session.setAttribute("errorMessage","You are not logged");
            resp.sendRedirect("main");
            return;
        }
        req.getRequestDispatcher("edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("doPost of /editaccount with redirect to editaccount");
        resp.sendRedirect("editaccount");
    }
}
