package Controller;

import DataBaseLayer.QueryResult;
import DataBaseLayer.entity.Course;
import Services.AssignmentService;
import Services.CourseService;
import Services.StudentService;
import Services.TeacherService;
import ServicesImpl.AssignmentServiceImpl;
import ServicesImpl.CourseServiceImpl;
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

@WebServlet("/admin")
public class AdminPageServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(AdminPageServlet.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("doGet of /admin with forward to admin.jsp");
        req.getRequestDispatcher("admin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("doPost of /admin with redirect to /admin");
        if (req.getParameter("login") != null) {
            logger.debug("Creating teacher process started");
            createNewTeacher(req, resp);
        } else {
            if (req.getParameter("course-title") != null) {
                logger.debug("Creating course process started");
                createNewCourse(req, resp);
            } else {
                if (req.getParameter("course-id") != null && req.getParameter("teacher-id") != null) {
                    logger.debug("Teacher assignment process started");
                    assignTeacherToCourse(req, resp);
                } else {
                    if (req.getParameter("student-status") != null) {
                        logger.debug("Locking / unlocking student");
                        lockStudent(req, resp);
                    } else if (req.getParameter("course-id") != null) {
                        logger.debug("Deleting course");
                        deleteCourse(req, resp);
                    }
                }
            }
        }
    }

    private void createNewTeacher(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        String teacherLogin = req.getParameter("login");
        String teacherEmail = req.getParameter("email");
        String teacherName = req.getParameter("name");
        String teacherLastName = req.getParameter("lastName");
        String teacherPass = req.getParameter("psw");
        String teacherRepPass = req.getParameter("psw-repeat");
        TeacherService teacherService = new TeacherServiceImpl();
        logger.debug("TeacherService was created");
        if (!teacherPass.equals(teacherRepPass)) {
            logger.info("Teacher's passwords don't match");
            session.setAttribute("errorMessage", "Passwords don't match");
            resp.sendRedirect("admin");
            return;
        }
        QueryResult queryResult = teacherService.createTeacher(teacherLogin, teacherPass, teacherEmail, teacherName, teacherLastName);
        if (queryResult.getResult()) {
            logger.info("Teacher was created");
            session.setAttribute("infoMessage", "Teacher was successfully created");
        } else {
            logger.debug("User already exist, redirect to /admin");
            session.setAttribute("errorMessage", queryResult.getException());
        }
        resp.sendRedirect("admin");
    }

    private void createNewCourse(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        String courseTitle = req.getParameter("course-title");
        CourseService courseService = new CourseServiceImpl();
        logger.debug("CourseService was created");
        if (courseService.findCourse(courseTitle) == null) {
            courseService.createCourse(courseTitle);
            logger.info("Course " + courseTitle + " was successfully created");
            session.setAttribute("infoMessage", "New course was successfully created");
        } else {
            logger.debug("Course with title " + courseTitle + " already exist");
            session.setAttribute("errorMessage", "Course with title " + courseTitle + " already exist");
        }
        resp.sendRedirect("admin");
    }

    private void assignTeacherToCourse(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        int courseID = Integer.parseInt(req.getParameter("course-id"));
        int teacherID = Integer.parseInt(req.getParameter("teacher-id"));
        AssignmentService assignmentService = new AssignmentServiceImpl();
        logger.debug("AssignmentService was created");
        QueryResult queryResult = assignmentService.assignTeacherToCourse(courseID, teacherID);
        if (queryResult.getResult()) {
            logger.info("Teacher was assigned to a course");
            session.setAttribute("infoMessage", "Teacher was assigned to a course");
        } else {
            logger.info("Teacher was not assigned to a course. Reason: " + queryResult.getException());
            logger.debug(queryResult.getException());
            session.setAttribute("errorMessage", "Something went wrong! " +
                    queryResult.getException());
        }
        resp.sendRedirect("admin");
    }

    private void lockStudent(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        int studentID = Integer.parseInt(req.getParameter("student-id"));
        String status = req.getParameter("student-status");
        StudentService studentService = new StudentServiceImpl();
        logger.debug("StudentService was created");
        if (studentService.findStudent(studentID) == null) {
            logger.info("Student does not exit");
            session.setAttribute("errorMessage", "Something went wrong! Student with ID = "
                    + studentID + " does not exist");
            resp.sendRedirect("admin");
            return;
        }
        if (!status.equalsIgnoreCase("locked") &&
                !status.equalsIgnoreCase("unlocked")) {
            logger.info("Wrong status");
            session.setAttribute("errorMessage", "Something went wrong! " +
                    "Status is \"" + status + "\" but can be \"locked\" or \"unlocked\" only!");
            resp.sendRedirect("admin");
            return;
        }
        if (status.equalsIgnoreCase("locked")) {
            logger.info("Student " + studentID + " was " + status);
            session.setAttribute("infoMessage", "Student " + studentID + " was " + status);
        }
        if (status.equalsIgnoreCase("unlocked")) {
            logger.info("Student " + studentID + " was " + status);
            session.setAttribute("infoMessage", "Student " + studentID + " was " + status);
        }
        studentService.lockStudent(studentID, status);
        resp.sendRedirect("admin");


    }

    private void deleteCourse(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        int courseID = Integer.parseInt(req.getParameter("course-id"));
        CourseService courseService = new CourseServiceImpl();
        logger.debug("CourseService was created");
        Course currentCourse = courseService.findCourse(courseID);
        logger.debug("Current course is " + currentCourse);
        if (currentCourse != null) {
            courseService.deleteCourse(courseID);
            logger.info("Course " + currentCourse.getTitle() + " was deleted");
            session.setAttribute("infoMessage", "Course " +
                    currentCourse.getTitle() + " was deleted");
        } else {
            logger.info("Course with ID " + courseID + " does not exist");
            session.setAttribute("errorMessage", "Course with ID " + courseID + " does not exist");
        }
        resp.sendRedirect("admin");
    }

}