
package xs.client.services;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import xs.client.entities.Album;
import xs.client.entities.User;
import static xs.client.validation.BeanValidation.validateAlbum;
import static xs.client.validation.QueryValidation.validateEntry;
import static xs.client.validation.QueryValidation.validateUPC;
import static xs.client.validation.QueryValidation.validateUserId;

/**
 * Client bean for Album entity class
 * 
 * @author saukin
 */
@Named("albumClient")
@SessionScoped
public class AlbumClient implements Serializable {

    ClientConfig config = new DefaultClientConfig();

    Client client = Client.create(config);
    
    WebResource wr = client.resource(UriBuilder.fromUri("http://localhost:8080/XS_PROJECT/webresources/albums/").build());
    
    /**
     * Edits album instance in service
     * 
     * @param a - Album entity bean that keeps data for editing Album in service
     * @return - Album object of edited album
     * @throws Exception
     */
    public Album editAlbum(Album a) throws Exception {
        if (!validateAlbum(a)) {
            throw new Exception("invalid Album bean");
        }
        User newUser = new User();
        Album newAlbum = new Album();
        newUser.setUser_id(a.getUser_ownership().getUser_id());
        newAlbum.setEntry(a.getEntry());
        newAlbum.setAlbum_name(a.getAlbum_name());
        newAlbum.setUpc_code(a.getUpc_code());
        newAlbum.setPressing_year(a.getPressing_year());
        newAlbum.setArtist_group(a.getArtist_group());
        newAlbum.setCondition_state(a.getCondition_state());
        newAlbum.setNotes(a.getNotes());
        newAlbum.setUser_ownership(newUser);
        newAlbum.setActive(true);

        ClientResponse cr = wr.path("edit").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(ClientResponse.class, newAlbum);
        
        return getAlbumByEntry(String.valueOf(newAlbum.getEntry()));
    }
    
    /**
     * Creates album instance in service
     * 
     * @param userId - user id value for creating a new album
     * @param a - Album entity bean that keeps data for editing Album in service
     * @return Album object of created album
     * @throws Exception
     */
    public Album createAlbum(String userId, Album a) throws Exception {
        if (!validateUserId(userId)) {
            throw new Exception("userId value is not valid");
        }
        if (!validateAlbum(a)) {
            throw new Exception("invalid Album bean");
        }
        
        UserClient uc = new UserClient();
        User newUser = uc.getUserById(userId);

        Album newAlbum = new Album();
        newAlbum.setAlbum_name(a.getAlbum_name());
        newAlbum.setUpc_code(a.getUpc_code());
        newAlbum.setPressing_year(a.getPressing_year());
        newAlbum.setArtist_group(a.getArtist_group());
        newAlbum.setCondition_state(a.getCondition_state());
        newAlbum.setNotes(a.getNotes());
        newAlbum.setUser_ownership(newUser);
        newAlbum.setActive(true);

        Album cr = wr.path("create").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(Album.class, newAlbum);

        return wr.path("entry/"+cr.getEntry()).type(MediaType.TEXT_PLAIN).accept(MediaType.APPLICATION_JSON).get(Album.class);

    }
    
    /**
     * Gets list of all active albums 
     * 
     * 
     * @return List of Album objects of all active albums
     */
    public List<Album> getAlbums() {
        
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures()
                .put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client c = Client.create(clientConfig);

        ClientResponse response
                = wr.accept(MediaType.APPLICATION_JSON)
                        .get(ClientResponse.class);

        GenericType<List<Album>> genericType = new GenericType<List<Album>>() {
        };

        return response.getEntity(genericType);
        
    }
    
    /**
     * gets album by entry
     * @param entry - entry value in DB
     * @return Album object of an album selected by entry
     * @throws Exception
     */
    public Album getAlbumByEntry(String entry) throws Exception {
        
        if (!validateEntry(entry)) {
            throw new Exception("entry value is not valid");
        }
        
        return wr.path("entry/"+entry).type(MediaType.TEXT_PLAIN).accept(MediaType.APPLICATION_JSON).get(Album.class);
        
    }
       
