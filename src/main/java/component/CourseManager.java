package component;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Stateless
public class CourseManager {
    @EJB
    private UserManager userManager;
    private Map<String, CourseLocal> courseMap;
    private static final String[][] courses = {
            {"微积分", "东九B202"},
            {"线性代数", "西十二S202"}
    };

    public Collection<CourseLocal> getCourseList() {
        if(courseMap == null){
            courseMap = new HashMap<>();
            userManager.registerUser("math", "123456", "math@teacher.com", true);
            try{
                for(String[] c : courses){
                    CourseLocal course = InitialContext.doLookup("java:global/CourseEJB");
                    course.addCourse(c[0], userManager.getUser("math"), c[1]);
                    courseMap.put(c[0], course);
                    userManager.getUser("math").addCourse(course);
                }
            }catch(NamingException e){
                e.printStackTrace();
            }
        }
        return courseMap.values();
    }
}
