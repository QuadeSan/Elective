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
    private CourseService courseService;
    private AssignmentService assignmentService;
    private StudentService studentService;

    @Override
    public void init() throws ServletException {
        teacherService = new TeacherServiceImpl();
        logger.debug("TeacherService was created");
        courseService = new CourseServiceImpl();
        logger.debug("CourseService was created");
        assignmentService = new AssignmentServiceImpl();
        logger.debug("AssignmentService was created");
        studentService = new StudentServiceImpl();
        logger.debug("StudentService was created");
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
            case "createCourse":
                logger.debug("Creating course process started");
                createNewCourse(req, resp);
                break;
            case "assignTeacher":
                logger.debug("Teacher assignment process started");
                assignTeacherToCourse(req, resp);
                break;
            case "lockStudent":
                logger.debug("Locking / unlocking student");
                lockStudent(req, resp);
                break;
            case "deleteCourse":
                logger.debug("Deleting course");
                deleteCourse(req, resp);
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

    private void createNewCourse(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String courseTitle = req.getParameter("title");
        String courseTopic = req.getParameter("topic");

        HttpSession session = req.getSession();

        OperationResult operationResult = courseService.createCourse(courseTopic, courseTitle);
        if (operationResult.isSuccess()) {
            session.setAttribute("infoMessage", operationResult.getMessage());
        } else {
            session.setAttribute("errorMessage", operationResult.getMessage());
        }
        resp.sendRedirect("admin");
    }

    private void assignTeacherToCourse(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int courseID = Integer.parseInt(req.getParameter("course-id"));
        int teacherID = Integer.parseInt(req.getParameter("teacher-id"));

        HttpSession session = req.getSession();

        OperationResult response = assignmentService.assignTeacherToCourse(courseID, teacherID);
        if (response.isSuccess()) {
            session.setAttribute("infoMessage", response.getMessage());
        } else {
            session.setAttribute("errorMessage", "Something went wrong! " +
                    response.getMessage());
        }
        resp.sendRedirect("admin");
    }

    private void lockStudent(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int studentID = Integer.parseInt(req.getParameter("student-id"));
        String status = req.getParameter("student-status");

        HttpSession session = req.getSession();

        OperationResult operationResult = studentService.lockStudent(studentID, status);
        if (operationResult.isSuccess()) {
            session.setAttribute("infoMessage", operationResult.getMessage());
        } else {
            session.setAttribute("errorMessage", operationResult.getMessage());
        }
        resp.sendRedirect("admin");
    }

    private void deleteCourse(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int courseID = Integer.parseInt(req.getParameter("course-id"));

        HttpSession session = req.getSession();

        OperationResult operationResult = courseService.deleteCourse(courseID);
        if (operationResult.isSuccess()) {

            session.setAttribute("infoMessage", operationResult.getMessage());
        } else {
            session.setAttribute("errorMessage", operationResult.getMessage());
        }
        resp.sendRedirect("admin");
    }

}