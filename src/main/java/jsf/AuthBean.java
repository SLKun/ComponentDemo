package jsf;

import component.*;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

@ManagedBean
public class AuthBean {
    @EJB
    private UserManager userManager;
    @EJB
    private CookieUtilLocal cookieUtil;
    @EJB
    private ConstantUtilLocal constantUtil;

    private String username;
    private String password;
    private String email;
    private Integer isTeacher;
    private String result;

    public String register() {
        // Check Fields
        if (username == null || username.isEmpty()) {
            result = "用户名为空!";
            return "back";
        }
        if (password == null || password.length() < 6) {
            result = "密码不合法!";
            return "back";
        }
        if (email == null || email.isEmpty()) {
            result = "邮箱为空!";
            return "back";
        }
        // Register
        if (userManager.registerUser(username, password, email, isTeacher.equals(1)) != 0) {
            result = "用户名已被注册!";
            return "back";
        } else {
            result = "用户注册成功!";
            return "success";
        }
    }

    public String login() {
        UserBean user = userManager.getUser(username);
        if (user == null || !user.checkPassword(password)) {
            result = "登录失败!";
            return "back";
        }
        String token = userManager.loginIn(user);
        cookieUtil.setCookie("token", token);
        cookieUtil.setSession("token", token);
        return constantUtil.retrievePageName(user);
    }

    public String logout() {
        String token = cookieUtil.readCookie("token");
        userManager.logout(token);
        cookieUtil.setCookie("token", "");
        return "logout";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getIsTeacher() {
        return isTeacher;
    }

    public void setIsTeacher(Integer isTeacher) {
        this.isTeacher = isTeacher;
    }
}