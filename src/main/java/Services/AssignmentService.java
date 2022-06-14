package Services;

import DataBaseLayer.QueryResult;

public interface AssignmentService {

    QueryResult assignTeacherToCourse(int course_id, int teacher_id);

    void unassignTeacherFromCourse(int course_id, int teacher_id);

    QueryResult assignStudentToCourse(int course_id, int student_id);

    void unassignStudentFromCourse(int course_id, int student_id);

}
