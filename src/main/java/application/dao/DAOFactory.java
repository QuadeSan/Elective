package application.dao;

/**
 * Abstract factory for setting implementation
 * of DAO type
 * {@link #setInstance(DAOFactory) Set Instance} method
 * used to set current implementation. Method should be called
 * when application is starting
 * {@link #getInstance() Get Instance} main method
 * used to get instance of DAOFactory
 */
public abstract class DAOFactory {

    private static DAOFactory instance;

    public abstract AdministratorDAO getAdministratorDAO();

    public abstract AssignmentDAO getAssignmentDAO();

    public abstract CourseDAO getCourseDAO();

    public abstract StudentDAO getStudentDAO();

    public abstract TeacherDAO getTeacherDAO();

    public static DAOFactory getInstance() {
        return instance;
    }

    public static void setInstance(DAOFactory factoryImpl) {
        instance = factoryImpl;
    }

}
