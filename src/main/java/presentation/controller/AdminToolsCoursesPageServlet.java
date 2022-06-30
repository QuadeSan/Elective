package presentation.controller;

import application.OperationResult;
import application.ValuedOperationResult;
import application.entity.Course;
import application.services.AssignmentService;
import application.services.CourseService;
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
 * Servlet for courses admin tools
 * {@link #doGet(HttpServletRequest, HttpServletResponse) Get} method
 * show view-only list of courses
 * {@link #doPost(HttpServletRequest, HttpServletResponse) Post} method
 * used to handle create/delete/edit course requests
 */
@WebServlet("/toolscourses")
public class AdminToolsCoursesPageServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(AdminToolsCoursesPageServlet.class);
    private CourseService courseService;
    AssignmentService assignmentService;

    @Override
    public void init() throws ServletException {
        courseService = new CourseServiceImpl();
        logger.debug("CourseService was created");
        assignmentService = new AssignmentServiceImpl();
        logger.debug("AssignmentService was created");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("doGet of /toolscourses with forward to toolscourses.jsp");
        HttpSession session = req.getSession();
        ValuedOperationResult<Iterable<Course>> operationResult = courseService.showAllCourses();
        if (operationResult.isSuccess()) {
            logger.info("List of courses " + operationResult.hashCode());
            session.setAttribute("allCoursesList", operationResult.getResult());
            req.getRequestDispatcher("toolscourses.jsp").forward(req, resp);
        } else {
            session.setAttribute("errorMessage", operationResult.getMessage());
            resp.sendRedirect("error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("doPost of /toolscourses with redirect to toolscourses.jsp");
        switch (req.getParameter("adminAction")) {
            case "createCourse":
                logger.debug("Creating course process started");
                createNewCourse(req, resp);
                break;
            case "deleteCourse":
                logger.debug("Deleting course");
                deleteCourse(req, resp);
                break;
            case "editCourse":
                logger.debug("Editing course");
                editCourse(req, resp);
                break;
        }
    }

    private void createNewCourse(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        StringBuilder errors = new StringBuilder();

        String courseTitle = req.getParameter("title");
        if (courseTitle.isBlank() || courseTitle.length() > 15) {
            errors.append("Title can't be black or longer then 15 characters").append("\n");
        }

        String courseTopic = req.getParameter("topic");
        if (courseTopic.isBlank() || courseTopic.length() > 15) {
            errors.append("Title can't be black or longer then 15 characters").append("\n");
        }

        HttpSession session = req.getSession();

        if (errors.length() > 0) {
            session.setAttribute("errorMessage", errors.toString());
            resp.sendRedirect("toolscourses");
            return;
        }

        OperationResult operationResult = courseService.createCourse(courseTopic, courseTitle);
        if (operationResult.isSuccess()) {
            session.setAttribute("infoMessage", operationResult.getMessage());
        } else {
            session.setAttribute("errorMessage", operationResult.getMessage());
        }
        resp.sendRedirect("toolscourses");
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
        resp.sendRedirect("toolscourses");
    }

    private void editCourse(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        Course editedCourse = (Course) session.getAttribute("editedCourse");
        logger.debug("edited course = " + editedCourse);
        int courseId = editedCourse.getCourseID();
        String newTopic = req.getParameter("new-topic");
        String newTitle = req.getParameter("new-title");
        String newStatus = req.getParameter("new-status");
        int newTeacherId = Integer.parseInt(req.getParameter("teacher-id"));

        if (!newTopic.equals("")) {
            OperationResult operationResult = courseService.changeTopic(courseId, newTopic);
            if (!operationResult.isSuccess()) {
                session.setAttribute("errorMessage", operationResult.getMessage());
                resp.sendRedirect("error");
                return;
            }
        }
        if (!newTitle.equals("")) {
            OperationResult operationResult = courseService.changeTitle(courseId, newTitle);
            if (!operationResult.isSuccess()) {
                session.setAttribute("errorMessage", operationResult.getMessage());
                resp.sendRedirect("error");
                return;
            }
        }
        if (!newStatus.equals("")) {
            OperationResult operationResult = courseService.changeStatus(courseId, newStatus);
            if (!operationResult.isSuccess()) {
                session.setAttribute("errorMessage", operationResult.getMessage());
                resp.sendRedirect("error");
                return;
            }
        }
        if (newTeacherId != 0) {
            OperationResult operationResult = assignmentService.changeTeacherAssignment(courseId, newTeacherId);
            if (!operationResult.isSuccess()) {
                session.setAttribute("errorMessage", operationResult.getMessage());
            }
        }
        resp.sendRedirect("toolscourses");
    }
}
