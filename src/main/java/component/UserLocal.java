package component;

import javax.ejb.Local;
import java.util.List;

@Local
public interface UserLocal {
    boolean register(String username, String password, String email, UserEJB.UserType type);
    boolean checkPassword(String password);
    void modifyUser(String password, String email);
    void addCourse(CourseLocal course);
    void deleteCourse(CourseLocal course);

    String getUsername();
    String getEmail();
    String getUserType();
    List<CourseLocal> getCourseList();
}
