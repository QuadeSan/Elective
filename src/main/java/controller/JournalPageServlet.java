package controller;

import application.entity.Student;
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
import java.util.List;

@WebServlet("/journal")
public class JournalPageServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(AccountPageServlet.class);
    AssignmentService assignmentService;

    @Override
    public void init() throws ServletException {
        assignmentService = new AssignmentServiceImpl();
        logger.debug("AssignmentService was created");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("doGet of /journal with forward to journal.jsp");
        HttpSession session = req.getSession();

        if (req.getParameter("viewedCourse") != null) {
            int courseID = Integer.parseInt(req.getParameter("viewedCourse"));
            logger.debug("Trying to find all students on course #" + courseID);

            session.setAttribute("viewedCourse", courseID);
            resp.sendRedirect("journal");
            return;
        }
        int courseID = (int) session.getAttribute("viewedCourse");
        logger.debug("Trying to find all students on course #" + courseID);

        Iterable<Student> studentList = assignmentService.showStudentsOnCourse(courseID);
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

        logger.debug("Going to set mark for course #" +courseID+" for student with ID " +studentID);
        assignmentService.setMarkForStudent(courseID,studentID,mark);
        resp.sendRedirect("journal");
    }
}
