package Controller;

import DataBaseLayer.entity.Course;
import DataBaseLayer.entity.Student;
import Services.AssignmentService;
import ServicesImpl.AssignmentServiceImpl;
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
import java.util.List;
import java.util.Map;

@WebServlet("/journal")
public class JournalPageServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(AccountPageServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("doGet of /journal with forward to journal.jsp");
        HttpSession session = req.getSession();
        String userRole = (String) session.getAttribute("userRole");
        if (userRole == null || userRole.equals("guest")) {
            logger.debug("You are not logged");
            req.setAttribute("errorMessage", "You are not logged");
            req.getRequestDispatcher("error.jsp").forward(req, resp);
            return;
        }
        if (req.getParameter("viewedCourse") != null) {
            int courseID = Integer.parseInt(req.getParameter("viewedCourse"));
            AssignmentService assignmentService = new AssignmentServiceImpl();
            logger.debug("AssignmentService was created");
            logger.debug("Trying to find all students on course #" + courseID);
            List<Student> studentList = assignmentService.showStudentsOnCourse(courseID);
            logger.info("List of students " + studentList.hashCode());
            session.setAttribute("studentList", studentList);
            session.setAttribute("viewedCourse", courseID);
            resp.sendRedirect("journal");
            return;
        }
        int courseID = (int) session.getAttribute("viewedCourse");
        AssignmentService assignmentService = new AssignmentServiceImpl();
        logger.debug("AssignmentService was created");
        logger.debug("Trying to find all students on course #" + courseID);
        List<Student> studentList = assignmentService.showStudentsOnCourse(courseID);
        logger.info("List of students " + studentList.hashCode());
        session.setAttribute("studentList", studentList);
        req.getRequestDispatcher("journal.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("doPost of /journal with redirect to journal");
        HttpSession session = req.getSession();
        int courseID = (int) session.getAttribute("viewedCourse");
        int studentID = Integer.parseInt(req.getParameter("student_id"));
        int mark = Integer.parseInt(req.getParameter("mark"));
        AssignmentService assignmentService = new AssignmentServiceImpl();
        logger.debug("AssignmentService was created");
        logger.debug("Going to set mark for course #" +courseID+" for student with ID " +studentID);
        assignmentService.setMarkForStudent(courseID,studentID,mark);
        resp.sendRedirect("journal");
    }
}
