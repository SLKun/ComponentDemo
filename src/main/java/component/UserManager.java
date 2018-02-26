package component;

import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Stateless
public class UserManager {
    private static final String[] admin = {"admin", "123456", "admin@admin.com"};
    private static final String[][] teachers = {
            {"teacher1", "123456", "teacher1@teacher.com"},
            {"teacher2", "123456", "teacher2@teacher.com"}
    };
    private static final String[][] students = {
            {"student1", "123456", "student1@student.com"},
            {"student2", "123456", "student2@student.com"}
    };
    private Map<String, UserLocal> userMap = new HashMap<>();
    private Map<String, UserLocal> onlineMap = new HashMap<>();

    public UserManager() {
        try {
            // init admin
            UserLocal user = InitialContext.doLookup("java:global/UserEJB");
            user.register(admin[0], admin[1], admin[2], UserEJB.UserType.ADMIN);
            userMap.put(user.getUsername(), user);
            // init teacher
            for (String[] teacher : teachers) {
                user = InitialContext.doLookup("java:global/UserEJB");
                user.register(teacher[0], teacher[1], teacher[2], UserEJB.UserType.TEACHER);
                userMap.put(user.getUsername(), user);
            }
            // init student
            for (String[] student : students) {
                user = InitialContext.doLookup("java:global/UserEJB");
                user.register(student[0], student[1], student[2], UserEJB.UserType.STUDENT);
                userMap.put(user.getUsername(), user);
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public int registerUser(String username, String password, String email, boolean isTeacher) {
        if (!userMap.containsKey(username)) {
            UserEJB.UserType userType = isTeacher ? UserEJB.UserType.TEACHER : UserEJB.UserType.STUDENT;
            try {
                UserLocal user = InitialContext.doLookup("java:global/UserEJB");
                user.register(username, password, email, userType);
                userMap.put(username, user);
            } catch (NamingException e) {
                e.printStackTrace();
            }
            return 0;
        } else {
            return -1;
        }
    }

    public String loginIn(UserLocal user) {
        String token = UUID.randomUUID().toString().replace("-", "");
        onlineMap.put(token, user);
        return token;
    }

    public void logout(String token) {
        onlineMap.remove(token);
    }

    public void deleteUser(String username) {
        userMap.remove(username);
    }

    public Collection<UserLocal> getUserList() {
        return userMap.values();
    }

    public UserLocal getUser(String username) {
        return userMap.get(username);
    }

    public UserLocal getOnlineUser(String token) {
        return onlineMap.get(token);
    }
}