    /**
     * gets list of active/inactive albums of a user
     * @param userId - user id value from DB
     * @param active - boolean value of active/inactive album
     * @return List of Album objects of active/inactive albums of a user
     * @throws Exception
     */
    public List<Album> getActiveAlbumsByUserID(String userId, boolean active) throws Exception {
        if (!validateUserId(userId)) {
            throw new Exception("userId value is not valid");
        }
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures()
                .put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client c = Client.create(clientConfig);

        

        ClientResponse response
                = wr.path(userId+"/"+active).type(MediaType.TEXT_PLAIN).accept(MediaType.APPLICATION_JSON)
                        .get(ClientResponse.class);

        GenericType<List<Album>> genericType = new GenericType<List<Album>>() {
        };

        return response.getEntity(genericType);
    }
    
    
//    public List<Album> getInactiveAlbumsByUserID(String user_id) {
//        if (!validateUserId(userId)) {
//            throw new Exception("userId value is not valid");
//        }
//        ClientConfig clientConfig = new DefaultClientConfig();
//        clientConfig.getFeatures()
//                .put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
//        Client c = Client.create(clientConfig);
//
//       
//
//        ClientResponse response
//                = wr.path(user_id+"/false").type(MediaType.TEXT_PLAIN).accept(MediaType.APPLICATION_JSON)
//                        .get(ClientResponse.class);
//
//        GenericType<List<Album>> genericType = new GenericType<List<Album>>() {
//        };
//
//        return response.getEntity(genericType);
//    }

    /**
     * Gets list of active albums selected by upc/artist/albumName
     * 
     * @param upc - value of upc_code/artist/album_name to search
     * @return List of Album objects of active albums selected by upc/artist/albumName
     * @throws Exception
     */
    
    public List<Album> getAlbumByUpc(String upc) throws Exception {
        if(!validateUPC(upc)) {
            return null;
        }
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures()
                .put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client c = Client.create(clientConfig);

        ClientResponse response
                = wr.path(upc).type(MediaType.TEXT_PLAIN).accept(MediaType.APPLICATION_JSON)
                        .get(ClientResponse.class);

        GenericType<List<Album>> genericType = new GenericType<List<Album>>() {
        };

        return response.getEntity(genericType);
    }
    
    /**
     * Get list of albums selected by upc/artist/albumName and user id
     * @param userId - user id value to search
     * @param upc - upc/artist/albumName value to search
     * @return List of Album objects of albums selected by upc/artist/albumName and user id
     * @throws Exception
     */
    public List<Album> getAlbumByUpcAndUserId(String userId, String upc) throws Exception {
        
        if (!validateUserId(userId)) {
            throw new Exception("userId value is not valid");
        }
        if(!validateUPC(upc)) {
            return null;
        }
        
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures()
                .put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client c = Client.create(clientConfig);

        ClientResponse response
                = wr.path("upc/"+ upc + "/" + userId).type(MediaType.TEXT_PLAIN).accept(MediaType.APPLICATION_JSON)
                        .get(ClientResponse.class);

        GenericType<List<Album>> genericType = new GenericType<List<Album>>() {
        };

        return response.getEntity(genericType);
    }
    
    /**
     * Counts active albums of a user
     * @param userId - user id  value to count
     * @return Count of active albums of a user
     * @throws Exception
     */
    public String countActive(String userId) throws Exception {
        if (!validateUserId(userId)) {
            throw new Exception("userId value is not valid");
        }
        String count = wr.path("count/" + userId + "/true").get(String.class);
        
        return count;
    }
    
    /**
     * Counts inactive albums of a user
     * 
     * @param userId user id  value to count
     * @return Count of inactive albums of a user
     * @throws Exception
     */
    public String countInactive(String userId) throws Exception {
        if (!validateUserId(userId)) {
            throw new Exception("userId value is not valid");
        }
        String count = wr.path("count/" + userId + "/false").get(String.class);
        
        return count;
    }
    
    /**
     * Deactivates an album
     * @param entry - entry id value of an album to deactivate
     * @return Album object of deactivated album
     * @throws Exception
     */
    public Album deleteAlbum(String entry) throws Exception {
        if (!validateEntry(entry)) {
            throw new Exception("entry value is not valid");
        }
        wr.path("deleteEntry/").type(MediaType.TEXT_PLAIN).accept(MediaType.APPLICATION_JSON).put(ClientResponse.class, entry);
        
        return wr.path("entry/" + entry).type(MediaType.TEXT_PLAIN).accept(MediaType.APPLICATION_JSON).get(Album.class);
    }
    
    /**
     * Reactivates an album
     * @param entry - entry id value of an album to reactivate
     * @return Album object of reactivated album
     * @throws Exception
     */
    public Album  restoreAlbum(String entry) throws Exception {
        if (!validateEntry(entry)) {
            throw new Exception("entry value is not valid");
        }
        wr.path("restoreEntry/").type(MediaType.TEXT_PLAIN).accept(MediaType.APPLICATION_JSON).put(ClientResponse.class, entry);
        
        return wr.path("entry/" + entry).type(MediaType.TEXT_PLAIN).accept(MediaType.APPLICATION_JSON).get(Album.class);
        
    }
    
}
