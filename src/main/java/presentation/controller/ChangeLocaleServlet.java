package presentation.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet provide access to changeLocale.jsp
 * file for all non-admin users
 */
@WebServlet("/changelocale")
public class ChangeLocaleServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(ChangeLocaleServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Changing locale with forward to changeLocale.jsp");
        req.getRequestDispatcher("changeLocale.jsp").forward(req, resp);
    }
}
