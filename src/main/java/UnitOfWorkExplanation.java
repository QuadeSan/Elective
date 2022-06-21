/*
import com.mysql.cj.jdbc.ConnectionImpl;

import javax.servlet.ServletContextEvent;
import java.sql.Connection;

public abstract class UnitOfWorkFactory {
    public static UnitOfWorkFactory instance;

    public static void setInstance(UnitOfWorkFactory $instance) {
        instance = $instance;
    }

    public abstract UnitOfWork create();
}

public final class SqlUnitOfWorkFactory extends UnitOfWorkFactory  {

    public static final SqlUnitOfWorkFactory instance = new SqlUnitOfWorkFactory();

    public UnitOfWork create() {
        return new SqlUnitOfWork();
    }
}

public interface UnitOfWorkTransaction extends AutoCloseable {
    void commit();
}

public class UnitOfWorkSqlTransaction implements UnitOfWorkTransaction {
    private final Connection connection;

    public UnitOfWorkSqlTransaction(Connection connection) {
        this.connection = connection;

        this.connection.setAutoCommit(false);
    }

    public void commit() {
        this.connection.commit();
    }

    public void close() {
        this.connection.rollback();
    }
}

public interface UnitOfWork {

    UserDAO getUserDao();

    UnitOfWorkTransaction beginTransaction();
}

public class SqlUnitOfWork implements UnitOfWork {

    private final Connection connection;

    private UnitOfWorkSqlTransaction transaction;

    public SqlUnitOfWork() {
        this.connection = ConnectionImpl.getInstance();
    }

    @Override
    public UserDAO getUserDao() {
        return new UserDAO(this.connection);
    }

    @Override
    public UnitOfWorkTransaction beginTransaction() {
       if (this.transaction == null) {
           this.transaction = new UnitOfWorkSqlTransaction(this.connection);
       }

       return this.transaction;
    }
}

public class TeacherService {
    private final UnitOfWork uow;

    public TeacherService(UnitOfWork uow) {
        this.uow = uow;
    }

    private void DeleteTeacher(Teacher teacher) {
        CourseService courseService = new CourseService(this.uow);

        List<Course> assingedCourses = courseService.getCoursesByTeacher(teacher.getId());

        try (UnitOfWorkTransaction transaction = this.uow.beginTransaction()) {
            for (Cource course: assingedCourses) {
                courseService.unassign(course);
            }

            TeacherDao teacherDao = this.uow.getTeacherDao();

            teacherDao.delete(teacher);

            transaction.commit();
        }
    }
}

public class CourseService {
    private final UnitOfWork uow;

    public CourseService(UnitOfWork uow) {
        this.uow = uow;
    }

    public void unassign(Course course) {
        CourseDao dao = this.uow.getCourseDao();

        course.setAssignedTeacher(null);

        dao.update(coruse);
    }
}


@WebListener
public static class Listener {
    public void contextInitialized(ServletContextEvent sce) {
        UnitOfWorkFactory.setInstance(SqlUnitOfWorkFactory.instance);
    }
}

*/
