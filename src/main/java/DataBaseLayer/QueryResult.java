package DataBaseLayer;

import java.util.ArrayList;
import java.util.List;

public class QueryResult {

    private boolean success;
    private List<String> exceptions;

    public QueryResult() {
        success = true;
        exceptions = new ArrayList<>();
    }

    public void addException(Exception ex) {
        exceptions.add(ex.toString());
    }

    public boolean getResult() {
        return exceptions.isEmpty();
    }

    public String getException() {
        if (exceptions.get(0).contains("Login already exist")){
            return "Login already exist";
        }
        if (exceptions.get(0).contains("Title already exist")) {
            return "Course with current title already exist";
        }
        if (exceptions.get(0).contains("teachers_assignments.PRIMARY")) {
            return "Teacher already assigned to current course!";
        }
        if (exceptions.get(0).contains("teachers_assignments.courses_course_id")) {
            return "Current course already have assigned teacher!";
        }
        if (exceptions.get(0).contains("foreign key constraint fails")) {
            return "Either the course or the teacher does not exist!";
        }
        if (exceptions.get(0).contains("Deleting course failed")){
            return "Course with current ID doest not exist";
        }
        return "Unhandled exception";
    }
}
