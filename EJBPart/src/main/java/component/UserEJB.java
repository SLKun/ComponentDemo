package component;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.registry.infomodel.User;
import java.util.*;

@Stateless
public class UserEJB implements UserManager, UserBean {
    // Manager Property
    private static final Map<String, UserBean> userMap = new HashMap<>();
    private static final Map<String, UserBean> onlineMap = new HashMap<>();
    // Pre-defined Users
    private static final String[] admin = {"admin", "123456", "admin@admin.com"};
    private static final String[][] teachers = {
            {"teacher1", "123456", "teacher1@teacher.com"},
            {"teacher2", "123456", "teacher2@teacher.com"}
    };
    private static final String[][] students = {
            {"student1", "123456", "student1@student.com"},
            {"student2", "123456", "student2@student.com"}
    };
    // Field
    private String username;
    private String password;
    private String email;
    private UserType userType;
    private List<CourseBean> courseList = new ArrayList<>();
    // EJBs
    @EJB
    private ConstantUtilLocal constantUtil;
    @EJB
    private CourseManager courseManager;

    /**
     * Constructor
     **/
    public UserEJB() {
        // init admin
        UserBean adminUser = new UserEJB(admin[0], admin[1], admin[2], UserType.ADMIN);
        userMap.put(adminUser.getUsername(), adminUser);
        // init teacher
        for (String[] teacher : teachers) {
            UserBean u = new UserEJB(teacher[0], teacher[1], teacher[2], UserType.TEACHER);
            userMap.put(u.getUsername(), u);
        }
        // init student
        for (String[] student : students) {
            UserBean u = new UserEJB(student[0], student[1], student[2], UserType.STUDENT);
            userMap.put(u.getUsername(), u);
        }
    }

    public UserEJB(String username, String password, String email, UserType type) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.userType = type;
    }

    /**
     * Operator
     **/
    @Override
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    @Override
    public void modifyUser(String password, String email) {
        this.password = password;
        this.email = email;
    }

    @Override
    public void addCourse(CourseBean course) {
        courseList.add(course);
    }

    @Override
    public void deleteCourse(CourseBean course) {
        courseList.remove(course);
    }

    /**
     * Manager Method
     **/
    @Override
    public int registerUser(String username, String password, String email, boolean isTeacher) {
        if (userMap.containsKey(username)) {
            return -1;
        }
        UserEJB.UserType userType = isTeacher ? UserType.TEACHER : UserType.STUDENT;
        UserBean user = new UserEJB(username, password, email, userType);
        userMap.put(username, user);
        return 0;
    }

    @Override
    public String loginIn(UserBean user) {
        String token = UUID.randomUUID().toString().replace("-", "");
        onlineMap.put(token, user);
        return token;
    }

    @Override
    public void logout(String token) {
        onlineMap.remove(token);
    }

    @Override
    public void deleteUser(String username) {
        UserBean user = getUser(username);
        if(!user.getCourseList().isEmpty()){
            List<CourseBean> courses = new ArrayList<>(user.getCourseList());
            for(CourseBean course : courses){
                getCourseManager().deleteCourse(course.getId());
            }
        }
        userMap.remove(username);
        Iterator<Map.Entry<String, UserBean>> it = onlineMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, UserBean> entry = it.next();
            if (entry.getValue().getUsername().equals(username)) {
                it.remove();
            }
        }
    }

    @Override
    public UserBean getUser(String username) {
        return userMap.get(username);
    }

    @Override
    public UserBean getOnlineUser(String token) {
        return onlineMap.get(token);
    }

    @Override
    public Collection<UserBean> getUserList() {
        return userMap.values();
    }

    private CourseManager getCourseManager(){
        if(courseManager == null){
            try {
                courseManager = InitialContext.doLookup("java:global/CourseEJB");
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
        return courseManager;
    }

    /**
     * Field Getter and Setter
     **/
    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public UserType getUserType() {
        return userType;
    }

    @Override
    public String getUserTypeStr() {
        if(constantUtil == null){
            try {
                constantUtil = InitialContext.doLookup("java:global/ConstantUtil");
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
        return constantUtil.retrievePromptName(this);
    }

    @Override
    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public List<CourseBean> getCourseList() {
        return courseList;
    }
}
