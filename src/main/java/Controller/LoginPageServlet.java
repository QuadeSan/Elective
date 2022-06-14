package Controller;

import DataBaseLayer.QueryResult;
import Services.StudentService;
import ServicesImpl.StudentServiceImpl;
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

    private static Logger logger = LogManager.getLogger(LoginPageServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("doGet of /login with forward to login.jsp");
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("doPost of /login with forward to login.jsp");
        HttpSession session = req.getSession();
        logger.debug("creating studentService");
        StudentService studentService = new StudentServiceImpl();
        logger.debug("StudentService was created");
        String login = req.getParameter("login");
        String email = req.getParameter("email");
        String password = req.getParameter("psw");
        String reppassword = req.getParameter("psw-repeat");
        if (!password.equals(reppassword)) {
            logger.info("Passwords don't match");
            session.setAttribute("errorMessage", "Passwords don't match");
            resp.sendRedirect("register");
            return;
        }
        QueryResult queryResult = studentService.createStudent(login, password, email);
        if (queryResult.getResult()) {
            logger.info("Student was created");
            session.setAttribute("infoMessage", "Account was successfully created!");
            resp.sendRedirect("login");
        } else {
            logger.debug("User already exist, redirect to /register");
            session.setAttribute("errorMessage", queryResult.getException());
            resp.sendRedirect("register");
        }
//        if (studentService.findStudent(login) == null) {
//            studentService.createStudent(login, password, email);
//            logger.info("Student was created");
//            session.setAttribute("infoMessage", "Account was successfully created!");
//            resp.sendRedirect("login");
//        } else {
//            logger.debug("User already exist, redirect to /register");
//            session.setAttribute("errorMessage", "Login already exist");
//            resp.sendRedirect("register");
//        }
    }
}
