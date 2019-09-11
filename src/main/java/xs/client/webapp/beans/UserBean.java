
package xs.client.webapp.beans;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import xs.client.messages.MessagesUtil;
import xs.client.services.UserClient;

import static xs.client.validation.QueryValidation.validateEmail;
import static xs.client.validation.QueryValidation.validatePassword;
import static xs.client.webapp.controller.LoginController.checkEmail;


/**
 * UserBean object for webClient to get input from JSF page for User handling
 * 
 * @author saukin
 */
@Named("userBean")
@SessionScoped
public class UserBean implements Serializable {
    
    private int user_id;
    
    @NotNull
    @Size(min=2,max=25, message = "email must be between 2 and 25 characters")
    private String email;
    
    @NotNull
    @Size(min=2,max=25, message = "password must be between 2 and 25 characters")
    private String password;
    
    private String action = "signUp";
    
    /**
     *
     */
    public UserBean() {
        
    }

    /**
     *
     * @param user_id
     */
    public UserBean(int user_id) {
        this.user_id = user_id;
    }

    /**
     *
     * @return
     */
    public int getUser_id() {
        return user_id;
    }

    /**
     *
     * @param user_id
     */
    public void setUser_id(int user_id) {
        this.user_id = user_id;
        
    }

    /**
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * sets the email and verify if email already exists or it is not empty
     * sets the action field to switch the signUp/logIn options or do nothing
     * in case email is empty
     * 
     * @param email
     */
    public void setEmail(String email) {
        if (validateEmail(email.trim())) {
            if (!checkEmail(email)) {
                action = "signUp";
            } else action = "logIn";
            this.email = email;
        } else {action = "noWayBuddy";}
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        if (validatePassword(password.trim())) {
            this.password = password;
        } else 
            this.password = password;
    }

    /**
     *
     * @return
     */
    public String getAction() {
        return action;
    }

    /**
     *
     * @return
     */
    public String idToString() {
        return String.valueOf(user_id);
    }
    
    
        
        
    
    
}
