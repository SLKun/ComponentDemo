package component;

import javax.ejb.Stateful;
import java.util.ArrayList;
import java.util.List;


@Stateful
public class UserEJB implements UserLocal {
    public enum UserType{
        ADMIN, TEACHER, STUDENT
    }

    private String username;
    private String password;
    private String email;
    private UserType userType;
    private List<CourseLocal> courseList = new ArrayList<>();

    @Override
    public boolean register(String username, String password, String email, UserType type) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.userType = type;
        return true;
    }

    @Override
    public boolean checkPassword(String password){
        return this.password.equals(password);
    }

    @Override
    public void modifyUser(String password, String email) {
        this.password = password;
        this.email = email;
    }

    @Override
    public void addCourse(CourseLocal course) {
        courseList.add(course);
    }

    @Override
    public void deleteCourse(CourseLocal course) {
        courseList.remove(course);
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getUserType() {
        switch (userType){
            case ADMIN:
                return "管理员";
            case TEACHER:
                return "教师帐号";
            case STUDENT:
                return "学生帐号";
        }
        return "";
    }

    public List<CourseLocal> getCourseList() {
        return courseList;
    }
}
