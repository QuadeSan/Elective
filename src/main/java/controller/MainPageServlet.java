package controller;

import application.entity.Administrator;
import application.entity.Student;
import application.entity.Teacher;
import application.services.AdministratorService;
import application.services.StudentService;
import application.services.TeacherService;
import application.services.impl.AdministratorServiceImpl;
import application.services.impl.StudentServiceImpl;
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

@WebServlet("/main")
public class MainPageServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(MainPageServlet.class);
    private StudentService studentService;
    private TeacherService teacherService;
    private AdministratorService administratorService;

    @Override
    public void init() throws ServletException {
        studentService = new StudentServiceImpl();
        logger.debug("StudentService was created");
        teacherService = new TeacherServiceImpl();
        logger.debug("TeacherService was created");
        administratorService = new AdministratorServiceImpl();
        logger.debug("AdministratorService was created");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("doGet of /main with forward to main.jsp");
        HttpSession session = req.getSession();
        String userRole = (String) session.getAttribute("userRole");
        if (userRole == null) {
            session.setAttribute("userRole", "guest");
        }
        req.getRequestDispatcher("main.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("doPost of /main with redirect to main");
        String login = req.getParameter("uname");
        String password = req.getParameter("psw");
        HttpSession session = req.getSession();
        Student currentStudent = studentService.findStudent(login, password);
        if (currentStudent != null) {
            session.setAttribute("currentUser", currentStudent);
            session.setAttribute("userRole", "Student");
            logger.info("You was logged as " + currentStudent);
            resp.sendRedirect("main");
            return;
        }
        logger.info("Student was not found");
        Teacher currentTeacher = teacherService.findTeacher(login, password);
        if (currentTeacher != null) {
            session.setAttribute("currentUser", currentTeacher);
            session.setAttribute("userRole", "Teacher");
            logger.info("You was logged as " + currentTeacher);
            resp.sendRedirect("main");
            return;
        }
        logger.info("Teacher was not found");
        Administrator currentAdmin = administratorService.findAdministrator(login, password);
        if (currentAdmin != null) {
            session.setAttribute("currentUser", currentAdmin);
            session.setAttribute("userRole", "Admin");
            logger.info("You was logged as " + currentAdmin);
            resp.sendRedirect("main");
            return;
        }
        session.setAttribute("errorMessage",
                "Unable to login. Either Login or Password is incorrect");
        resp.sendRedirect("login");
    }
}