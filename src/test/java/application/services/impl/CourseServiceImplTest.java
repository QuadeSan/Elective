package application.services.impl;

import application.OperationResult;
import application.ValuedOperationResult;
import application.dao.*;
import application.entity.Course;
import data.dao.impl.MySQLAdministratorDAO;
import data.dao.impl.MySQLCourseDAO;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class CourseServiceImplTest {

    @Test
    public void creationOfNewCourseWithNewTitleIsOK() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        CourseDAO courseDAOMock = mock(MySQLCourseDAO.class);
        CourseServiceImpl courseService = new CourseServiceImpl();

        doReturn(courseDAOMock).when(daoFactoryMock).getCourseDAO();
        doNothing().when(courseDAOMock).createCourse(eq("newTopic"), eq("newTitle"));

        OperationResult expected = new OperationResult(true, "New course was successfully created");
        OperationResult actual = courseService.createCourse("newTopic", "newTitle");

        assertEquals(expected, actual);
    }


    @Test
    public void creationOfNewCourseWithExistedTitleIsThrowingExceptionCausingFalseOperationResult() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        CourseDAO courseDAOMock = mock(MySQLCourseDAO.class);
        CourseServiceImpl courseService = new CourseServiceImpl();

        doReturn(courseDAOMock).when(daoFactoryMock).getCourseDAO();
        doThrow(AlreadyExistException.class).when(courseDAOMock).createCourse(eq("newTopic"), eq("existedTitle"));

        OperationResult expected = new OperationResult(false, "Course with title existedTitle already exist");
        OperationResult actual = courseService.createCourse("newTopic", "existedTitle");

        assertEquals(expected, actual);
    }

    @Test
    public void failedOperationResultWhenCreateCourseMethodUsedAndDAOExceptionThrown() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        CourseDAO courseDAOMock = mock(MySQLCourseDAO.class);
        CourseServiceImpl courseService = new CourseServiceImpl();

        doReturn(courseDAOMock).when(daoFactoryMock).getCourseDAO();
        doThrow(DAOException.class).when(courseDAOMock).createCourse(any(), any());

        OperationResult expected = new OperationResult(false, "Unhandled exception");
        OperationResult actual = courseService.createCourse("newTopic", "existedTitle");

        assertEquals(expected, actual);
    }

    @Test
    public void successOperationResultWhenTryingToFindCourseWithExistedId() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        CourseDAO courseDAOMock = mock(MySQLCourseDAO.class);
        CourseServiceImpl courseService = new CourseServiceImpl();

        doReturn(courseDAOMock).when(daoFactoryMock).getCourseDAO();
        doReturn(new Course()).when(courseDAOMock).findCourse(1);

        ValuedOperationResult<Course> expected = new ValuedOperationResult<>(true, "Course was found", new Course());
        ValuedOperationResult<Course> actual = courseService.findCourse(1);

        assertEquals(expected, actual);
    }

    @Test
    public void failedOperationResultWhenTryingTryingToFindCourseWithUnExistedId() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        CourseDAO courseDAOMock = mock(MySQLCourseDAO.class);
        CourseServiceImpl courseService = new CourseServiceImpl();

        doReturn(courseDAOMock).when(daoFactoryMock).getCourseDAO();
        doThrow(NotExistException.class).when(courseDAOMock).findCourse(-1);

        ValuedOperationResult<Course> expected = new ValuedOperationResult<>(false, "Course with ID = " +
                "-1 does not exist", null);
        ValuedOperationResult<Course> actual = courseService.findCourse(-1);

        assertEquals(expected, actual);
    }

    @Test
    public void failedOperationResultWhenTryingTryingToFindCourseAndDAOExceptionThrown() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        CourseDAO courseDAOMock = mock(MySQLCourseDAO.class);
        CourseServiceImpl courseService = new CourseServiceImpl();

        doReturn(courseDAOMock).when(daoFactoryMock).getCourseDAO();
        doThrow(DAOException.class).when(courseDAOMock).findCourse(any(Integer.class));

        ValuedOperationResult<Course> expected = new ValuedOperationResult<>(false, "Unhandled exception", null);
        ValuedOperationResult<Course> actual = courseService.findCourse(1);

        assertEquals(expected, actual);
    }

    @Test
    public void CourseWasSuccessfullyDeletedWhenMethodWasUsedWithExistedId() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        CourseDAO courseDAOMock = mock(MySQLCourseDAO.class);
        CourseServiceImpl courseService = new CourseServiceImpl();

        doReturn(courseDAOMock).when(daoFactoryMock).getCourseDAO();
        doNothing().when(courseDAOMock).deleteCourse(1);

        OperationResult expected = new OperationResult(true, "Course was successfully deleted");
        OperationResult actual = courseService.deleteCourse(1);

        assertEquals(expected, actual);
    }

    @Test
    public void ExceptionWasThrownWhenTryingToDeleteCourseWithUnExistedId() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        CourseDAO courseDAOMock = mock(MySQLCourseDAO.class);
        CourseServiceImpl courseService = new CourseServiceImpl();
        doReturn(courseDAOMock).when(daoFactoryMock).getCourseDAO();
        doThrow(NotExistException.class).when(courseDAOMock).deleteCourse(-1);

        OperationResult expected = new OperationResult(false, "Course with ID = " +
                "-1 does not exist");
        OperationResult actual = courseService.deleteCourse(-1);

        assertEquals(expected, actual);
    }

    @Test
    public void successfulOperationResultWhenChangingTopicOfExistedCourse() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        CourseDAO courseDAOMock = mock(MySQLCourseDAO.class);
        CourseServiceImpl courseService = new CourseServiceImpl();

        doReturn(courseDAOMock).when(daoFactoryMock).getCourseDAO();
        doNothing().when(courseDAOMock).changeTopic(eq(1), eq("newTopic"));

        OperationResult expected = new OperationResult(true, "Topic was changed");
        OperationResult actual = courseService.changeTopic(1, "newTopic");

        assertEquals(expected, actual);
    }

    @Test
    public void failedOperationResultWhenChangingTopicAndDAOExceptionThrown() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        CourseDAO courseDAOMock = mock(MySQLCourseDAO.class);
        CourseServiceImpl courseService = new CourseServiceImpl();

        doReturn(courseDAOMock).when(daoFactoryMock).getCourseDAO();
        doThrow(DAOException.class).when(courseDAOMock).changeTopic(any(Integer.class), any());

        OperationResult expected = new OperationResult(false, "Unhandled exception");
        OperationResult actual = courseService.changeTopic(1, "anyTopic");

        assertEquals(expected, actual);
    }

    @Test
    public void successfulOperationResultWhenChangingTitleOfExistedCourse() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        CourseDAO courseDAOMock = mock(MySQLCourseDAO.class);
        CourseServiceImpl courseService = new CourseServiceImpl();
        doReturn(courseDAOMock).when(daoFactoryMock).getCourseDAO();
        doNothing().when(courseDAOMock).changeTitle(eq(1), eq("newTitle"));

        OperationResult expected = new OperationResult(true, "Title was changed");
        OperationResult actual = courseService.changeTitle(1, "newTitle");

        assertEquals(expected, actual);
    }

    @Test
    public void failedOperationResultWhenChangingTitleAndDAOExceptionThrown() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        CourseDAO courseDAOMock = mock(MySQLCourseDAO.class);
        CourseServiceImpl courseService = new CourseServiceImpl();

        doReturn(courseDAOMock).when(daoFactoryMock).getCourseDAO();
        doThrow(DAOException.class).when(courseDAOMock).changeTitle(any(Integer.class), any());

        OperationResult expected = new OperationResult(false, "Unhandled exception");
        OperationResult actual = courseService.changeTitle(1, "anyTitle");

        assertEquals(expected, actual);
    }

    @Test
    public void successfulOperationResultWhenChangingStatusOfExistedCourse() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        CourseDAO courseDAOMock = mock(MySQLCourseDAO.class);
        CourseServiceImpl courseService = new CourseServiceImpl();

        doReturn(courseDAOMock).when(daoFactoryMock).getCourseDAO();
        doNothing().when(courseDAOMock).changeStatus(eq(1), eq("newStatus"));

        OperationResult expected = new OperationResult(true, "Status was changed");
        OperationResult actual = courseService.changeStatus(1, "newStatus");

        assertEquals(expected, actual);
    }

    @Test
    public void successfulOperationResultWhenChangingStatusOfUnExistedCourse() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        CourseDAO courseDAOMock = mock(MySQLCourseDAO.class);
        CourseServiceImpl courseService = new CourseServiceImpl();

        doReturn(courseDAOMock).when(daoFactoryMock).getCourseDAO();
        doThrow(NotExistException.class).when(courseDAOMock).changeStatus(eq(-1), any(String.class));

        OperationResult expected = new OperationResult(false, "Course with ID = " +
                "-1 does not exist");
        OperationResult actual = courseService.changeStatus(-1, "newStatus");

        assertEquals(expected, actual);
    }

    @Test
    public void failedOperationResultWhenChangingStatusAndDAOExceptionThrown() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        CourseDAO courseDAOMock = mock(MySQLCourseDAO.class);
        CourseServiceImpl courseService = new CourseServiceImpl();

        doReturn(courseDAOMock).when(daoFactoryMock).getCourseDAO();
        doThrow(DAOException.class).when(courseDAOMock).changeStatus(any(Integer.class), any());

        OperationResult expected = new OperationResult(false, "Unhandled exception");
        OperationResult actual = courseService.changeStatus(1, "anyStatus");

        assertEquals(expected, actual);
    }

    @Test
    public void successfulOperationResultWhenUsingShowAllStudentsMethod() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        CourseDAO courseDAOMock = mock(MySQLCourseDAO.class);
        Iterable<Course> courses = mock(Iterable.class);
        CourseServiceImpl courseService = new CourseServiceImpl();

        doReturn(courseDAOMock).when(daoFactoryMock).getCourseDAO();
        doReturn(courses).when(courseDAOMock).showAllCourses();

        ValuedOperationResult<Iterable<Course>> expected = new ValuedOperationResult<>(true, "List of courses", courses);
        ValuedOperationResult<Iterable<Course>> actual = courseService.showAllCourses();

        assertEquals(expected, actual);
    }

    @Test
    public void failedOperationResultWhenUsingShowAllStudentsMethodAndDAOExceptionThrown() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        CourseDAO courseDAOMock = mock(MySQLCourseDAO.class);
        CourseServiceImpl courseService = new CourseServiceImpl();

        doReturn(courseDAOMock).when(daoFactoryMock).getCourseDAO();
        doThrow(DAOException.class).when(courseDAOMock).showAllCourses();

        ValuedOperationResult<Iterable<Course>> expected = new ValuedOperationResult<>(false, "Unhandled exception", null);
        ValuedOperationResult<Iterable<Course>> actual = courseService.showAllCourses();

        assertEquals(expected, actual);
    }

    @Test
    public void failedOperationResultWhenServiceCanNotCloseDAOInCreateCourseMethod() throws Exception {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        CourseDAO courseDAOMock = mock(MySQLCourseDAO.class);
        CourseServiceImpl courseService = new CourseServiceImpl();

        doReturn(courseDAOMock).when(daoFactoryMock).getCourseDAO();
        doThrow(Exception.class).when(courseDAOMock).close();

        OperationResult expected = new OperationResult(false, "Unhandled exception");
        OperationResult actual = courseService.createCourse("anyTitle", "anyTopic");

        assertEquals(expected, actual);
    }

    @Test
    public void failedOperationResultWhenServiceCanNotCloseDAOInFindCourseMethod() throws Exception {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        CourseDAO courseDAOMock = mock(MySQLCourseDAO.class);
        CourseServiceImpl courseService = new CourseServiceImpl();

        doReturn(courseDAOMock).when(daoFactoryMock).getCourseDAO();
        doThrow(Exception.class).when(courseDAOMock).close();

        ValuedOperationResult<Course> expected = new ValuedOperationResult<>(false, "Unhandled exception", null);
        ValuedOperationResult<Course> actual = courseService.findCourse(1);

        assertEquals(expected, actual);
    }

    @Test
    public void failedOperationResultWhenServiceCanNotCloseDAOInDeleteCourseMethod() throws Exception {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        CourseDAO courseDAOMock = mock(MySQLCourseDAO.class);
        CourseServiceImpl courseService = new CourseServiceImpl();

        doReturn(courseDAOMock).when(daoFactoryMock).getCourseDAO();
        doThrow(Exception.class).when(courseDAOMock).close();

        OperationResult expected = new OperationResult(false, "Unhandled exception");
        OperationResult actual = courseService.deleteCourse(1);

        assertEquals(expected, actual);
    }

    @Test
    public void failedOperationResultWhenServiceCanNotCloseDAOInChangeTopicMethod() throws Exception {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        CourseDAO courseDAOMock = mock(MySQLCourseDAO.class);
        CourseServiceImpl courseService = new CourseServiceImpl();

        doReturn(courseDAOMock).when(daoFactoryMock).getCourseDAO();
        doThrow(Exception.class).when(courseDAOMock).close();

        OperationResult expected = new OperationResult(false, "Unhandled exception");
        OperationResult actual = courseService.changeTopic(1, "anyTopic");

        assertEquals(expected, actual);
    }

    @Test
    public void failedOperationResultWhenServiceCanNotCloseDAOInChangeTitleMethod() throws Exception {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        CourseDAO courseDAOMock = mock(MySQLCourseDAO.class);
        CourseServiceImpl courseService = new CourseServiceImpl();

        doReturn(courseDAOMock).when(daoFactoryMock).getCourseDAO();
        doThrow(Exception.class).when(courseDAOMock).close();

        OperationResult expected = new OperationResult(false, "Unhandled exception");
        OperationResult actual = courseService.changeTitle(1, "anyTitle");

        assertEquals(expected, actual);
    }

    @Test
    public void failedOperationResultWhenServiceCanNotCloseDAOInChangeStatusMethod() throws Exception {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        CourseDAO courseDAOMock = mock(MySQLCourseDAO.class);
        CourseServiceImpl courseService = new CourseServiceImpl();

        doReturn(courseDAOMock).when(daoFactoryMock).getCourseDAO();
        doThrow(Exception.class).when(courseDAOMock).close();

        OperationResult expected = new OperationResult(false, "Unhandled exception");
        OperationResult actual = courseService.changeStatus(1, "anyStatus");

        assertEquals(expected, actual);
    }

    @Test
    public void failedOperationResultWhenServiceCanNotCloseDAOInShowAllCoursesMethod() throws Exception {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        DAOFactory.setInstance(daoFactoryMock);
        CourseDAO courseDAOMock = mock(MySQLCourseDAO.class);
        CourseServiceImpl courseService = new CourseServiceImpl();

        doReturn(courseDAOMock).when(daoFactoryMock).getCourseDAO();
        doThrow(Exception.class).when(courseDAOMock).close();

        ValuedOperationResult<Iterable<Course>> expected = new ValuedOperationResult<>(false, "Unhandled exception", null);
        ValuedOperationResult<Iterable<Course>> actual = courseService.showAllCourses();

        assertEquals(expected, actual);
    }
}