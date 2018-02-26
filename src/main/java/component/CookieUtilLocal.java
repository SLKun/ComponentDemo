package component;

import javax.ejb.Local;

@Local
public interface CookieUtilLocal {
    /**
     * Set Cookie For Response
     * @param name Cookie Name
     * @param value  Cookie Value for specific name
     */
    void setCookie(String name, String value);

    /**
     * Read Cookie Value for specific name
     * @param name Cookie Name
     * @return Cookie Value
     */
    String readCookie(String name);
}
