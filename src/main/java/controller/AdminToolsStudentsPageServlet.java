package controller;

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
        logger.debug("doGet of /toolsstudents with forward to toolsstudents.jsp");
        HttpSession session = req.getSession();
        ValuedOperationResult<Iterable<Student>> operationResult = studentService.showAllStudents();
        if (operationResult.isSuccess()) {
            logger.info("List of students " + operationResult.hashCode());
            session.setAttribute("allStudentsList", operationResult.getResult());
            req.getRequestDispatcher("toolsstudents.jsp").forward(req, resp);
        } else {
            session.setAttribute("errorMessage", operationResult.getMessage());
            resp.sendRedirect("error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("doPost of /toolsstudents with redirect to toolsstudents.jsp");

        int studentID = Integer.parseInt(req.getParameter("student_id"));
        String status = req.getParameter("status");

        HttpSession session = req.getSession();

        OperationResult operationResult = studentService.lockStudent(studentID, status);
        if (operationResult.isSuccess()) {
            resp.sendRedirect("toolsstudents");
            return;
        }
        session.setAttribute("errorMessage", operationResult.getMessage());
        resp.sendRedirect("error");
    }
}
