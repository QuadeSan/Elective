package Controller;

import DataBaseLayer.QueryResult;
import DataBaseLayer.entity.Course;
import DataBaseLayer.entity.Student;
import Services.AssignmentService;
import Services.CourseService;
import ServicesImpl.AssignmentServiceImpl;
import ServicesImpl.CourseServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/courses")
public class CoursesPageServlet extends HttpServlet {

    private static Logger logger = LogManager.getLogger(CoursesPageServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("doGet of /courses with forward to courses.jsp");
        if (req.getParameter("course_id") != null) {
            int courseID = Integer.parseInt(req.getParameter("course_id"));
            logger.debug("Trying to assign to course " + courseID);
            HttpSession session = req.getSession();
            Student currentStudent = (Student) session.getAttribute("currentUser");
            int studentID = currentStudent.getStudentID();
            AssignmentService assignmentService = new AssignmentServiceImpl();
            logger.debug("AssignmentService was created");
            QueryResult result = assignmentService.assignStudentToCourse(courseID, studentID);
            if (result.getResult()) {
                session.setAttribute("infoMessage", "You was signed for course #" + courseID);
            } else {
                session.setAttribute("errorMessage", result.getException());
            }
            resp.sendRedirect("courses");
            return;
        }
        CourseService courseService = new CourseServiceImpl();
        logger.debug("CourseService was created");
        List<Course> courses = courseService.showAllCourses();
        req.setAttribute("Courses", courses);
        logger.debug("List of all courses " + courses.hashCode());
        req.getRequestDispatcher("courses.jsp").forward(req, resp);

    }
}
