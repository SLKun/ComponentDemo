package jsf;

import component.CookieUtilLocal;
import component.CourseManager;
import component.UserLocal;
import component.UserManager;

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
        UserLocal user = userManager.getUser(username);
        if(user.checkPassword(oldPassword)){
            user.modifyUser(newPassword, newEmail);
            return "success";
        }
        return "back";
    }

    public UserLocal getLoginedUser() {
        String token = cookieUtil.readCookie("token");
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
