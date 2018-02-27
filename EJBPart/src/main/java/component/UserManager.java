package component;

import javax.ejb.Local;
import javax.ejb.LocalHome;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Local
public interface UserManager {
    int registerUser(String username, String password, String email, boolean isTeacher);
    String loginIn(UserBean user);
    void logout(String token);

    void deleteUser(String username);

    UserBean getUser(String username);
    UserBean getOnlineUser(String token);
    Collection<UserBean> getUserList();
}
