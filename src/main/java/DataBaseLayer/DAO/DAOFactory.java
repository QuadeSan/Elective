package DataBaseLayer.DAO;

public abstract class DAOFactory {

    private static DAOFactory instance;

    public static final int MYSQL = 1;

    public abstract AdministratorDAO getAdministratorDAO();

    public abstract AssignmentDAO getAssigmentDAO();

    public abstract CourseDAO getCourseDAO();

    public abstract StudentDAO getStudentDAO();

    public abstract TeacherDAO getTeacherDAO();

    public abstract UserDAO getUserDAO();

    public static DAOFactory getInstance() {
        return instance;
    }

    public static void setInstance(DAOFactory factoryImlp) {
        instance = factoryImlp;
    }


//    public static DAOFactory getDAOFactory(int factoryImlp){
//        switch (factoryImlp) {
//            case MYSQL:
//                return new MySQLDAOFactory();
//            default:
//                return null;
//        }
//    }
}
