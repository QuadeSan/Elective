package dataBaseLayer.dao;

public abstract class DAOFactory {

    private static DAOFactory instance;

    public static final int MYSQL = 1;

    public abstract AdministratorDAO getAdministratorDAO();

    public abstract AssignmentDAO getAssignmentDAO();

    public abstract CourseDAO getCourseDAO();

    public abstract StudentDAO getStudentDAO();

    public abstract TeacherDAO getTeacherDAO();

    public static DAOFactory getInstance() {
        return instance;
    }

    public static void setInstance(DAOFactory factoryImlp) {
        instance = factoryImlp;
    }

}
