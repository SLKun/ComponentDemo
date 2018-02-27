package component;

import javax.ejb.Local;
import java.util.List;

@Local
public interface UserBean {
    // Enum Type
    enum UserType{
        ADMIN, TEACHER, STUDENT
    }
    // Field Getter and Setter
    String getUsername();
    void setUsername(String username);
    String getPassword();
    void setPassword(String password);
    String getEmail();
    void setEmail(String email);
    UserType getUserType();
    String getUserTypeStr();
    void setUserType(UserType userType);
    List<CourseBean> getCourseList();
    // Operator
    boolean checkPassword(String password);
    void modifyUser(String password, String email);
    void addCourse(CourseBean course);
    void deleteCourse(CourseBean course);
}
