import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class HelloTag extends SimpleTagSupport {

    private String login;

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public void doTag() throws JspException, IOException {
        String welcomeMessage;
        if (login == null) {
            welcomeMessage = "Hello guest !";
        } else {
            welcomeMessage = "Hello " + login + " !";
        }
        getJspContext().getOut().print(welcomeMessage);
    }
}
