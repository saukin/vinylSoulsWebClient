/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xs.client.services;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import xs.client.entities.User;
import static xs.client.validation.QueryValidation.validateEmail;
import static xs.client.validation.QueryValidation.validatePassword;
import static xs.client.validation.QueryValidation.validateUserId;

/**
 *
 * @author admin
 */
@Named("userClient")
@RequestScoped
public class UserClient {
    
//    @Inject
//    UserBean userBean;
    
    ClientConfig config = new DefaultClientConfig();
    Client client = Client.create(config);
    
    WebResource wr = client.resource(UriBuilder.fromUri("http://localhost:8080/XS_PROJECT/webresources/albums/").build());

    /**
     *
     */
    public UserClient() {
    }
   
    /**
     *
     * @param email
     * @param password
     * @return
     * @throws Exception
     */
    public User createUser(String email, String password) throws Exception {
        if (!validateEmail(email)) {
            throw new Exception("email value is not valid");
        }
        if (!validatePassword(password)) {
            throw new Exception("email value is not valid");
        }
        User newUser = new User();
        
        newUser.setEmail(email);
        newUser.setPassword(password);
        
        wr.path("signup").type(MediaType.APPLICATION_JSON).
                    accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, newUser);
        
        
        return getUserByEmail(email);
    }
    
    /**
     *
     * @param userId
     * @return
     * @throws Exception
     */
    public User getUserById(String userId) throws Exception {
        if (!validateUserId(userId)) {
            throw new Exception("userId value is not valid");
        }
        WebResource newWr = client.resource(UriBuilder.fromUri("http://localhost:8080/XS_PROJECT/webresources/albums/userId/").build());
        return newWr.path(userId).type(MediaType.TEXT_PLAIN).accept(MediaType.APPLICATION_JSON).get(User.class);
        
    }
    
    /**
     *
     * @param email
     * @return
     * @throws Exception
     */
    public User getUserByEmail(String email) throws Exception {
//        if (!validateEmail(email)) {
//            throw new Exception("email value is not valid");
//        }
        WebResource newWr = client.resource(UriBuilder.fromUri("http://localhost:8080/XS_PROJECT/webresources/albums/login/"+email).build());
        return newWr.type(MediaType.TEXT_PLAIN).accept(MediaType.APPLICATION_JSON).get(User.class);
        
    }
    
    
    
}
