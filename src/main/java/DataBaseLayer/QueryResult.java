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
        if (exceptions.get(0).contains("users.login")) {
            return "Login already exist";
        }
        if (exceptions.get(0).contains("Duplicate entry")) {
            return "Assignment already exist!";
        }
        if (exceptions.get(0).contains("foreign key constraint fails")) {
            return "Either the course or the teacher does not exist!";
        }
        return "Unhandled exception";
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < exceptions.size(); i++) {
//            if (i > 0) {
//                sb.append(", ");
//            }
//            sb.append(exceptions.get(i));
//        }
//        return sb.toString();
    }
}
