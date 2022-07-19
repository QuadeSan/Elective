package presentation.controller;

import application.OperationResult;
import application.ValuedOperationResult;
import application.entity.Administrator;
import application.entity.Course;
import application.entity.Student;
import application.entity.Teacher;
import application.services.AdministratorService;
import application.services.AssignmentService;
import application.services.StudentService;
import application.services.TeacherService;
import application.services.impl.AdministratorServiceImpl;
import application.services.impl.AssignmentServiceImpl;
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

/**
 * Servlet for personal account page
 * {@link #doGet(HttpServletRequest, HttpServletResponse) Get} method check
 * is user logged and if not, it redirects user to main page showing error message
 * if user logged method check current role to use appropriate service to show
 * all courses for current user. Also, method handle drop course request from users
 * with Student role
 * {@link #doPost(HttpServletRequest, HttpServletResponse) Post} method handle
 * account delete request from {@link AccountEditPageServlet} servlet
 */
@WebServlet("/account")
public class AccountPageServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(AccountPageServlet.class);
    private AssignmentService assignmentService;
    private AdministratorService administratorService;
    private StudentService studentService;
    private TeacherService teacherService;

    @Override
    public void init() throws ServletException {
        assignmentService = new AssignmentServiceImpl();
        logger.debug("AssignmentService was created");
        administratorService = new AdministratorServiceImpl();
        logger.debug("AdministratorService was created");
        studentService = new StudentServiceImpl();
        logger.debug("StudentService was created");
        teacherService = new TeacherServiceImpl();
        logger.debug("TeacherService was created");
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

        showCoursesForRole(req, resp, userRole);

        if (req.getParameter("course_id") != null) {
            dropCourse(req, resp);
            return;
        }
        req.getRequestDispatcher("account.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("doPost of /account with forward to account.jsp");
        if (req.getParameter("account-action").equals("edit")) {
            editAccount(req, resp);
            return;
        }
        if (req.getParameter("account-action").equals("delete")) {
            deleteAccount(req, resp);
            return;
        }
        resp.sendRedirect("account");
    }

    private void editAccount(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        int userId = Integer.parseInt(req.getParameter("user-id"));
        String newLogin = req.getParameter("login");
        String newEmail = req.getParameter("email");
        String newPassword = req.getParameter("psw");
        String newPasswordRep = req.getParameter("psw-repeat");
        String newName = req.getParameter("name");
        String newLastName = req.getParameter("last-name");
        if (!newPassword.equals(newPasswordRep)) {
            session.setAttribute("errorMessage", "Passwords does not match");
            resp.sendRedirect("editaccount");
            return;
        }
        String role = (String) session.getAttribute("userRole");
        switch (role) {
            case "Student":
                ValuedOperationResult<Student> studentOperationResult = studentService.editAccount(userId, newLogin, newEmail, newPassword, newName, newLastName);
                if (!studentOperationResult.isSuccess()) {
                    session.setAttribute("errorMessage", studentOperationResult.getMessage());
                    resp.sendRedirect("editaccount");
                    return;
                }
                session.setAttribute("currentUser", studentOperationResult.getResult());
                break;
            case "Teacher":
                ValuedOperationResult<Teacher> teacherOperationResult = teacherService.editAccount(userId, newLogin, newEmail, newPassword, newName, newLastName);
                if (!teacherOperationResult.isSuccess()) {
                    session.setAttribute("errorMessage", teacherOperationResult.getMessage());
                    resp.sendRedirect("editaccount");
                    return;
                }
                session.setAttribute("currentUser", teacherOperationResult.getResult());
                break;
            case "Admin":
                ValuedOperationResult<Administrator> adminOperationResult = administratorService.editAccount(userId, newLogin, newEmail, newPassword, newName, newLastName);
                if (!adminOperationResult.isSuccess()) {
                    session.setAttribute("errorMessage", adminOperationResult.getMessage());
                    resp.sendRedirect("editaccount");
                    return;
                }
                session.setAttribute("currentUser", adminOperationResult.getResult());
        }
        resp.sendRedirect("account");
    }

    private void showCoursesForRole(HttpServletRequest req, HttpServletResponse resp, String userRole) throws IOException {
        HttpSession session = req.getSession();
        ValuedOperationResult<Iterable<Course>> operationResult;
        switch (userRole) {
            case "Student":
                Student currentStudent = (Student) session.getAttribute("currentUser");
                if (currentStudent.getStatus().equals("locked")) {
                    session.setAttribute("errorMessage", "Your account is locked, " +
                            "contact administrator for more information");
                    resp.sendRedirect("error");
                    return;
                }
                int studentID = currentStudent.getStudentID();

                operationResult = assignmentService.showStudentCourses(studentID);
                currentStudent.setCourses(operationResult.getResult());
                break;
            case "Teacher":
                Teacher currentTeacher = (Teacher) session.getAttribute("currentUser");
                int teacherID = currentTeacher.getTeacherID();

                operationResult = assignmentService.showTeacherCourses(teacherID);
                currentTeacher.setCourses(operationResult.getResult());
                break;
        }
    }

    private void dropCourse(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        int courseID = Integer.parseInt(req.getParameter("course_id"));
        logger.debug("Trying to cancel assignment from course " + courseID);
        Student currentStudent = (Student) session.getAttribute("currentUser");
        int studentID = currentStudent.getStudentID();

        OperationResult OperationResult = assignmentService.leaveCourse(courseID, studentID);
        if (OperationResult.isSuccess()) {
            session.setAttribute("infoMessage", "You left the course # " + courseID);
            resp.sendRedirect("account");
        } else {
            session.setAttribute("errorMessage", OperationResult.getMessage());
            resp.sendRedirect("error");
        }
    }

    private void deleteAccount(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.debug("Trying to delete account");
        int userId = Integer.parseInt(req.getParameter("user-id"));

        OperationResult operationResult = administratorService.deleteAccount(userId);

        HttpSession session = req.getSession();
        if (!operationResult.isSuccess()) {
            session.setAttribute("errorMessage", operationResult.getMessage());
            resp.sendRedirect("error");
            return;
        }
        logger.info("Account was deleted");
        session.setAttribute("infoMessage", "Your account was deleted");
        resp.sendRedirect("logout");
    }
}
