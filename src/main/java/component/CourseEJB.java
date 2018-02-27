package component;

import javax.ejb.Stateful;

@Stateful
public class CourseEJB implements CourseLocal{
    private String courseName;
    private UserLocal teacher;
    private String location;

    public void addCourse(String courseName, UserLocal teacher, String location){
        this.courseName = courseName;
        this.teacher = teacher;
        this.location =  location;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public UserLocal getTeacher() {
        return teacher;
    }

    public void setTeacher(UserLocal teacher) {
        this.teacher = teacher;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
