package application.services.impl;

import application.OperationResult;
import application.ValuedOperationResult;
import application.dao.AlreadyExistException;
import application.dao.CourseDAO;
import application.dao.DAOFactory;
import application.dao.NotExistException;
import application.entity.Course;
import data.dao.impl.MySQLCourseDAO;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class CourseServiceImplTest {

    @Test
    public void creationOfNewCourseWithNewTitleIsOK() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        CourseDAO courseDAOMock = mock(MySQLCourseDAO.class);
        CourseServiceImpl courseService = new CourseServiceImpl(daoFactoryMock);

        doReturn(courseDAOMock).when(daoFactoryMock).getCourseDAO();
        doNothing().when(courseDAOMock).createCourse(eq("newTopic"), eq("newTitle"));

        OperationResult expected = new OperationResult(true, "New course was successfully created");
        OperationResult actual = courseService.createCourse("newTopic", "newTitle");

        assertEquals(expected, actual);
    }


    @Test
    public void creationOfNewCourseWithExistedTitleIsThrowingExceptionCausingFalseOperationResult() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        CourseDAO courseDAOMock = mock(MySQLCourseDAO.class);
        CourseServiceImpl courseService = new CourseServiceImpl(daoFactoryMock);

        doReturn(courseDAOMock).when(daoFactoryMock).getCourseDAO();
        doThrow(AlreadyExistException.class).when(courseDAOMock).createCourse(eq("newTopic"), eq("existedTitle"));

        OperationResult expected = new OperationResult(false, "Course with title existedTitle already exist");
        OperationResult actual = courseService.createCourse("newTopic", "existedTitle");

        assertEquals(expected, actual);
    }

    @Test
    public void successOperationResultWhenTryingToFindCourseWithExistedId() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        CourseDAO courseDAOMock = mock(MySQLCourseDAO.class);
        CourseServiceImpl courseService = new CourseServiceImpl(daoFactoryMock);

        doReturn(courseDAOMock).when(daoFactoryMock).getCourseDAO();
        doReturn(new Course()).when(courseDAOMock).findCourse(1);

        ValuedOperationResult<Course> expected = new ValuedOperationResult<>(true, "Course was found", new Course());
        ValuedOperationResult<Course> actual = courseService.findCourse(1);

        assertEquals(expected, actual);
    }

    @Test
    public void failedOperationResultWhenTryingTryingToFindCourseWithUnExistedId() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        CourseDAO courseDAOMock = mock(MySQLCourseDAO.class);
        CourseServiceImpl courseService = new CourseServiceImpl(daoFactoryMock);

        doReturn(courseDAOMock).when(daoFactoryMock).getCourseDAO();
        doThrow(NotExistException.class).when(courseDAOMock).findCourse(-1);


        ValuedOperationResult<Course> expected = new ValuedOperationResult<>(false, "Course with ID = " +
                "-1 does not exist", null);
        ValuedOperationResult<Course> actual = courseService.findCourse(-1);

        assertEquals(expected, actual);
    }

    @Test
    public void CourseWasSuccessfullyDeletedWhenMethodWasUsedWithExistedId() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        CourseDAO courseDAOMock = mock(MySQLCourseDAO.class);
        CourseServiceImpl courseService = new CourseServiceImpl(daoFactoryMock);

        doReturn(courseDAOMock).when(daoFactoryMock).getCourseDAO();
        doNothing().when(courseDAOMock).deleteCourse(1);

        OperationResult expected = new OperationResult(true, "Course was successfully deleted");
        OperationResult actual = courseService.deleteCourse(1);

        assertEquals(expected, actual);
    }

    @Test
    public void ExceptionWasThrownWhenTryingToDeleteCourseWithUnExistedId() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        CourseDAO courseDAOMock = mock(MySQLCourseDAO.class);
        CourseServiceImpl courseService = new CourseServiceImpl(daoFactoryMock);

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
        CourseDAO courseDAOMock = mock(MySQLCourseDAO.class);
        CourseServiceImpl courseService = new CourseServiceImpl(daoFactoryMock);

        doReturn(courseDAOMock).when(daoFactoryMock).getCourseDAO();
        doNothing().when(courseDAOMock).changeTopic(eq(1), eq("newTopic"));

        OperationResult expected = new OperationResult(true, "Topic was changed");
        OperationResult actual = courseService.changeTopic(1, "newTopic");

        assertEquals(expected, actual);
    }

    @Test
    public void successfulOperationResultWhenChangingTitleOfExistedCourse() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        CourseDAO courseDAOMock = mock(MySQLCourseDAO.class);
        CourseServiceImpl courseService = new CourseServiceImpl(daoFactoryMock);

        doReturn(courseDAOMock).when(daoFactoryMock).getCourseDAO();
        doNothing().when(courseDAOMock).changeTitle(eq(1), eq("newTitle"));

        OperationResult expected = new OperationResult(true, "Title was changed");
        OperationResult actual = courseService.changeTitle(1, "newTitle");

        assertEquals(expected, actual);
    }

    @Test
    public void successfulOperationResultWhenChangingStatusOfExistedCourse() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        CourseDAO courseDAOMock = mock(MySQLCourseDAO.class);
        CourseServiceImpl courseService = new CourseServiceImpl(daoFactoryMock);

        doReturn(courseDAOMock).when(daoFactoryMock).getCourseDAO();
        doNothing().when(courseDAOMock).changeStatus(eq(1), eq("newStatus"));

        OperationResult expected = new OperationResult(true, "Status was changed");
        OperationResult actual = courseService.changeStatus(1, "newStatus");

        assertEquals(expected, actual);
    }

    @Test
    public void successfulOperationResultWhenChangingStatusOfUnExistedCourse() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        CourseDAO courseDAOMock = mock(MySQLCourseDAO.class);
        CourseServiceImpl courseService = new CourseServiceImpl(daoFactoryMock);

        doReturn(courseDAOMock).when(daoFactoryMock).getCourseDAO();
        doThrow(NotExistException.class).when(courseDAOMock).changeStatus(eq(-1), any(String.class));

        OperationResult expected = new OperationResult(false, "Course with ID = " +
                "-1 does not exist");
        OperationResult actual = courseService.changeStatus(-1, "newStatus");

        assertEquals(expected, actual);
    }

    @Test
    public void successfulOperationResultWhenUsingShowAllStudentsMethod() {
        DAOFactory daoFactoryMock = mock(DAOFactory.class);
        CourseDAO courseDAOMock = mock(MySQLCourseDAO.class);
        Iterable<Course> courses = mock(Iterable.class);
        CourseServiceImpl courseService = new CourseServiceImpl(daoFactoryMock);

        doReturn(courseDAOMock).when(daoFactoryMock).getCourseDAO();
        doReturn(courses).when(courseDAOMock).showAllCourses();

        ValuedOperationResult<Iterable<Course>> expected = new ValuedOperationResult<>(true, "List of courses", courses);
        ValuedOperationResult<Iterable<Course>> actual = courseService.showAllCourses();

        assertEquals(expected, actual);
    }
}