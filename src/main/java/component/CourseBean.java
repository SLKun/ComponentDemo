package component;

import javax.ejb.Local;

@Local
public interface CourseBean {
    String getId();
    void setId(String id);
    String getCourseName();
    void setCourseName(String courseName);
    UserBean getTeacher();
    void setTeacher(UserBean teacher);
    String getLocation();
    void setLocation(String location);
}
