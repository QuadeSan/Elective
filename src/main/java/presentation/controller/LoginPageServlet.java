package presentation.controller;

import application.OperationResult;
import application.services.StudentService;
import application.services.impl.StudentServiceImpl;
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
        StringBuilder errors = new StringBuilder();

        String login = req.getParameter("login");
        if (login.isBlank() || login.length() > 15) {
            errors.append("Login can't be black or longer then 15 characters").append("\n");
        }

        String name = req.getParameter("name");
        if (name.isBlank() || name.length() > 15) {
            errors.append("Name can't be black or longer then 15 characters").append("\n");
        }

        String lastName = req.getParameter("lastName");
        if (lastName.isBlank() || lastName.length() > 15) {
            errors.append("Last name can't be black or longer then 15 characters").append("\n");
        }

        String email = req.getParameter("email");
        if (email.isBlank() || !email.contains("@")) {
            errors.append("Login can't be black and must contain @ symbol").append("\n");
        }

        String password = req.getParameter("psw");
        String reppassword = req.getParameter("psw-repeat");
        if (!password.equals(reppassword)) {
            errors.append("Passwords don't match").append("\n");
        }

        HttpSession session = req.getSession();

        if (errors.length() > 0) {
            session.setAttribute("errorMessage", errors.toString());
            resp.sendRedirect("register");
            return;
        }

        OperationResult operationResult = studentService.createStudent(login, password, email, name, lastName);
        if (operationResult.isSuccess()) {
            logger.info("Student was created");
            session.setAttribute("infoMessage", operationResult.getMessage());
            resp.sendRedirect("login");
        } else {
            logger.error("Unsuccessful operation" + operationResult.getMessage());
            session.setAttribute("errorMessage", operationResult.getMessage());
            resp.sendRedirect("register");
        }
    }
}
