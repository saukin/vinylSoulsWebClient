
package xs.client.validation;

import java.time.LocalDate;

/**
 * VAlidation class for Album an User objects' fields
 * @author saukin
 */
public class QueryValidation {
    
    /**
     * 
     * @param id
     * @return
     */
    public static boolean validateUserId(String id) {
        //8
        return (id.trim().length() <= 8 && id.trim().length() > 0);
    }
    
    /**
     * validates email/login
     * @param email
     * @return
     */
    public static boolean validateEmail(String email) {
        //25
        return (email.trim().length() <= 25 && email.trim().length() > 0);
    }
    
    /**
     * validates password
     * @param password
     * @return
     */
    public static boolean validatePassword(String password) {
        //25
        return (password.trim().length() <= 25 && password.trim().length() > 0);
    }
    
    /**
     * validates entry
     * @param entry
     * @return
     */
    public static boolean validateEntry(String entry) {
        //8
        return (entry.trim().length() <= 8 && entry.trim().length() > 0);
    }
    
    /**
     * validates album_name
     * @param albumName
     * @return
     */
    public static boolean validateAlbumName(String albumName) {
        //25
        return (albumName.trim().length() <= 25 && albumName.trim().length() > 0);
    }
    
    /**
     * validates upc
     * @param upc
     * @return
     */
    public static boolean validateUPC(String upc) {
        // 25
        
        return (upc.trim().length() > 0);
    }
    
    /**
     * validates pressing year 
     * @param year
     * @return
     */
    public static boolean validateYear(int year) {
       // 4
        int thisYear = LocalDate.now().getYear();
           
        return !((thisYear < year) || (year < 1900));
    }
    
    /**
     * validates artist
     * @param artist
     * @return
     */
    public static boolean validateArtist(String artist) {
        //25
        return (artist.trim().length() <= 25 && artist.trim().length() > 0);
    }
    
    /**
     * validates condition
     * @param condition
     * @return
     */
    public static boolean validateCondition(String condition) {
        // 4
        return condition.trim().length() <= 4;
    }
    
    /**
     * validates notes
     * @param notes
     * @return
     */
    public static boolean validateNotes(String notes) {
        // 400
        return notes.trim().length() <= 400;
    }
    
    /**
     * validates owner's id
     * @param owner
     * @return
     */
    public static boolean validateOwner(String owner) {
        // 8
        return owner.trim().length() <= 8;
    }
    
    /**
     * validates active 
     * @param active
     * @return
     */
    public static boolean validateActive(String active) {
       // true false
       try {
           boolean parsedActive = Boolean.getBoolean(active);
           
           return parsedActive;
           
       } catch (NumberFormatException e) {
           return false;
       }
       
    }
    
    
}
