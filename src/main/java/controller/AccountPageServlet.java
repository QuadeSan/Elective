package controller;

import application.entity.Course;
import application.entity.Student;
import application.entity.Teacher;
import application.services.AssignmentService;
import application.services.impl.AssignmentServiceImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/account")
public class AccountPageServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(AccountPageServlet.class);
    private AssignmentService assignmentService;

    @Override
    public void init() throws ServletException {
        assignmentService = new AssignmentServiceImpl();
        logger.debug("AssignmentService was created");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("doGet of /account with forward to account.jsp");
        HttpSession session = req.getSession();
        String userRole = (String) session.getAttribute("userRole");
        if (userRole == null || userRole.equals("guest")) {
            logger.debug("You are not logged");
            req.setAttribute("errorMessage", "You are not logged");
            req.getRequestDispatcher("error.jsp").forward(req, resp);
            return;
        }
        if (userRole.equals("Student")) {
            Student currentStudent = (Student) session.getAttribute("currentUser");
            int studentID = currentStudent.getStudentID();

            Iterable<Course> courseList = assignmentService.showStudentCourses(studentID);
            currentStudent.setCourses(courseList);
        }
        if (userRole.equals("Teacher")) {
            Teacher currentTeacher = (Teacher) session.getAttribute("currentUser");
            int teacherID = currentTeacher.getTeacherID();

            Iterable<Course> courseList = assignmentService.showTeacherCourses(teacherID);
            currentTeacher.setCourses(courseList);
        }
        if (req.getParameter("course_id") != null) {
            int courseID = Integer.parseInt(req.getParameter("course_id"));
            logger.debug("Trying to cancel assignment from course " + courseID);
            Student currentStudent = (Student) session.getAttribute("currentUser");
            int studentID = currentStudent.getStudentID();

            assignmentService.unassignStudentFromCourse(courseID, studentID);
            logger.info("Student " + studentID + " left the course # " + courseID);
            session.setAttribute("infoMessage", "You left the course #" + courseID);
            resp.sendRedirect("account");
            return;
        }
        req.getRequestDispatcher("account.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("doPost of /account with forward to account.jsp");
        HttpSession session = req.getSession();
        String password = req.getParameter("psw");
        String reppassword = req.getParameter("psw-repeat");
        String login = req.getParameter("login");
        String email = req.getParameter("email");
        String name = req.getParameter("firstname");
        String lastName = req.getParameter("lastname");
        Map<String, String> parameters = getParameters(req, resp);
        if (!password.equals(reppassword)) {
            logger.info("Passwords don't match");
            session.setAttribute("errorMessage", "Passwords don't match");
            resp.sendRedirect("editaccount");
            return;
        } else {
            session.setAttribute("infoMessage", "Information was changed");
        }
        resp.sendRedirect("account");
    }

    Map<String, String> getParameters(HttpServletRequest req, HttpServletResponse resp) {
        Map<String, String> result = new HashMap<>();
        HttpSession session = req.getSession();
        String login = req.getParameter("login");
        result.put("login", login);
        String email = req.getParameter("email");
        result.put("email", email);
        String name = req.getParameter("firstname");
        result.put("name", name);
        String lastName = req.getParameter("lastname");
        result.put("lastName", lastName);
        String password = req.getParameter("psw");
        result.put("password", password);
        String reppassword = req.getParameter("psw-repeat");
        result.put("reppassword", reppassword);
        return result;
    }

    boolean checkPasswords(Map<String, String> parameters) {

        return parameters.get("password").equals(parameters.get("reppassword"));
    }
}
