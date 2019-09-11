
package xs.client.entities;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity class for the  REST Web Service XS_PROJECT
 * @author saukin
 */

@XmlRootElement
public class Album implements Serializable {
    
    private int entry;
    private String album_name;
    private String upc_code;
    private int pressing_year;
    private String artist_group;
    private String condition_state;
    private String notes;
    private User user_ownership;
    private boolean active;

    /**
     *
     */
    public Album() {
    }
    
    /**
     *
     * @param entry
     * @param album_name
     * @param upc_code
     * @param pressing_year
     * @param artist_group
     * @param condition_state
     * @param notes
     * @param user_ownership
     * @param active
     */
    public Album(int entry, String album_name, String upc_code, int pressing_year, String artist_group, String condition_state, String notes, User user_ownership, boolean active) {
        this.entry = entry;
        this.album_name = album_name;
        this.upc_code = upc_code;
        this.pressing_year = pressing_year;
        this.artist_group = artist_group;
        this.condition_state = condition_state;
        this.notes = notes;
        this.user_ownership = user_ownership;
        this.active = active;
    }

    /**
     *
     * @return
     */
    public int getEntry() {
        return entry;
    }

    /**
     *
     * @param entry
     */
    public void setEntry(int entry) {
        this.entry = entry;
    }

    /**
     *
     * @return
     */
    public String getAlbum_name() {
        return album_name;
    }

    /**
     *
     * @param album_name
     */
    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }

    /**
     *
     * @return
     */
    public String getUpc_code() {
        return upc_code;
    }

    /**
     *
     * @param upc_code
     */
    public void setUpc_code(String upc_code) {
        this.upc_code = upc_code;
    }

    /**
     *
     * @return
     */
    public int getPressing_year() {
        return pressing_year;
    }

    /**
     *
     * @param pressing_year
     */
    public void setPressing_year(int pressing_year) {
        this.pressing_year = pressing_year;
    }

    /**
     *
     * @return
     */
    public String getArtist_group() {
        return artist_group;
    }

    /**
     *
     * @param artist_group
     */
    public void setArtist_group(String artist_group) {
        this.artist_group = artist_group;
    }

    /**
     *
     * @return
     */
    public String getCondition_state() {
        return condition_state;
    }

    /**
     *
     * @param condition_state
     */
    public void setCondition_state(String condition_state) {
        this.condition_state = condition_state;
    }

    /**
     *
     * @return
     */
    public String getNotes() {
        return notes;
    }

    /**
     *
     * @param notes
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     *
     * @return
     */
    public User getUser_ownership() {
        return user_ownership;
    }

    /**
     *
     * @param user_ownership
     */
    public void setUser_ownership(User user_ownership) {
        this.user_ownership = user_ownership;
    }

    /**
     *
     * @return
     */
    public boolean getActive() {
        return active;
    }

    /**
     *
     * @param active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Album{" + "entry=" + entry + ", album_name=" + album_name + ", upc_code=" + upc_code + ", pressing_year=" + pressing_year + ", artist_group=" + artist_group + ", condition_state=" + condition_state + ", notes=" + notes + ", user_ownership=" + user_ownership + ", active=" + active + '}';
    }
    
    
    
}
