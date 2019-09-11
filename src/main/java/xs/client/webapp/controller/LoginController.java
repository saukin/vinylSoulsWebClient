
package xs.client.webapp.controller;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import xs.client.entities.User;
import xs.client.messages.MessagesUtil;
import xs.client.services.AlbumClient;
import xs.client.services.UserClient;
import static xs.client.validation.QueryValidation.validateEmail;
import static xs.client.validation.QueryValidation.validatePassword;
import xs.client.webapp.beans.UserBean;

/**
 * LoginController bean
 * 
 * @author saukin
 */
@Named("loginController")
@RequestScoped
public class LoginController {
    
    @Inject
    UserClient userClient;
    
    @Inject
    UserBean userBean;
    
    @Inject
    ListManager listManager;
    
    @Inject
    AlbumClient albumClient;
    
    String outcome = "index";
    
    int user_id;
    
    /**
     * checks if an email already exists
     * @param email
     * @return boolean exists or not
     */
    public static boolean checkEmail(String email) {
        try {
            ClientConfig config = new DefaultClientConfig();
            Client client = Client.create(config);
            WebResource wr = client.resource(UriBuilder.fromUri("http://localhost:8080/XS_PROJECT/webresources/albums/").build());
            User u = wr.path("login/"+email).type(MediaType.TEXT_PLAIN)
                    .accept(MediaType.APPLICATION_JSON).get(User.class);
            if (u!=null) return true;
            } catch (ClientHandlerException | UniformInterfaceException e) {
                return false;
            }
        return false;
    }
    
    /**
     * process login or not
     * @return String value of a page to render
     * @throws Exception
     */
    public String login() throws Exception {

        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

        FacesMessage message;
        boolean loggedIn;
        
        if (userBean.getPassword().equals(userClient.getUserByEmail(userBean.getEmail()).getPassword())) {
            loggedIn = true;
            userBean.setUser_id(userClient.getUserByEmail(userBean.getEmail()).getUser_id());
            user_id = userBean.getUser_id();
            message = MessagesUtil.getMessage(
                    "xs.client.bundles.messages", "welcome", new Object[]{userBean.getEmail()});
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
//            actionManager.setAlbum(albumClient.getAlbum);
            listManager.setAlbumList(albumClient.getActiveAlbumsByUserID(String.valueOf(user_id), true));
            if (listManager.getAlbumList().size()>0) {
                listManager.setSelectedAlbum(listManager.getAlbumList().get(0));
                listManager.setAlbumBean();
            }
            listManager.setSearchId(true);
            outcome = "myPage";
            listManager.setPage(outcome);
            listManager.setUpcName("");
        } else {
            loggedIn = false;
            message = MessagesUtil.getMessage(
                    "xs.client.bundles.messages", "loginerror", new Object[]{userBean.getEmail()});
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            outcome = "index";
            userBean.setPassword("");
        }
        session.setAttribute("loggedIn", loggedIn);
        FacesContext.getCurrentInstance().addMessage(null, message);
        
        return outcome;
    }
    
    /**
     * process logout
     * @return
     */
    public String logOut() {
        
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

        session.setAttribute("loggedIn", false);
        
        listManager.setAlbumList(albumClient.getAlbums());
        listManager.setSearchId(false);
        outcome = "index";
        listManager.setPage(outcome);
        
        return outcome;
    }
    
    /**
     * Signs a user up
     * @param email - entered email 
     * @param password - entered password
     * @return String value of a page to render
     * @throws Exception
     */
    public String signUp(String email, String password) throws Exception {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        FacesMessage message;
        if (validateEmail(email) && validatePassword(password)) {
            userClient.createUser(email, password);
            message = MessagesUtil.getMessage(
                    "xs.client.bundles.messages", "signedUp", new Object[]{email});
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        } else {
            message = MessagesUtil.getMessage(
                    "xs.client.bundles.messages", "emptyPassword", new Object[]{email});
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
        userBean.setPassword("");
        
        return "index";
    }
    
    /**
     * chooses what to do by a button : signUp or login
     * @return  String value of a page to render
     * @throws Exception
     */
    public String chooseSignupLogin() throws Exception {
        try{switch (userBean.getAction()) {
            case "signUp":
                return signUp(userBean.getEmail(), userBean.getPassword());
            case "logIn":
                return login(); 
            default:
                return "index"; 
        }
        } catch (Exception e) {
            System.out.println("HERERERERER");
            return "index";
        }
    }
    
}
    
    

