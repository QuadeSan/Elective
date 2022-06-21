package controller;

import services.StudentService;
import services.impl.StudentServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginPageServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(LoginPageServlet.class);
    private StudentService studentService;

    @Override
    public void init() throws ServletException {
        logger.debug("creating studentService");
        studentService = new StudentServiceImpl();
        logger.debug("StudentService was created");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("doGet of /login with forward to login.jsp");
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("doPost of /login with forward to login.jsp");

        String login = req.getParameter("login");
        String name = req.getParameter("name");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String password = req.getParameter("psw");
        String reppassword = req.getParameter("psw-repeat");

        HttpSession session = req.getSession();

        if (!password.equals(reppassword)) {
            logger.info("Passwords don't match");
            session.setAttribute("errorMessage", "Passwords don't match");
            resp.sendRedirect("register");
            return;
        }
        Response response = studentService.createStudent(login, password, email, name, lastName);
        if (response.isSuccess()) {
            logger.info("Student was created");
            session.setAttribute("infoMessage", response.getMessage());
            resp.sendRedirect("login");
        } else {
            logger.error("Unsuccessful operation" + response.getMessage());
            session.setAttribute("errorMessage", response.getMessage());
            resp.sendRedirect("register");
        }
    }
}
