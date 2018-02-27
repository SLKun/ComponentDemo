package component;

import javax.ejb.Local;
import java.util.Collection;

@Local
public interface CourseManager {
    void addCourse(String courseName, UserBean teacher, String location);
    void deleteCourse(String courseId);
    CourseBean getCourse(String courseId);
    Collection<CourseBean> getCourseList();
}
