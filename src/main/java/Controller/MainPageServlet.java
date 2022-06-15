package Controller;

import DataBaseLayer.entity.Administrator;
import DataBaseLayer.entity.Student;
import DataBaseLayer.entity.Teacher;
import DataBaseLayer.entity.User;
import Services.AdministartorService;
import Services.StudentService;
import Services.TeacherService;
import ServicesImpl.AdministratorServiceImpl;
import ServicesImpl.StudentServiceImpl;
import ServicesImpl.TeacherServiceImpl;
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

    private static Logger logger = LogManager.getLogger(MainPageServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("doGet of /main with forward to main.jsp");
        HttpSession session = req.getSession();
        String userRole = (String) session.getAttribute("userRole");
        if (userRole == null) {
            User guest = new User();
            guest.setLogin("guest");
            session.setAttribute("currentUser", guest);
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
        StudentService studentService = new StudentServiceImpl();
        Student currentStudent = studentService.findStudent(login, password);
        if (currentStudent != null) {
            session.setAttribute("currentUser", currentStudent);
            session.setAttribute("userRole", "Student");
            logger.info("You was logged as " + currentStudent);
            resp.sendRedirect("main");
            return;
        }
        logger.info("Student was not found");
        TeacherService teacherService = new TeacherServiceImpl();
        Teacher currentTeacher = teacherService.findTeacher(login, password);
        if (currentTeacher != null) {
            session.setAttribute("currentUser", currentTeacher);
            session.setAttribute("userRole", "Teacher");
            logger.info("You was logged as " + currentTeacher);
            resp.sendRedirect("main");
        } else {
            logger.info("Teacher was not found");
            AdministartorService administartorService = new AdministratorServiceImpl();
            Administrator currentAdmin = administartorService.findAdministrator(login, password);
            if (currentAdmin != null) {
                session.setAttribute("currentUser", currentAdmin);
                session.setAttribute("userRole", "Admin");
                logger.info("You was logged as " + currentAdmin);
                resp.sendRedirect("main");
            } else {
                session.setAttribute("errorMessage",
                        "Unable to login. Either Login or Password is incorrect");
                resp.sendRedirect("login");
            }
        }

    }
}