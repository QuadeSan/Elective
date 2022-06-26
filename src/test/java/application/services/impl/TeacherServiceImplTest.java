package application.services.impl;

import application.OperationResult;
import application.dao.DAOFactory;
import application.dao.StudentDAO;
import application.dao.TeacherDAO;
import application.services.StudentService;
import application.services.TeacherService;
import data.dao.impl.MySQLStudentDAO;
import data.dao.impl.MySQLTeacherDAO;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class TeacherServiceImplTest {


    @Test
    public void AddingNewTeacherWithNewAccountIsOK() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        TeacherDAO teacherDAOMock = mock(MySQLTeacherDAO.class);
        when(daoFactoryMock.getTeacherDAO()).thenReturn(teacherDAOMock);

        TeacherServiceImpl teacherService = new TeacherServiceImpl(daoFactoryMock);
        TeacherServiceImpl teacherSpy = spy(teacherService);
        when(daoFactoryMock.getTeacherDAO()).thenReturn(teacherDAOMock);
        doNothing().when(teacherDAOMock).createTeacher(eq("newLogin"), any(), any(), any(), any());
        doReturn(new OperationResult(true, "Account was successfully created!"))
                .when(teacherSpy).createTeacher(eq("newLogin"), any(), any(), any(), any());

        OperationResult expected = new OperationResult(true, "Account was successfully created!");
        OperationResult actual = teacherSpy.createTeacher("new", "1", "any()", "any()", "any()");

        assertEquals(expected, actual);
    }

}