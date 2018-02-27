package component;

import javax.ejb.Stateless;

@Stateless
public class ConstantUtil implements ConstantUtilLocal{
    public String retrievePageName(UserBean user){
        if(user.getUserType().equals(UserBean.UserType.ADMIN)){
            return "admin";
        }else if(user.getUserType().equals(UserBean.UserType.TEACHER)){
            return "teacher";
        }else{
            return "student";
        }
    }

    public String retrievePromptName(UserBean user){
        if(user.getUserType().equals(UserBean.UserType.ADMIN)){
            return "管理员";
        }else if(user.getUserType().equals(UserBean.UserType.TEACHER)){
            return "教师帐号";
        }else{
            return "学生账号";
        }
    }
}
