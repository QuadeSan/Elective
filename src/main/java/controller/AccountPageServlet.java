package controller;

import application.OperationResult;
import application.ValuedOperationResult;
import application.entity.Course;
import application.entity.Student;
import application.entity.Teacher;
import application.services.AdministratorService;
import application.services.AssignmentService;
import application.services.impl.AdministratorServiceImpl;
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
    private AdministratorService administratorService;

    @Override
    public void init() throws ServletException {
        assignmentService = new AssignmentServiceImpl();
        logger.debug("AssignmentService was created");
        administratorService = new AdministratorServiceImpl();
        logger.debug("AdministratorService was created");
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
        if (req.getParameter("course_id") != null) {
            int courseID = Integer.parseInt(req.getParameter("course_id"));
            logger.debug("Trying to cancel assignment from course " + courseID);
            Student currentStudent = (Student) session.getAttribute("currentUser");
            int studentID = currentStudent.getStudentID();

            OperationResult OperationResultU = assignmentService.unassignStudentFromCourse(courseID, studentID);
            if (OperationResultU.isSuccess()) {
                session.setAttribute("infoMessage", "You left the course # " + courseID);
                resp.sendRedirect("account");
                return;
            } else {
                session.setAttribute("errorMessage", OperationResultU.getMessage());
                resp.sendRedirect("error");
                return;
            }
        }
        req.getRequestDispatcher("account.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("doPost of /account with forward to account.jsp");
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
