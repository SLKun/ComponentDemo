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

    private String result;

    private String oldPassword;
    private String newPassword;
    private String newEmail;

    public void test(String username) {
        System.out.println("TEST" + username);
        userManager.deleteUser(username);
    }

    public String modifyUser(String username){
        if(username == null || username.isEmpty()){
            return retrievePageName();
        }
        UserLocal user = userManager.getUser(username);
        if(user != null && user.checkPassword(oldPassword)){
            user.modifyUser(newPassword, newEmail);
            return retrievePageName();
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

    public UserLocal getLoginedUser() {
        String token = cookieUtil.readCookie("token");
        if(token == null || token.isEmpty()){
            token = cookieUtil.readSession("token");
        }
        return userManager.getOnlineUser(token);
    }

    public Collection<UserLocal> getUserList() {
        List<UserLocal> list = new ArrayList<>(userManager.getUserList());
        Collections.sort(list, new Comparator<UserLocal>() {
            @Override
            public int compare(UserLocal o1, UserLocal o2) {
                if(o1.getUserType().compareTo(o2.getUserType()) != 0){
                    return -o1.getUserType().compareTo(o2.getUserType());
                }else{
                    return o1.getUsername().compareTo(o2.getUsername());
                }
            }
        });
        return list;
    }

    public Collection<CourseLocal> getCourseList() {
        List<CourseLocal> list = new ArrayList<>(courseManager.getCourseList());
        return list;
    }

    public Collection<CourseLocal> getCourseListForUser(String username) {
        List<CourseLocal> list = new ArrayList<>(courseManager.getCourseList());
        return list;
    }

    public void addCourseForUser(String username){
//        userManager.getUser(username).addCourse();
    }

    @SuppressWarnings("Duplicates")
    private String retrievePageName(){
        UserLocal user = getLoginedUser();
        if(user.getUserType().equals("管理员")){
            return "admin";
        }else if(user.getUserType().equals("教师帐号")){
            return "teacher";
        }else{
            return "student";
        }
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
        return getLoginedUser().getEmail();
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
}
