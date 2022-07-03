package application.services.impl;

import application.OperationResult;
import application.ValuedOperationResult;
import application.dao.*;
import application.entity.Course;
import application.entity.Student;
import data.dao.impl.MySQLAssignmentDAO;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class AssignmentServiceImplTest {

    @Test
    public void successfulOperationResultWhenTryingToChangeAssignmentForCourseWithExistedTeacherId() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        AssignmentDAO assignmentDAOMock = mock(MySQLAssignmentDAO.class);
        AssignmentServiceImpl assignmentService = new AssignmentServiceImpl();

        doReturn(assignmentDAOMock).when(daoFactoryMock).getAssignmentDAO();
        doNothing().when(assignmentDAOMock).changeTeacherAssignment(1, 1);

        OperationResult expected = new OperationResult(true, "New teacher was assigned to course");
        OperationResult actual = assignmentService.changeTeacherAssignment(1, 1);

        assertEquals(expected, actual);
    }

    @Test
    public void failedOperationResultWhenTryingToChangeAssignmentForCourseWithNonExistedTeacherId() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        AssignmentDAO assignmentDAOMock = mock(MySQLAssignmentDAO.class);
        AssignmentServiceImpl assignmentService = new AssignmentServiceImpl();

        doReturn(assignmentDAOMock).when(daoFactoryMock).getAssignmentDAO();
        doThrow(NotExistException.class).when(assignmentDAOMock).changeTeacherAssignment(1, -1);

        OperationResult expected = new OperationResult(false, "Teacher with ID = -1 does not exist");
        OperationResult actual = assignmentService.changeTeacherAssignment(1, -1);

        assertEquals(expected, actual);
    }

    @Test
    public void failedOperationResultWhenTryingToChangeTeacherAssignmentAndDAOExceptionThrown() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        AssignmentDAO assignmentDAOMock = mock(MySQLAssignmentDAO.class);
        AssignmentServiceImpl assignmentService = new AssignmentServiceImpl();

        doReturn(assignmentDAOMock).when(daoFactoryMock).getAssignmentDAO();
        doThrow(DAOException.class).when(assignmentDAOMock).changeTeacherAssignment(any(Integer.class), any(Integer.class));

        OperationResult expected = new OperationResult(false, "Unhandled exception");
        OperationResult actual = assignmentService.changeTeacherAssignment(1, 1);

        assertEquals(expected, actual);
    }

    @Test
    public void successfulOperationResultWhenTryingToSignUpForCourseAsAStudent() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        AssignmentDAO assignmentDAOMock = mock(MySQLAssignmentDAO.class);
        AssignmentServiceImpl assignmentService = new AssignmentServiceImpl();

        doReturn(assignmentDAOMock).when(daoFactoryMock).getAssignmentDAO();
        doNothing().when(assignmentDAOMock).assignStudentToCourse(1, 1);

        OperationResult expected = new OperationResult(true, "You joined course # 1");
        OperationResult actual = assignmentService.assignStudentToCourse(1, 1);

        assertEquals(expected, actual);
    }

    @Test
    public void failedOperationResultWhenTryingToSignUpForCourseAsAStudentSecondTime() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        AssignmentDAO assignmentDAOMock = mock(MySQLAssignmentDAO.class);
        AssignmentServiceImpl assignmentService = new AssignmentServiceImpl();

        doReturn(assignmentDAOMock).when(daoFactoryMock).getAssignmentDAO();
        doThrow(AlreadyExistException.class).when(assignmentDAOMock).assignStudentToCourse(1, 1);

        OperationResult expected = new OperationResult(false, "You are already enrolled in the course");
        OperationResult actual = assignmentService.assignStudentToCourse(1, 1);

        assertEquals(expected, actual);
    }

    @Test
    public void failedOperationResultWhenTryingToSignUpForCourseAsAStudentAndDAOExceptionThrown() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        AssignmentDAO assignmentDAOMock = mock(MySQLAssignmentDAO.class);
        AssignmentServiceImpl assignmentService = new AssignmentServiceImpl();

        doReturn(assignmentDAOMock).when(daoFactoryMock).getAssignmentDAO();
        doThrow(DAOException.class).when(assignmentDAOMock).assignStudentToCourse(any(Integer.class), any(Integer.class));

        OperationResult expected = new OperationResult(false, "Unhandled exception");
        OperationResult actual = assignmentService.assignStudentToCourse(1, 1);

        assertEquals(expected, actual);
    }

    @Test
    public void successfulOperationResultWhenTryingToLeaveCourseAsAStudent() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        AssignmentDAO assignmentDAOMock = mock(MySQLAssignmentDAO.class);
        AssignmentServiceImpl assignmentService = new AssignmentServiceImpl();

        doReturn(assignmentDAOMock).when(daoFactoryMock).getAssignmentDAO();
        doNothing().when(assignmentDAOMock).unassignStudentFromCourse(1, 1);

        OperationResult expected = new OperationResult(true, "You left the course");
        OperationResult actual = assignmentService.leaveCourse(1, 1);

        assertEquals(expected, actual);
    }

    @Test
    public void failedOperationResultWhenTryingToLeaveCourseAsAStudentAndDAOExceptionThrown() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        AssignmentDAO assignmentDAOMock = mock(MySQLAssignmentDAO.class);
        AssignmentServiceImpl assignmentService = new AssignmentServiceImpl();

        doReturn(assignmentDAOMock).when(daoFactoryMock).getAssignmentDAO();
        doThrow(DAOException.class).when(assignmentDAOMock).unassignStudentFromCourse(any(Integer.class), any(Integer.class));

        OperationResult expected = new OperationResult(false, "Unhandled exception");
        OperationResult actual = assignmentService.leaveCourse(1, 1);

        assertEquals(expected, actual);
    }

    @Test
    public void successfulOperationResultWhenTryingToObtainAllTeachersCourses() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        AssignmentDAO assignmentDAOMock = mock(MySQLAssignmentDAO.class);
        Iterable<Course> iterableMock = mock(Iterable.class);
        AssignmentServiceImpl assignmentService = new AssignmentServiceImpl();

        doReturn(assignmentDAOMock).when(daoFactoryMock).getAssignmentDAO();
        doReturn(iterableMock).when(assignmentDAOMock).showTeacherCourses(1);

        ValuedOperationResult<Iterable<Course>> expected = new ValuedOperationResult<>(true, "List of courses of teacher 1", iterableMock);
        ValuedOperationResult<Iterable<Course>> actual = assignmentService.showTeacherCourses(1);

        assertEquals(expected, actual);
    }

    @Test
    public void failedOperationResultWhenTryingToObtainAllTeachersCoursesAndDAOExceptionThrown() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        AssignmentDAO assignmentDAOMock = mock(MySQLAssignmentDAO.class);
        AssignmentServiceImpl assignmentService = new AssignmentServiceImpl();

        doReturn(assignmentDAOMock).when(daoFactoryMock).getAssignmentDAO();
        doThrow(DAOException.class).when(assignmentDAOMock).showTeacherCourses(1);

        ValuedOperationResult<Iterable<Course>> expected = new ValuedOperationResult<>(false, "Unhandled exception", null);
        ValuedOperationResult<Iterable<Course>> actual = assignmentService.showTeacherCourses(1);

        assertEquals(expected, actual);
    }

    @Test
    public void successfulOperationResultWhenTryingToObtainAllStudentsCourses() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        AssignmentDAO assignmentDAOMock = mock(MySQLAssignmentDAO.class);
        Iterable<Course> iterableMock = mock(Iterable.class);
        AssignmentServiceImpl assignmentService = new AssignmentServiceImpl();

        doReturn(assignmentDAOMock).when(daoFactoryMock).getAssignmentDAO();
        doReturn(iterableMock).when(assignmentDAOMock).showStudentCourses(1);

        ValuedOperationResult<Iterable<Course>> expected = new ValuedOperationResult<>(true, "List of courses of student 1", iterableMock);
        ValuedOperationResult<Iterable<Course>> actual = assignmentService.showStudentCourses(1);

        assertEquals(expected, actual);
    }

    @Test
    public void failedOperationResultWhenTryingToObtainAllStudentsCoursesAndDAOExceptionThrown() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        AssignmentDAO assignmentDAOMock = mock(MySQLAssignmentDAO.class);
        AssignmentServiceImpl assignmentService = new AssignmentServiceImpl();

        doReturn(assignmentDAOMock).when(daoFactoryMock).getAssignmentDAO();
        doThrow(DAOException.class).when(assignmentDAOMock).showStudentCourses(1);

        ValuedOperationResult<Iterable<Course>> expected = new ValuedOperationResult<>(false, "Unhandled exception", null);
        ValuedOperationResult<Iterable<Course>> actual = assignmentService.showStudentCourses(1);

        assertEquals(expected, actual);
    }

    @Test
    public void successfulOperationResultWhenTryingToSeeJournalOfCourse() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        AssignmentDAO assignmentDAOMock = mock(MySQLAssignmentDAO.class);
        Iterable<Student> iterableMock = mock(Iterable.class);
        AssignmentServiceImpl assignmentService = new AssignmentServiceImpl();

        doReturn(assignmentDAOMock).when(daoFactoryMock).getAssignmentDAO();
        doReturn(iterableMock).when(assignmentDAOMock).showStudentsOnCourse(1);

        ValuedOperationResult<Iterable<Student>> expected = new ValuedOperationResult<>(true, "List of students on course # 1", iterableMock);
        ValuedOperationResult<Iterable<Student>> actual = assignmentService.showJournal(1);

        assertEquals(expected, actual);
    }

    @Test
    public void failedOperationResultWhenTryingToSeeJournalOfCourseAndDAOExceptionThrown() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        AssignmentDAO assignmentDAOMock = mock(MySQLAssignmentDAO.class);
        AssignmentServiceImpl assignmentService = new AssignmentServiceImpl();

        doReturn(assignmentDAOMock).when(daoFactoryMock).getAssignmentDAO();
        doThrow(DAOException.class).when(assignmentDAOMock).showStudentsOnCourse(any(Integer.class));

        ValuedOperationResult<Iterable<Student>> expected = new ValuedOperationResult<>(false, "Unhandled exception", null);
        ValuedOperationResult<Iterable<Student>> actual = assignmentService.showJournal(1);

        assertEquals(expected, actual);
    }

    @Test
    public void successfulOperationResultWhenChangingMarkForStudent() {
       DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        AssignmentDAO assignmentDAOMock = mock(MySQLAssignmentDAO.class);
        AssignmentServiceImpl assignmentService = new AssignmentServiceImpl();

        doReturn(assignmentDAOMock).when(daoFactoryMock).getAssignmentDAO();
        doNothing().when(assignmentDAOMock).setMarkForStudent(1, 1, 100);

        OperationResult expected = new OperationResult(true,
                "Student # 1 got mark 100 for course # 1");
        OperationResult actual = assignmentService.setMarkForStudent(1, 1, 100);

        assertEquals(expected, actual);
    }

    @Test
    public void failedOperationResultWhenChangingMarkForStudentAndDAOExceptionThrown() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        AssignmentDAO assignmentDAOMock = mock(MySQLAssignmentDAO.class);
        AssignmentServiceImpl assignmentService = new AssignmentServiceImpl();

        doReturn(assignmentDAOMock).when(daoFactoryMock).getAssignmentDAO();
        doThrow(DAOException.class).when(assignmentDAOMock).setMarkForStudent(any(Integer.class), any(Integer.class), any(Integer.class));

        OperationResult expected = new OperationResult(false, "Unhandled exception");
        OperationResult actual = assignmentService.setMarkForStudent(1, 1, 100);

        assertEquals(expected, actual);
    }

    @Test
    public void failedOperationResultWhenServiceCanNotCloseDAOInChangeTeacherAssignmentMethod() throws Exception {
       DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        AssignmentDAO assignmentDAOMock = mock(MySQLAssignmentDAO.class);
        AssignmentServiceImpl assignmentService = new AssignmentServiceImpl();

        doReturn(assignmentDAOMock).when(daoFactoryMock).getAssignmentDAO();
        doThrow(Exception.class).when(assignmentDAOMock).close();

        OperationResult expected = new OperationResult(false, "Unhandled exception");
        OperationResult actual = assignmentService.changeTeacherAssignment(1, 1);

        assertEquals(expected, actual);
    }

    @Test
    public void failedOperationResultWhenServiceCanNotCloseDAOInAssignStudentToCourseMethod() throws Exception {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        AssignmentDAO assignmentDAOMock = mock(MySQLAssignmentDAO.class);
        AssignmentServiceImpl assignmentService = new AssignmentServiceImpl();

        doReturn(assignmentDAOMock).when(daoFactoryMock).getAssignmentDAO();
        doThrow(Exception.class).when(assignmentDAOMock).close();

        OperationResult expected = new OperationResult(false, "Unhandled exception");
        OperationResult actual = assignmentService.assignStudentToCourse(1, 1);

        assertEquals(expected, actual);
    }

    @Test
    public void failedOperationResultWhenServiceCanNotCloseDAOInUnAssignStudentFromCourseMethod() throws Exception {
       DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        AssignmentDAO assignmentDAOMock = mock(MySQLAssignmentDAO.class);
        AssignmentServiceImpl assignmentService = new AssignmentServiceImpl();

        doReturn(assignmentDAOMock).when(daoFactoryMock).getAssignmentDAO();
        doThrow(Exception.class).when(assignmentDAOMock).close();

        OperationResult expected = new OperationResult(false, "Unhandled exception");
        OperationResult actual = assignmentService.leaveCourse(1, 1);

        assertEquals(expected, actual);
    }

    @Test
    public void failedOperationResultWhenServiceCanNotCloseDAOInShowTeacherCoursesMethod() throws Exception {
       DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        AssignmentDAO assignmentDAOMock = mock(MySQLAssignmentDAO.class);
        AssignmentServiceImpl assignmentService = new AssignmentServiceImpl();

        doReturn(assignmentDAOMock).when(daoFactoryMock).getAssignmentDAO();
        doThrow(Exception.class).when(assignmentDAOMock).close();

        ValuedOperationResult<Iterable<Course>> expected = new ValuedOperationResult<>(false, "Unhandled exception", null);
        ValuedOperationResult<Iterable<Course>> actual = assignmentService.showTeacherCourses(1);

        assertEquals(expected, actual);
    }

    @Test
    public void failedOperationResultWhenServiceCanNotCloseDAOInShowStudentCoursesMethod() throws Exception {
      DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        AssignmentDAO assignmentDAOMock = mock(MySQLAssignmentDAO.class);
        AssignmentServiceImpl assignmentService = new AssignmentServiceImpl();

        doReturn(assignmentDAOMock).when(daoFactoryMock).getAssignmentDAO();
        doThrow(Exception.class).when(assignmentDAOMock).close();

        ValuedOperationResult<Iterable<Course>> expected = new ValuedOperationResult<>(false, "Unhandled exception", null);
        ValuedOperationResult<Iterable<Course>> actual = assignmentService.showStudentCourses(1);

        assertEquals(expected, actual);
    }

    @Test
    public void failedOperationResultWhenServiceCanNotCloseDAOInShowJournalMethod() throws Exception {
       DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        AssignmentDAO assignmentDAOMock = mock(MySQLAssignmentDAO.class);
        AssignmentServiceImpl assignmentService = new AssignmentServiceImpl();

        doReturn(assignmentDAOMock).when(daoFactoryMock).getAssignmentDAO();
        doThrow(Exception.class).when(assignmentDAOMock).close();

        ValuedOperationResult<Iterable<Student>> expected = new ValuedOperationResult<>(false, "Unhandled exception", null);
        ValuedOperationResult<Iterable<Student>> actual = assignmentService.showJournal(1);

        assertEquals(expected, actual);
    }

    @Test
    public void failedOperationResultWhenServiceCanNotCloseDAOInSetMarkForStudentMethod() throws Exception {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        AssignmentDAO assignmentDAOMock = mock(MySQLAssignmentDAO.class);
        AssignmentServiceImpl assignmentService = new AssignmentServiceImpl();

        doReturn(assignmentDAOMock).when(daoFactoryMock).getAssignmentDAO();
        doThrow(Exception.class).when(assignmentDAOMock).close();

        OperationResult expected = new OperationResult(false, "Unhandled exception");
        OperationResult actual = assignmentService.setMarkForStudent(1, 1, 100);

        assertEquals(expected, actual);
    }
}