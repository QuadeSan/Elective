package controller;

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
        String teacherLogin = req.getParameter("login");
        String teacherEmail = req.getParameter("email");
        String teacherName = req.getParameter("name");
        String teacherLastName = req.getParameter("lastName");
        String teacherPass = req.getParameter("psw");
        String teacherRepPass = req.getParameter("psw-repeat");

        HttpSession session = req.getSession();
        if (!teacherPass.equals(teacherRepPass)) {
            logger.info("Teacher's passwords don't match");
            session.setAttribute("errorMessage", "Passwords don't match");
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
