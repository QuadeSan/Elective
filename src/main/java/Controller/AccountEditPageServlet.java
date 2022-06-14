package Controller;

import DataBaseLayer.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet("/editaccount")
public class AccountEditPageServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(AccountEditPageServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("doGet of /editaccount with forward to edit.jsp");
//        HttpSession session = req.getSession();
//        String currentUser = (String) session.getAttribute("currentUser");
//        if (currentUser == null) {
//            User guest = new User();
//            guest.setLogin("guest");
//            session.setAttribute("currentUser", "guest");
//        }
        req.getRequestDispatcher("edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("doPost of /editaccount with forward to account.jsp");
        req.getRequestDispatcher("account.jsp").forward(req, resp);
    }
}
