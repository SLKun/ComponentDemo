package component;

import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.*;

@Stateless
public class CourseEJB implements CourseManager, CourseBean {
    // Pre-defined Courses
    private static final String[][] courses = {
            {"微积分", "东九B202"},
            {"线性代数", "西十二S202"}
    };
    // Manager Property
    private Map<String, CourseBean> courseMap = new HashMap<>();
    // Field
    private String id;
    private String courseName;
    private UserBean teacher;
    private String location;
    private List<UserBean> students = new ArrayList<>();

    /**
     * Constructor
     */
    public CourseEJB() throws NamingException {
        UserManager userManager = InitialContext.doLookup("java:global/UserEJB");
        UserBean teacher1 = userManager.getUser("teacher1");
        CourseBean course1 = new CourseEJB(courses[0][0], teacher1, courses[0][1]);
        courseMap.put(course1.getId(), course1);
        teacher1.addCourse(course1);
        UserBean teacher2 = userManager.getUser("teacher2");
        CourseBean course2 = new CourseEJB(courses[1][0], teacher2, courses[1][1]);
        courseMap.put(course2.getId(), course2);
        teacher2.addCourse(course2);
    }

    public CourseEJB(String courseName, UserBean teacher, String location) {
        this.id = UUID.randomUUID().toString().replace("-", "");
        this.courseName = courseName;
        this.teacher = teacher;
        this.location = location;
    }

    /**
     * Operator
     **/
    @Override
    public void modifyCourse(UserBean teacher, String location) {
        this.teacher = teacher;
        this.location = location;
    }

    @Override
    public void addStudent(UserBean student) {
        students.add(student);
    }

    @Override
    public void deleteStudent(UserBean student) {
        students.remove(student);
    }

    /**
     * Manager Method
     **/
    @Override
    public CourseBean getCourse(String courseId) {
        return courseMap.get(courseId);
    }

    @Override
    public void addCourse(String courseName, UserBean teacher, String location) {
        CourseBean course = new CourseEJB(courseName, teacher, location);
        courseMap.put(course.getId(), course);
        teacher.addCourse(course);
    }

    @Override
    public void deleteCourse(String courseId) {
        CourseBean course = getCourse(courseId);
        // delete teacher-course
        UserBean teacher = course.getTeacher();
        teacher.deleteCourse(course);
        // remove from map
        courseMap.remove(courseId);
    }

    public Collection<CourseBean> getCourseList() {
        return courseMap.values();
    }

    /**
     * Field Getter and Setter
     **/
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public UserBean getTeacher() {
        return teacher;
    }

    public void setTeacher(UserBean teacher) {
        this.teacher = teacher;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<UserBean> getStudents() {
        return students;
    }
}
