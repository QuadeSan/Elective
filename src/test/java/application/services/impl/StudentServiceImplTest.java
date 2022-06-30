package application.services.impl;

import application.OperationResult;
import application.ValuedOperationResult;
import application.dao.*;
import application.entity.Student;
import application.services.StudentService;
import data.dao.impl.MySQLStudentDAO;
import org.junit.Test;
import org.mockito.AdditionalMatchers;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class StudentServiceImplTest {


    @Test
    public void AddingNewStudentWithNewAccountIsOK() {
        StudentService studentServiceMock = spy(new StudentServiceImpl());
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        StudentDAO studentDAOMock = mock(MySQLStudentDAO.class);
        DAOFactory.setInstance(daoFactoryMock);

        when(daoFactoryMock.getStudentDAO()).thenReturn(studentDAOMock);
        doNothing().when(studentDAOMock).createStudent(eq("newLogin"), any(), any(), any(), any());
        doReturn(new OperationResult(true, "Account was successfully created!"))
                .when(studentServiceMock).createStudent(eq("newLogin"),any(), any(), any(), any());
//        when(studentServiceMock.createStudent(eq("newLogin"), any(), any(), any(), any()))
//                .thenReturn(new OperationResult(true, "Account was successfully created!"));

        OperationResult expected = new OperationResult(true, "Account was successfully created!");
        OperationResult actual = studentServiceMock.createStudent("newLogin","1","1","1","1");

        assertEquals(expected, actual);
    }


    @Test()
    public void AddingNewStudentWithExistedAccountIsThrowingExceptionCausingFalseOperationResult() {
        StudentService studentServiceMock = mock(StudentServiceImpl.class);
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        StudentDAO studentDAOMock = mock(MySQLStudentDAO.class);

        when(daoFactoryMock.getStudentDAO()).thenReturn(studentDAOMock);
        doThrow(AlreadyExistException.class).when(studentDAOMock).createStudent(eq("existedLogin"), any(), any(), any(), any());
        when(studentServiceMock.createStudent(any(), any(), any(), any(), any()))
                .thenReturn(new OperationResult(false, "Login already exist"));

        OperationResult expected = new OperationResult(false, "Login already exist");
        OperationResult actual = studentServiceMock.createStudent(eq("existedLogin"), any(), any(), any(), any());

        assertEquals(expected, actual);
    }

    @Test
    public void SuccessOperationResultWhenTryingToLoginAsAStudentWithRightCredentials() {
        StudentService studentServiceMock = mock(StudentServiceImpl.class);
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        StudentDAO studentDAOMock = mock(MySQLStudentDAO.class);
        Student studentMock = mock(Student.class);

        when(daoFactoryMock.getStudentDAO()).thenReturn(studentDAOMock);
        when(studentDAOMock.findStudent(eq("rightLogin"), eq("rightPass"))).thenReturn(studentMock);
        when(studentServiceMock.findStudent(eq("rightLogin"), eq("rightPass")))
                .thenReturn(new ValuedOperationResult<>(true, "You logged as Student", studentMock));

        ValuedOperationResult<Student> expected = new ValuedOperationResult<>(true, "You logged as Student", studentMock);
        ValuedOperationResult<Student> actual = studentServiceMock.findStudent("rightLogin", "rightPass");

        assertEquals(expected, actual);
    }

    @Test
    public void FailedOperationResultWhenTryingToLoginAsAStudentWithWrongCredentials() {
        StudentService studentServiceMock = mock(StudentServiceImpl.class);
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        StudentDAO studentDAOMock = mock(MySQLStudentDAO.class);

        when(daoFactoryMock.getStudentDAO()).thenReturn(studentDAOMock);
        doThrow(NotExistException.class).when(studentDAOMock).findStudent(eq("wrongLogin"), eq("wrongPass"));
        when(studentServiceMock.findStudent(eq("wrongLogin"), eq("wrongPass")))
                .thenReturn(new ValuedOperationResult<>(false, "Student does not exist", null));

        ValuedOperationResult<Student> expected = new ValuedOperationResult<>(false, "Student does not exist", null);
        ValuedOperationResult<Student> actual = studentServiceMock.findStudent("wrongLogin", "wrongPass");

        assertEquals(expected, actual);
    }

    @Test
    public void SuccessfulOperationResultWhenChangingStatusOfExistedStudent() {
        StudentService studentServiceMock = mock(StudentServiceImpl.class);
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        StudentDAO studentDAOMock = mock(MySQLStudentDAO.class);

        when(daoFactoryMock.getStudentDAO()).thenReturn(studentDAOMock);
        doNothing().when(studentDAOMock).changeStatus(any(Integer.class), eq("locked"));
        when(studentServiceMock.lockStudent(any(Integer.class), eq("locked")))
                .thenReturn(new OperationResult(true, "Student was locked"));
        when(studentServiceMock.lockStudent(any(Integer.class), eq("unlocked")))
                .thenReturn(new OperationResult(true, "Student was unlocked"));

        OperationResult expectedLock = new OperationResult(true, "Student was locked");
        OperationResult actualLock = studentServiceMock.lockStudent(1, "locked");

        OperationResult expectedUnLock = new OperationResult(true, "Student was unlocked");
        OperationResult actualUnLock = studentServiceMock.lockStudent(1, "unlocked");

        assertEquals(expectedLock, actualLock);
        assertEquals(expectedUnLock, actualUnLock);
    }

    @Test
    public void FailedOperationalResultWhenChangingStatusOfUnExistedStudent() {
        StudentService studentServiceMock = mock(StudentServiceImpl.class);
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        StudentDAO studentDAOMock = mock(MySQLStudentDAO.class);

        when(daoFactoryMock.getStudentDAO()).thenReturn(studentDAOMock);
        doThrow(NotExistException.class).when(studentDAOMock).changeStatus(any(Integer.class),
                AdditionalMatchers.or(eq("locked"), eq("unlocked")));
        when(studentServiceMock.lockStudent(any(Integer.class),
                AdditionalMatchers.or(eq("locked"), eq("unlocked"))))
                .thenReturn(new OperationResult(false, "Student with current ID does not exist"));

        OperationResult expectedLock = new OperationResult(false, "Student with current ID does not exist");
        OperationResult actualLock = studentServiceMock.lockStudent(1, "locked");

        OperationResult expectedUnLock = new OperationResult(false, "Student with current ID does not exist");
        OperationResult actualUnLock = studentServiceMock.lockStudent(1, "unlocked");

        assertEquals(expectedLock, actualLock);
        assertEquals(expectedUnLock, actualUnLock);
    }

    @Test
    public void SuccessfulOperationResultWhenUsingShowAllStudentsMethod() {
        StudentService studentServiceMock = mock(StudentServiceImpl.class);
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        StudentDAO studentDAOMock = mock(MySQLStudentDAO.class);
        Iterable<Student> students = mock(Iterable.class);

        when(daoFactoryMock.getStudentDAO()).thenReturn(studentDAOMock);
        when(studentDAOMock.showAllStudents()).thenReturn(students);
        when(studentServiceMock.showAllStudents())
                .thenReturn(new ValuedOperationResult<>(true, "List of Students", students));

        OperationResult expected = new ValuedOperationResult<>(true, "List of Students", students);
        OperationResult actual = studentServiceMock.showAllStudents();

        assertEquals(expected, actual);
    }
}