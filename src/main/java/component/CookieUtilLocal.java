package component;

import javax.ejb.Local;

@Local
public interface CookieUtilLocal {
    /**
     * Set Cookie For Response
     */
    void setCookie(String name, String value);
    /**
     * Read Cookie Value for specific name
     */
    String readCookie(String name);

    /**
     * Set Session
     */
    void setSession(String name, String value);

    /**
     * Read Session
     */
    String readSession(String name);
}
