package presentation;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class HelloTag extends SimpleTagSupport {

    private String login;
    private String locale;

    public void setLogin(String login) {
        this.login = login;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Override
    public void doTag() throws JspException, IOException {
        String welcomeMessage;
        if (locale.equals("en")) {
            if (login == null || login.equals("")) {
                welcomeMessage = "Hello, guest !";
            } else {
                welcomeMessage = "Hello, " + login + " !";
            }
            getJspContext().getOut().print(welcomeMessage);
            return;
        }
        if (locale.equals("uk")) {
            if (login == null || login.equals("")) {
                welcomeMessage = "Привіт, гість !";
            } else {
                welcomeMessage = "Привіт, " + login + " !";
            }
            getJspContext().getOut().print(welcomeMessage);
        }
    }
}
