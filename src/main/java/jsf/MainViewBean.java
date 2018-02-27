package jsf;

import component.*;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import java.util.*;

@ManagedBean
public class MainViewBean {
    @EJB
    private UserManager userManager;
    @EJB
    private CourseManager courseManager;
    @EJB
    private CookieUtilLocal cookieUtil;
    @EJB
    private ConstantUtilLocal constantUtil;

    // State Bean
    private UserBean loginUser;
    // Input Parameter
    private String oldPassword;
    private String newPassword;
    private String newEmail;
    private String courseName;
    private String location;
    // Message
    private String result;

    /** User Management **/
    public String modifyUser(String username){
        if(username == null || username.isEmpty()){
            return constantUtil.retrievePageName(getLoginUser());
        }
        UserBean user = userManager.getUser(username);
        if(user != null && user.checkPassword(oldPassword)){
            user.modifyUser(newPassword, newEmail);
            return constantUtil.retrievePageName(getLoginUser());
        }else {
            result = "原密码错误!";
            return "back";
        }
    }

    public void deleteUser(String username){
        if(username != null && !username.isEmpty()){
            if(username.equals("admin")){
                result = "不允许删除管理员帐户!";
            }else{
                userManager.deleteUser(username);
            }
        }
    }

    public Collection<UserBean> getUserList() {
        List<UserBean> list = new ArrayList<>(userManager.getUserList());
        Collections.sort(list, new Comparator<UserBean>() {
            @Override
            public int compare(UserBean o1, UserBean o2) {
                if(o1.getUserType().compareTo(o2.getUserType()) != 0){
                    return o1.getUserType().compareTo(o2.getUserType());
                }else{
                    return o1.getUsername().compareTo(o2.getUsername());
                }
            }
        });
        return list;
    }

    /** Course Management **/
    public void deleteCourse(String courseId){
        CourseBean course = courseManager.getCourse(courseId);
        // delete teacher-course
        UserBean teacher = course.getTeacher();
        teacher.deleteCourse(course);
        // delete course
        courseManager.deleteCourse(courseId);
    }

    public Collection<CourseBean> getCourseList() {
        List<CourseBean> list = new ArrayList<>(courseManager.getCourseList());
        return list;
    }

    public Collection<CourseBean> getUserCourses(){
        courseManager.getCourseList();
        List<CourseBean> list = new ArrayList<>(getLoginUser().getCourseList());
        return list;
    }

    public void addCourse(){
        courseManager.addCourse(courseName, getLoginUser(), location);
    }

    public UserBean getLoginUser() {
        if(loginUser == null){
            String token = cookieUtil.readCookie("token");
            loginUser = userManager.getOnlineUser(token);
            if(loginUser == null){
                token = cookieUtil.readSession("token");
                loginUser = userManager.getOnlineUser(token);
            }
        }
        return loginUser;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    public String getNewEmail() {
        return getLoginUser().getEmail();
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void test(String username) {
        System.out.println("TEST" + username);
    }
}
