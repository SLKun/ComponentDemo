package component;

import javax.ejb.Local;

@Local
public interface ConstantUtilLocal {
    String retrievePageName(UserBean user);
    String retrievePromptName(UserBean user);
}
