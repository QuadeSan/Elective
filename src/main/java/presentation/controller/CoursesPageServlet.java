package presentation.controller;

import application.ValuedOperationResult;
import application.entity.Course;
import application.entity.Student;
import application.services.AssignmentService;
import application.services.CourseService;
import application.OperationResult;
import application.services.impl.AssignmentServiceImpl;
import application.services.impl.CourseServiceImpl;
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
 * Servlet for all courses page
 * It shows all courses with their current status
 * for all users no matter for their role
 * {@link #doPost(HttpServletRequest, HttpServletResponse) Post} method
 * handle sign for course request for all students
 */
@WebServlet("/courses")
public class CoursesPageServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(CoursesPageServlet.class);
    private AssignmentService assignmentService;
    private CourseService courseService;

    @Override
    public void init() throws ServletException {
        assignmentService = new AssignmentServiceImpl();
        logger.debug("AssignmentService was created");
        courseService = new CourseServiceImpl();
        logger.debug("CourseService was created");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("doGet of /courses with forward to courses.jsp");

        HttpSession session = req.getSession();
        if (req.getParameter("course_id") != null) {
            int courseID = Integer.parseInt(req.getParameter("course_id"));
            logger.debug("Trying to assign to course " + courseID);

            Student currentStudent = (Student) session.getAttribute("currentUser");
            int studentID = currentStudent.getStudentID();

            OperationResult operationResult = assignmentService.assignStudentToCourse(courseID, studentID);
            if (operationResult.isSuccess()) {
                session.setAttribute("infoMessage", operationResult.getMessage());
            } else {
                session.setAttribute("errorMessage", operationResult.getMessage());
            }
            resp.sendRedirect("courses");
            return;
        }
        ValuedOperationResult<Iterable<Course>> operationResult = courseService.showAllCourses();
        if (operationResult.isSuccess()) {
            req.setAttribute("Courses", operationResult.getResult());
            logger.debug("List of all courses revealed");
            req.getRequestDispatcher("courses.jsp").forward(req, resp);
        } else {
            session.setAttribute("errorMessage", operationResult.getMessage());
            resp.sendRedirect("error");
        }
    }
}
