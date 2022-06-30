package presentation.controller;

import application.ValuedOperationResult;
import application.entity.Course;
import application.entity.Teacher;
import application.services.CourseService;
import application.services.TeacherService;
import application.services.impl.CourseServiceImpl;
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

@WebServlet("/toolseditcourse")
public class AdminToolsEditCoursePageServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(AdminToolsEditCoursePageServlet.class);
    private CourseService courseService;
    private TeacherService teacherService;

    @Override
    public void init() throws ServletException {
        this.courseService = new CourseServiceImpl();
        logger.debug("CourseService was created");
        this.teacherService = new TeacherServiceImpl();
        logger.debug("TeacherService was created");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("doGet of /toolseditcourse with forward to toolseditcourse.jsp");

        HttpSession session = req.getSession();

        ValuedOperationResult<Iterable<Teacher>> operationResult = teacherService.showAllTeachers();
        if (!operationResult.isSuccess()) {
            session.setAttribute("errorMessage", operationResult.getMessage());
            resp.sendRedirect("error");
            return;
        }
        session.setAttribute("teachers", operationResult.getResult());
        req.getRequestDispatcher("toolseditcourse.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("doPost of /toolseditcourse with redirect to toolseditcourse");
        int editedCourse = Integer.parseInt(req.getParameter("course-id"));
        logger.debug("edited course = " + editedCourse);

        HttpSession session = req.getSession();

        ValuedOperationResult<Course> operationResult = courseService.findCourse(editedCourse);
        if (operationResult.isSuccess()) {
            session.setAttribute("editedCourse", operationResult.getResult());
            resp.sendRedirect("toolseditcourse");
        } else {
            session.setAttribute("errorMessage", operationResult.getMessage());
            resp.sendRedirect("error");
        }
    }
}
