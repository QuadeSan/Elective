package controller;

import dataBaseLayer.entity.Course;
import dataBaseLayer.entity.Student;
import services.AssignmentService;
import services.CourseService;
import services.impl.AssignmentServiceImpl;
import services.impl.CourseServiceImpl;
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

        if (req.getParameter("course_id") != null) {
            int courseID = Integer.parseInt(req.getParameter("course_id"));
            logger.debug("Trying to assign to course " + courseID);

            HttpSession session = req.getSession();

            Student currentStudent = (Student) session.getAttribute("currentUser");
            int studentID = currentStudent.getStudentID();

            Response response = assignmentService.assignStudentToCourse(courseID, studentID);
            if (response.isSuccess()) {
                session.setAttribute("infoMessage", response.getMessage());
            } else {
                session.setAttribute("errorMessage", response.getMessage());
            }
            resp.sendRedirect("courses");
            return;
        }
        List<Course> courses = courseService.showAllCourses();
        req.setAttribute("Courses", courses);
        logger.debug("List of all courses " + courses.hashCode());

        req.getRequestDispatcher("courses.jsp").forward(req, resp);
    }
}
