
package xs.client.validation;

import xs.client.entities.Album;
import xs.client.entities.User;
import static xs.client.validation.QueryValidation.validateAlbumName;
import static xs.client.validation.QueryValidation.validateArtist;
import static xs.client.validation.QueryValidation.validateCondition;
import static xs.client.validation.QueryValidation.validateEmail;
import static xs.client.validation.QueryValidation.validateNotes;
import static xs.client.validation.QueryValidation.validateUPC;
import static xs.client.validation.QueryValidation.validateUserId;
import static xs.client.validation.QueryValidation.validateYear;

/**
 *
 * @author saukin
 */
public class BeanValidation {
    
    /**
     * validates Album object
     * @param a - Album object value
     * @return boolean valid or not the Album object
     */
    public static boolean validateAlbum(Album a) {
        
        if (!validateAlbumName(a.getAlbum_name()) || (a.getUpc_code().length() > 25) || 
               !validateYear(a.getPressing_year()) || !validateArtist(a.getArtist_group()) ||
               !validateCondition(a.getCondition_state()) || !validateNotes(a.getNotes())) {
            return false;
        }
        
        return true;
    }
    
    /**
     * validates User object
     * @param u - User object value
     * @return boolean valid or not the User object
     */
    public static boolean validateUser(User u) {
        return !(!validateEmail(u.getEmail()) || !validateUserId(String.valueOf(u.getUser_id())));
    }
    
}
