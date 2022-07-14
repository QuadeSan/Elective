package presentation.controller;

import application.OperationResult;
import application.ValuedOperationResult;
import application.entity.Student;
import application.services.StudentService;
import application.services.impl.StudentServiceImpl;
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
 * Servlet for students admin tools
 * {@link #doGet(HttpServletRequest, HttpServletResponse) Get} method
 * used to show view-only list of all students
 * {@link #doPost(HttpServletRequest, HttpServletResponse) Post} method
 * used to handle lock/unlock student request from {@link AdminToolsStudentsPageServlet}
 */
@WebServlet("/toolsstudents")
public class AdminToolsStudentsPageServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(AdminToolsStudentsPageServlet.class);
    private StudentService studentService;

    @Override
    public void init() throws ServletException {
        studentService = new StudentServiceImpl();
        logger.debug("StudentService was created");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("doGet of /toolsstudents with forward to toolsStudents.jsp");

        int currentPage = 1;
        int limit = 5;
        if (req.getParameter("current-page") != null) {
            currentPage = Integer.parseInt(req.getParameter("current-page"));
        }
        int offSet = (currentPage - 1) * limit;

        HttpSession session = req.getSession();
        session.setAttribute("currentPage", currentPage);

        ValuedOperationResult<Integer> valuedOperationResult = studentService.studentCount();
        if (!valuedOperationResult.isSuccess()) {
            session.setAttribute("errorMessage", valuedOperationResult.getMessage());
            resp.sendRedirect("error");
            return;
        }
        int pageCount = (int) Math.ceil(valuedOperationResult.getResult() * 1.0 / limit);

        session.setAttribute("pageCount", pageCount);

        ValuedOperationResult<Iterable<Student>> operationResult = studentService.showAllStudents(offSet, limit);
        if (operationResult.isSuccess()) {
            logger.info("List of students " + operationResult.hashCode());
            session.setAttribute("allStudentsList", operationResult.getResult());
            req.getRequestDispatcher("toolsStudents.jsp").forward(req, resp);
        } else {
            session.setAttribute("errorMessage", operationResult.getMessage());
            resp.sendRedirect("error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("doPost of /toolsstudents with redirect to toolsStudents.jsp");

        int studentID = Integer.parseInt(req.getParameter("student_id"));
        String status = req.getParameter("status");

        HttpSession session = req.getSession();
        int currentPage = (int) session.getAttribute("currentPage");

        OperationResult operationResult = studentService.lockStudent(studentID, status);
        if (operationResult.isSuccess()) {
            resp.sendRedirect("toolsstudents?current-page=" + currentPage);
            return;
        }
        session.setAttribute("errorMessage", operationResult.getMessage());
        resp.sendRedirect("error");
    }
}
