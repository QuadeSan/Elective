package controller;

import application.*;
import application.services.impl.AssignmentServiceImpl;
import application.services.impl.CourseServiceImpl;
import application.services.impl.StudentServiceImpl;
import application.services.impl.TeacherServiceImpl;
import application.services.AssignmentService;
import application.services.CourseService;
import application.services.StudentService;
import application.services.TeacherService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/admin")
public class AdminPageServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(AdminPageServlet.class);
    private TeacherService teacherService;
    private AssignmentService assignmentService;

    @Override
    public void init() throws ServletException {
        teacherService = new TeacherServiceImpl();
        logger.debug("TeacherService was created");
        assignmentService = new AssignmentServiceImpl();
        logger.debug("AssignmentService was created");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("doGet of /admin with forward to admin.jsp");
        req.getRequestDispatcher("admin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("doPost of /admin with redirect to /admin");
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
            resp.sendRedirect("admin");
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
        resp.sendRedirect("admin");
    }
}