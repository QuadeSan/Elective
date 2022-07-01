package application.services.impl;

import application.OperationResult;
import application.ValuedOperationResult;
import application.dao.AlreadyExistException;
import application.dao.DAOFactory;
import application.dao.NotExistException;
import application.dao.TeacherDAO;
import application.entity.Teacher;
import data.dao.impl.MySQLTeacherDAO;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class TeacherServiceImplTest {


    @Test
    public void creationOfNewTeacherWithNewAccountIsOK() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        TeacherDAO teacherDAOMock = mock(MySQLTeacherDAO.class);
        TeacherServiceImpl teacherService = new TeacherServiceImpl(daoFactoryMock);

        doReturn(teacherDAOMock).when(daoFactoryMock).getTeacherDAO();
        doNothing().when(teacherDAOMock).createTeacher(eq("newLogin"), any(), any(), any(), any());

        OperationResult expected = new OperationResult(true, "Account was successfully created!");
        OperationResult actual = teacherService.createTeacher("newLogin", "newPassword",
                "newEmail", "newName", "newLastName");

        assertEquals(expected, actual);
    }

    @Test
    public void creationOfNewTeacherWithExistedAccountIsThrowingExceptionCausingFalseOperationResult() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        TeacherDAO teacherDAOMock = mock(MySQLTeacherDAO.class);
        TeacherServiceImpl teacherService = new TeacherServiceImpl(daoFactoryMock);

        doReturn(teacherDAOMock).when(daoFactoryMock).getTeacherDAO();
        doThrow(AlreadyExistException.class).when(teacherDAOMock).createTeacher(eq("existedLogin"), any(), any(), any(), any());

        OperationResult expected = new OperationResult(false, "Login already exist");
        OperationResult actual = teacherService.createTeacher("existedLogin", "newPassword",
                "newEmail", "newName", "newLastName");

        assertEquals(expected, actual);
    }

    @Test
    public void successOperationResultWhenTryingToLoginAsATeacherWithRightCredentials() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        TeacherDAO teacherDAOMock = mock(MySQLTeacherDAO.class);
        TeacherServiceImpl teacherService = new TeacherServiceImpl(daoFactoryMock);

        doReturn(teacherDAOMock).when(daoFactoryMock).getTeacherDAO();
        doReturn(new Teacher()).when(teacherDAOMock).findTeacher(eq("rightLogin"), eq("rightPass"));

        ValuedOperationResult<Teacher> expected = new ValuedOperationResult<>(true, "You logged as Teacher", new Teacher());
        ValuedOperationResult<Teacher> actual = teacherService.findTeacher("rightLogin", "rightPass");

        assertEquals(expected, actual);
    }

    @Test
    public void failedOperationResultWhenTryingToLoginAsATeacherWithWrongCredentials() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        TeacherDAO teacherDAOMock = mock(MySQLTeacherDAO.class);
        TeacherServiceImpl teacherService = new TeacherServiceImpl(daoFactoryMock);

        doReturn(teacherDAOMock).when(daoFactoryMock).getTeacherDAO();
        doThrow(NotExistException.class).when(teacherDAOMock).findTeacher(eq("wrongLogin"), eq("wrongPass"));


        ValuedOperationResult<Teacher> expected = new ValuedOperationResult<>(false, "Teacher with login = " +
                "wrongLogin does not exist", null);
        ValuedOperationResult<Teacher> actual = teacherService.findTeacher("wrongLogin", "wrongPass");

        assertEquals(expected, actual);
    }

    @Test
    public void successfulOperationResultWhenUsingShowAllTeachersMethod() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        TeacherDAO teacherDAOMock = mock(MySQLTeacherDAO.class);
        Iterable<Teacher> teachers = mock(Iterable.class);
        TeacherServiceImpl teacherService = new TeacherServiceImpl(daoFactoryMock);

        doReturn(teacherDAOMock).when(daoFactoryMock).getTeacherDAO();
        doReturn(teachers).when(teacherDAOMock).showAllTeachers();

        ValuedOperationResult<Iterable<Teacher>> expected = new ValuedOperationResult<>(true, "List of teachers", teachers);
        ValuedOperationResult<Iterable<Teacher>> actual = teacherService.showAllTeachers();

        assertEquals(expected, actual);
    }
}