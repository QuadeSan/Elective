package application.services.impl;

import application.OperationResult;
import application.ValuedOperationResult;
import application.dao.AlreadyExistException;
import application.dao.DAOFactory;
import application.dao.NotExistException;
import application.dao.StudentDAO;
import application.entity.Student;
import data.dao.impl.MySQLStudentDAO;
import org.junit.Test;
import org.mockito.AdditionalMatchers;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class StudentServiceImplTest {


    @Test
    public void creationOfNewStudentWithNewAccountIsOK() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        StudentDAO studentDAOMock = mock(MySQLStudentDAO.class);
        StudentServiceImpl studentService = new StudentServiceImpl(daoFactoryMock);

        doReturn(studentDAOMock).when(daoFactoryMock).getStudentDAO();
        doNothing().when(studentDAOMock).createStudent(eq("newLogin"), any(), any(), any(), any());

        OperationResult expected = new OperationResult(true, "Account was successfully created!");
        OperationResult actual = studentService.createStudent("newLogin", "newPassword",
                "newEmail", "newName", "newLastName");

        assertEquals(expected, actual);
    }


    @Test
    public void creationOfNewStudentWithExistedAccountIsThrowingExceptionCausingFalseOperationResult() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        StudentDAO studentDAOMock = mock(MySQLStudentDAO.class);
        StudentServiceImpl studentService = new StudentServiceImpl(daoFactoryMock);

        doReturn(studentDAOMock).when(daoFactoryMock).getStudentDAO();
        doThrow(AlreadyExistException.class).when(studentDAOMock).createStudent(eq("existedLogin"), any(), any(), any(), any());

        OperationResult expected = new OperationResult(false, "Login already exist");
        OperationResult actual = studentService.createStudent("existedLogin", "newPassword",
                "newEmail", "newName", "newLastName");

        assertEquals(expected, actual);
    }

    @Test
    public void successOperationResultWhenTryingToLoginAsAStudentWithRightCredentials() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        StudentDAO studentDAOMock = mock(MySQLStudentDAO.class);
        StudentServiceImpl studentService = new StudentServiceImpl(daoFactoryMock);

        doReturn(studentDAOMock).when(daoFactoryMock).getStudentDAO();
        doReturn(new Student()).when(studentDAOMock).findStudent(eq("rightLogin"), eq("rightPass"));

        ValuedOperationResult<Student> expected = new ValuedOperationResult<>(true, "You logged as Student", new Student());
        ValuedOperationResult<Student> actual = studentService.findStudent("rightLogin", "rightPass");

        assertEquals(expected, actual);
    }

    @Test
    public void failedOperationResultWhenTryingToLoginAsAStudentWithWrongCredentials() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        StudentDAO studentDAOMock = mock(MySQLStudentDAO.class);
        StudentServiceImpl studentService = new StudentServiceImpl(daoFactoryMock);

        doReturn(studentDAOMock).when(daoFactoryMock).getStudentDAO();
        doThrow(NotExistException.class).when(studentDAOMock).findStudent(eq("wrongLogin"), eq("wrongPass"));


        ValuedOperationResult<Student> expected = new ValuedOperationResult<>(false, "Student with login = " +
                "wrongLogin does not exist", null);
        ValuedOperationResult<Student> actual = studentService.findStudent("wrongLogin", "wrongPass");

        assertEquals(expected, actual);
    }

    @Test
    public void successfulOperationResultWhenChangingStatusOfExistedStudent() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        StudentDAO studentDAOMock = mock(MySQLStudentDAO.class);
        StudentServiceImpl studentService = new StudentServiceImpl(daoFactoryMock);

        doReturn(studentDAOMock).when(daoFactoryMock).getStudentDAO();
        doNothing().when(studentDAOMock).changeStatus(any(Integer.class),
                AdditionalMatchers.or(eq("locked"), eq("unlocked")));

        OperationResult expectedLock = new OperationResult(true, "Student was locked");
        OperationResult actualLock = studentService.lockStudent(1, "locked");

        OperationResult expectedUnLock = new OperationResult(true, "Student was unlocked");
        OperationResult actualUnLock = studentService.lockStudent(1, "unlocked");

        assertEquals(expectedLock, actualLock);
        assertEquals(expectedUnLock, actualUnLock);
    }

    @Test
    public void failedOperationalResultWhenChangingStatusOfUnExistedStudent() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        StudentDAO studentDAOMock = mock(MySQLStudentDAO.class);
        StudentServiceImpl studentService = new StudentServiceImpl(daoFactoryMock);

        doReturn(studentDAOMock).when(daoFactoryMock).getStudentDAO();
        doThrow(NotExistException.class).when(studentDAOMock).changeStatus(any(Integer.class),
                AdditionalMatchers.or(eq("locked"), eq("unlocked")));

        OperationResult expectedLock = new OperationResult(false, "Student with ID = 1 does not exist");
        OperationResult actualLock = studentService.lockStudent(1, "locked");

        OperationResult expectedUnLock = new OperationResult(false, "Student with ID = 1 does not exist");
        OperationResult actualUnLock = studentService.lockStudent(1, "unlocked");

        assertEquals(expectedLock, actualLock);
        assertEquals(expectedUnLock, actualUnLock);
    }

    @Test
    public void successfulOperationResultWhenUsingShowAllStudentsMethod() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        StudentDAO studentDAOMock = mock(MySQLStudentDAO.class);
        Iterable<Student> students = mock(Iterable.class);
        StudentServiceImpl studentService = new StudentServiceImpl(daoFactoryMock);

        doReturn(studentDAOMock).when(daoFactoryMock).getStudentDAO();
        doReturn(students).when(studentDAOMock).showAllStudents();

        ValuedOperationResult<Iterable<Student>> expected = new ValuedOperationResult<>(true, "List of students", students);
        ValuedOperationResult<Iterable<Student>> actual = studentService.showAllStudents();

        assertEquals(expected, actual);
    }

    @Test
    public void successfulOperationResultWhenUsingShowAllStudentsWithLimit() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        StudentDAO studentDAOMock = mock(MySQLStudentDAO.class);
        Iterable<Student> students = mock(Iterable.class);
        StudentServiceImpl studentService = new StudentServiceImpl(daoFactoryMock);

        doReturn(studentDAOMock).when(daoFactoryMock).getStudentDAO();
        doReturn(students).when(studentDAOMock).showAllStudents(any(Integer.class), any(Integer.class));

        ValuedOperationResult<Iterable<Student>> expected = new ValuedOperationResult<>(true, "List of students", students);
        ValuedOperationResult<Iterable<Student>> actual = studentService.showAllStudents(0, 5);

        assertEquals(expected, actual);
    }

    @Test
    public void studentCountMethodReturnSuccessWithResult0WhenThereAreNoStudentsYet() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        StudentDAO studentDAOMock = mock(MySQLStudentDAO.class);
        StudentServiceImpl studentService = new StudentServiceImpl(daoFactoryMock);

        doReturn(studentDAOMock).when(daoFactoryMock).getStudentDAO();
        doReturn(0).when(studentDAOMock).studentCount();

        ValuedOperationResult<Integer> expected = new ValuedOperationResult<>(true, "There are no students yet", 0);
        ValuedOperationResult<Integer> actual = studentService.studentCount();

        assertEquals(expected,actual);
    }

    @Test
    public void studentCountMethodReturnAmountOfStudentsIfThereAnyStudentExist(){
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        StudentDAO studentDAOMock = mock(MySQLStudentDAO.class);
        StudentServiceImpl studentService = new StudentServiceImpl(daoFactoryMock);

        doReturn(studentDAOMock).when(daoFactoryMock).getStudentDAO();
        doReturn(1).when(studentDAOMock).studentCount();

        ValuedOperationResult<Integer> expected = new ValuedOperationResult<>(true, "Count of students", 1);
        ValuedOperationResult<Integer> actual = studentService.studentCount();

        assertEquals(expected,actual);
    }
}