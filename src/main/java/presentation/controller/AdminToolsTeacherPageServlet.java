package presentation.controller;

import application.OperationResult;
import application.services.TeacherService;
import application.services.impl.TeacherServiceImpl;
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
 * Servlet for teachers admin tools
 * {@link #doPost(HttpServletRequest, HttpServletResponse) Post} method
 * used to handle create new teacher request from {@link AdminToolsTeacherPageServlet}
 */
@WebServlet("/toolsteacher")
public class AdminToolsTeacherPageServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(AdminToolsTeacherPageServlet.class);
    private TeacherService teacherService;

    @Override
    public void init() throws ServletException {
        teacherService = new TeacherServiceImpl();
        logger.debug("TeacherService was created");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("doGet of /toolsteacher with forward to toolsteacher.jsp");
        req.getRequestDispatcher("toolsteacher.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("doPost of /toolsteacher with redirect to /toolsteacher");

        switch (req.getParameter("adminAction")) {
            case "createTeacher":
                logger.debug("Creating teacher process started");
                createNewTeacher(req, resp);
                break;
        }
    }

    private void createNewTeacher(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        StringBuilder errors = new StringBuilder();

        String teacherLogin = req.getParameter("login");
        if (teacherLogin.isBlank() || teacherLogin.length() > 15) {
            errors.append("Login can't be black or longer then 15 characters").append("\n");
        }

        String teacherEmail = req.getParameter("email");
        if (teacherEmail.isBlank() || !teacherEmail.contains("@")) {
            errors.append("Login can't be black and must contain @ symbol").append("\n");
        }

        String teacherName = req.getParameter("name");
        if (teacherName.isBlank() || teacherName.length() > 15) {
            errors.append("Name can't be black or longer then 15 characters").append("\n");
        }

        String teacherLastName = req.getParameter("lastName");
        if (teacherLastName.isBlank() || teacherLastName.length() > 15) {
            errors.append("Last name can't be black or longer then 15 characters").append("\n");
        }

        String teacherPass = req.getParameter("psw");
        String teacherRepPass = req.getParameter("psw-repeat");
        if (!teacherPass.equals(teacherRepPass)) {
            errors.append("Passwords don't match").append("\n");
        }

        HttpSession session = req.getSession();

        if (errors.length() > 0) {
            session.setAttribute("errorMessage", errors.toString());
            resp.sendRedirect("toolsteacher");
            return;
        }

        OperationResult operationResult = teacherService.createTeacher(teacherLogin, teacherPass, teacherEmail, teacherName, teacherLastName);
        if (operationResult.isSuccess()) {
            logger.info("Teacher was created");
            session.setAttribute("infoMessage", operationResult.getMessage());
        } else {
            logger.debug("User already exist, redirect to /admin");
            session.setAttribute("errorMessage", operationResult.getMessage());
        }
        resp.sendRedirect("toolsteacher");
    }
}
