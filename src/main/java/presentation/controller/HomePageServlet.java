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
 * Servlet for welcome page
 * Gives access to index.jsp for all non-admin users
 */
@WebServlet("/home")
public class HomePageServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(HomePageServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("doGet of /home with forward to index.jsp");
        req.getRequestDispatcher("index.jsp").forward(req,resp);
    }
}
