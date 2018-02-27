package component;

import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import java.util.Map;

@Stateless
public class CookieUtil implements CookieUtilLocal {
    public void setCookie(String name, String value){
        FacesContext.getCurrentInstance().getExternalContext().addResponseCookie(name, value, null);
    }

    public String readCookie(String name){
        return String.valueOf(((Cookie)(this.readCookieMap().get(name))).getValue());
    }

    public Map<String, Object> readCookieMap(){
        return FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap();
    }

    @Override
    public void setSession(String name, String value) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(name, value);
    }

    @Override
    public String readSession(String name) {
        return String.valueOf(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(name));
    }
}
