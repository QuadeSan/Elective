package application.services.impl;

import application.OperationResult;
import application.PasswordHashing;
import application.ValuedOperationResult;
import application.dao.*;
import application.entity.Teacher;
import data.dao.impl.MySQLTeacherDAO;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class TeacherServiceImplTest {


    @Test
    public void creationOfNewTeacherWithNewAccountIsOK() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        TeacherDAO teacherDAOMock = mock(MySQLTeacherDAO.class);
        TeacherServiceImpl teacherService = new TeacherServiceImpl();

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
        DAOFactory.setInstance(daoFactoryMock);
        TeacherDAO teacherDAOMock = mock(MySQLTeacherDAO.class);
        TeacherServiceImpl teacherService = new TeacherServiceImpl();

        doReturn(teacherDAOMock).when(daoFactoryMock).getTeacherDAO();
        doThrow(AlreadyExistException.class).when(teacherDAOMock).createTeacher(eq("existedLogin"), any(), any(), any(), any());

        OperationResult expected = new OperationResult(false, "Login already exist");
        OperationResult actual = teacherService.createTeacher("existedLogin", "newPassword",
                "newEmail", "newName", "newLastName");

        assertEquals(expected, actual);
    }

    @Test
    public void failedOperationResultWhenUsingCreateTeacherMethodAndDAOExceptionThrown() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        TeacherDAO teacherDAOMock = mock(MySQLTeacherDAO.class);
        TeacherServiceImpl teacherService = new TeacherServiceImpl();

        doReturn(teacherDAOMock).when(daoFactoryMock).getTeacherDAO();
        doThrow(DAOException.class).when(teacherDAOMock).createTeacher(any(), any(), any(), any(), any());

        OperationResult expected = new OperationResult(false, "Unhandled exception");
        OperationResult actual = teacherService.createTeacher("existedLogin", "newPassword",
                "newEmail", "newName", "newLastName");

        assertEquals(expected, actual);
    }

    @Test
    public void successOperationResultWhenTryingToLoginAsATeacherWithRightCredentials() throws NoSuchAlgorithmException, InvalidKeySpecException {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        TeacherDAO teacherDAOMock = mock(MySQLTeacherDAO.class);
        Teacher teacherMock = mock(Teacher.class);
        TeacherServiceImpl teacherService = new TeacherServiceImpl();

        doReturn(PasswordHashing.createStrongPassword("rightPass")).when(teacherMock).getPassword();
        doReturn(teacherDAOMock).when(daoFactoryMock).getTeacherDAO();
        doReturn(teacherMock).when(teacherDAOMock).findTeacher(eq("rightLogin"));

        ValuedOperationResult<Teacher> expected = new ValuedOperationResult<>(true, "You logged as Teacher", teacherMock);
        ValuedOperationResult<Teacher> actual = teacherService.authorizeTeacher("rightLogin", "rightPass");

        assertEquals(expected, actual);
    }

    @Test
    public void failedOperationResultWhenTryingToLoginAsATeacherWithWrongPassword() throws NoSuchAlgorithmException, InvalidKeySpecException {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        TeacherDAO teacherDAOMock = mock(MySQLTeacherDAO.class);
        Teacher teacherMock = mock(Teacher.class);
        TeacherServiceImpl teacherService = new TeacherServiceImpl();

        doReturn(PasswordHashing.createStrongPassword("rightPass")).when(teacherMock).getPassword();
        doReturn(teacherDAOMock).when(daoFactoryMock).getTeacherDAO();
        doReturn(teacherMock).when(teacherDAOMock).findTeacher(eq("rightLogin"));

        ValuedOperationResult<Teacher> expected = new ValuedOperationResult<>(false, "Wrong password", null);
        ValuedOperationResult<Teacher> actual = teacherService.authorizeTeacher("rightLogin", "wrongPass");

        assertEquals(expected, actual);
    }

    @Test
    public void failedOperationResultWhenTryingToLoginAsATeacherWithWrongCredentials() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        TeacherDAO teacherDAOMock = mock(MySQLTeacherDAO.class);
        TeacherServiceImpl teacherService = new TeacherServiceImpl();

        doReturn(teacherDAOMock).when(daoFactoryMock).getTeacherDAO();
        doThrow(NotExistException.class).when(teacherDAOMock).findTeacher(eq("wrongLogin"));

        ValuedOperationResult<Teacher> expected = new ValuedOperationResult<>(false, "Teacher with login = " +
                "wrongLogin does not exist", null);
        ValuedOperationResult<Teacher> actual = teacherService.authorizeTeacher("wrongLogin", "anyPass");

        assertEquals(expected, actual);
    }

    @Test
    public void failedOperationResultWhenTryingToLoginAsATeacherAndDAOExceptionThrown() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        TeacherDAO teacherDAOMock = mock(MySQLTeacherDAO.class);
        TeacherServiceImpl teacherService = new TeacherServiceImpl();

        doReturn(teacherDAOMock).when(daoFactoryMock).getTeacherDAO();
        doThrow(DAOException.class).when(teacherDAOMock).findTeacher(any());

        ValuedOperationResult<Teacher> expected = new ValuedOperationResult<>(false, "Unhandled exception", null);
        ValuedOperationResult<Teacher> actual = teacherService.authorizeTeacher("anyLogin", "anyPassword");

        assertEquals(expected, actual);
    }

    @Test
    public void successfulOperationResultAfterEditingAccountWhenAllFieldsExist() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        TeacherDAO teacherDAOMock = mock(MySQLTeacherDAO.class);
        Teacher teacherMock = mock(Teacher.class);
        TeacherServiceImpl teacherService = new TeacherServiceImpl();

        doReturn(teacherDAOMock).when(daoFactoryMock).getTeacherDAO();
        doNothing().when(teacherDAOMock).updateLogin(any(Integer.class), any());
        doNothing().when(teacherDAOMock).updateEmail(any(Integer.class), any());
        doNothing().when(teacherDAOMock).updatePassword(any(Integer.class), any());
        doNothing().when(teacherDAOMock).updateName(any(Integer.class), any());
        doNothing().when(teacherDAOMock).updateLastName(any(Integer.class), any());
        doReturn(teacherMock).when(teacherDAOMock).findTeacher(any(Integer.class));

        ValuedOperationResult<Teacher> expected = new ValuedOperationResult<>(true, "Login, Email, Password, Name, Last name, was changed", teacherMock);
        ValuedOperationResult<Teacher> actual = teacherService.editAccount(1, "anyNewLogin", "anyEmail", "anyPassword", "anyName", "anyLastName");

        assertEquals(expected, actual);
    }

    @Test
    public void failedOperationResultAfterEditingAccountWhenNewLoginAlreadyExist() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        TeacherDAO teacherDAOMock = mock(MySQLTeacherDAO.class);
        TeacherServiceImpl teacherService = new TeacherServiceImpl();

        doReturn(teacherDAOMock).when(daoFactoryMock).getTeacherDAO();
        doThrow(AlreadyExistException.class).when(teacherDAOMock).updateLogin(any(Integer.class), eq("newExistedLogin"));

        ValuedOperationResult<Teacher> expected = new ValuedOperationResult<>(false, "Login already exist", null);
        ValuedOperationResult<Teacher> actual = teacherService.editAccount(1, "newExistedLogin", "anyEmail", "anyPassword", "anyName", "anyLastName");

        assertEquals(expected, actual);
    }

    @Test
    public void failedOperationResultWhenEditingAccountAndDAOExceptionThrown() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        TeacherDAO teacherDAOMock = mock(MySQLTeacherDAO.class);
        TeacherServiceImpl teacherService = new TeacherServiceImpl();

        doReturn(teacherDAOMock).when(daoFactoryMock).getTeacherDAO();
        doThrow(DAOException.class).when(teacherDAOMock).updateLogin(any(Integer.class), any());

        ValuedOperationResult<Teacher> expected = new ValuedOperationResult<>(false, "Account information was not changed", null);
        ValuedOperationResult<Teacher> actual = teacherService.editAccount(1, "anyLogin", "anyEmail", "anyPassword", "anyName", "anyLastName");

        assertEquals(expected, actual);
    }

    @Test
    public void successfulOperationResultWhenUsingShowAllTeachersMethod() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        TeacherDAO teacherDAOMock = mock(MySQLTeacherDAO.class);
        Iterable<Teacher> teachers = mock(Iterable.class);
        TeacherServiceImpl teacherService = new TeacherServiceImpl();

        doReturn(teacherDAOMock).when(daoFactoryMock).getTeacherDAO();
        doReturn(teachers).when(teacherDAOMock).showAllTeachers();

        ValuedOperationResult<Iterable<Teacher>> expected = new ValuedOperationResult<>(true, "List of teachers", teachers);
        ValuedOperationResult<Iterable<Teacher>> actual = teacherService.showAllTeachers();

        assertEquals(expected, actual);
    }

    @Test
    public void failedOperationResultWhenUsingShowAllTeachersMethodAndDAOExceptionThrown() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        TeacherDAO teacherDAOMock = mock(MySQLTeacherDAO.class);
        TeacherServiceImpl teacherService = new TeacherServiceImpl();

        doReturn(teacherDAOMock).when(daoFactoryMock).getTeacherDAO();
        doThrow(DAOException.class).when(teacherDAOMock).showAllTeachers();

        ValuedOperationResult<Iterable<Teacher>> expected = new ValuedOperationResult<>(false, "Unhandled exception", null);
        ValuedOperationResult<Iterable<Teacher>> actual = teacherService.showAllTeachers();

        assertEquals(expected, actual);
    }

    @Test
    public void failedOperationResultWhenServiceCanNotCloseDAOInCreateTeacherMethod() throws Exception {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        TeacherDAO teacherDAOMock = mock(MySQLTeacherDAO.class);
        TeacherServiceImpl teacherService = new TeacherServiceImpl();

        doReturn(teacherDAOMock).when(daoFactoryMock).getTeacherDAO();
        doThrow(Exception.class).when(teacherDAOMock).close();

        OperationResult expected = new OperationResult(false, "Unhandled exception");
        OperationResult actual = teacherService.createTeacher("existedLogin", "newPassword",
                "newEmail", "newName", "newLastName");

        assertEquals(expected, actual);
    }

    @Test
    public void failedOperationResultWhenServiceCanNotCloseDAOInAuthorizeTeacherMethod() throws Exception {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        TeacherDAO teacherDAOMock = mock(MySQLTeacherDAO.class);
        TeacherServiceImpl teacherService = new TeacherServiceImpl();

        doReturn(teacherDAOMock).when(daoFactoryMock).getTeacherDAO();
        doThrow(Exception.class).when(teacherDAOMock).close();

        ValuedOperationResult<Teacher> expected = new ValuedOperationResult<>(false, "Unhandled exception", null);
        ValuedOperationResult<Teacher> actual = teacherService.authorizeTeacher("anyLogin", "anyPassword");

        assertEquals(expected, actual);
    }

    @Test
    public void failedOperationResultWhenServiceCanNotCloseDAOInShowAllTeachersMethod() throws Exception {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        TeacherDAO teacherDAOMock = mock(MySQLTeacherDAO.class);
        TeacherServiceImpl teacherService = new TeacherServiceImpl();

        doReturn(teacherDAOMock).when(daoFactoryMock).getTeacherDAO();
        doThrow(Exception.class).when(teacherDAOMock).close();

        ValuedOperationResult<Iterable<Teacher>> expected = new ValuedOperationResult<>(false, "Unhandled exception", null);
        ValuedOperationResult<Iterable<Teacher>> actual = teacherService.showAllTeachers();

        assertEquals(expected, actual);
    }

    @Test
    public void failedOperationResultWhenServiceCanNotCloseDAOInEditAccountMethod() throws Exception {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        TeacherDAO teacherDAOMock = mock(MySQLTeacherDAO.class);
        TeacherServiceImpl teacherService = new TeacherServiceImpl();

        doReturn(teacherDAOMock).when(daoFactoryMock).getTeacherDAO();
        doThrow(Exception.class).when(teacherDAOMock).close();

        ValuedOperationResult<Teacher> expected = new ValuedOperationResult<>(false, "Unhandled exception", null);
        ValuedOperationResult<Teacher> actual = teacherService.editAccount(1, "anyLogin", "anyEmail", "anyPassword", "anyName", "anyLastName");

        assertEquals(expected, actual);
    }
}