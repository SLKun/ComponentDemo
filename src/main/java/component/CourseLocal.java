package component;

import javax.ejb.Local;

@Local
public interface CourseLocal {
    void addCourse(String courseName, UserLocal teacher, String location);

    String getCourseName();
    void setCourseName(String courseName);
    UserLocal getTeacher();
    void setTeacher(UserLocal teacher);
    String getLocation();
    void setLocation(String location);
}
