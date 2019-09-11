package xs.client.webapp.beans;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * AlbumBean object for webClient to get input from JSF page for Album handling
 * 
 * @author saukin
 */
@Named("albumBean")
@SessionScoped
public class AlbumBean implements Serializable {
    
//    @Inject 
//    ListManager listManager;
    
    private int entry;
    
//    @NotEmpty(message = "provide album name please")
    @Size(min=1, max=25, message = "provide album name please")
    private String album_name;
    private String upc_code;
    
    @Min(1900) @Max(2019)
    private int pressing_year;
    
    @NotEmpty(message = "provide artist/group please")
    private String artist_group;
    private String condition_state;
    private String notes;
    private int user_id;
    private boolean active;
    
    /**
     *
     */
    public AlbumBean() {
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
     * @param user_id
     * @param active
     */
    public AlbumBean(int entry, String album_name, String upc_code, int pressing_year, String artist_group, String condition_state, String notes, int user_id, boolean active) {
        this.entry = entry;
        this.album_name = album_name;
        this.upc_code = upc_code;
        this.pressing_year = pressing_year;
        this.artist_group = artist_group;
        this.condition_state = condition_state;
        this.notes = notes;
        this.user_id = user_id;
        this.active = active;
    }

    /**
     *
     * @return
     */
    public int getEntry() {
//        return listManager.getAlbum().getEntry();
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
        album_name = album_name.trim();
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
        this.upc_code = upc_code.trim();
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
        this.artist_group = artist_group.trim();
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
        this.condition_state = condition_state.trim();
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
        this.notes = notes.trim();
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
    public boolean isActive() {
        return active;
    }

    /**
     *
     * @param active
     */
    public void setActive(boolean active) {
        this.active = active;
    }
    
    /**
     * Clears the bean parameters to clear fields on JSF addRecord page
     * @return empty bean
     */
    public String clearBean() {
        this.entry = 0;
        this.album_name = "";
        this.upc_code = "";
        this.pressing_year = 0;
        this.artist_group = "";
        this.condition_state = "";
        this.notes = "";
        
        return "addRecord";
    }
    
    
}
