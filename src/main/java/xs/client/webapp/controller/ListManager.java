
package xs.client.webapp.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotEmpty;
import xs.client.entities.Album;
import xs.client.messages.MessagesUtil;
import xs.client.services.AlbumClient;
import xs.client.webapp.beans.AlbumBean;
import xs.client.webapp.beans.UserBean;

/**
 * Class for handling the data from the service
 * 
 * @author saukin
 */
@Named("listManager")
@SessionScoped
public class ListManager implements Serializable {

    
    @Inject 
    UserBean userBean;
    
    @Inject
    AlbumBean albumBean;
    
    AlbumClient albumClient = new AlbumClient();
    
    /**
     * 
     * List to render in data table
     */
    private List<Album> albumList = albumClient.getAlbums();
    
    /**
     * Album to send the values to AlbumBean
     */
    private Album selectedAlbum = getAlbumFromList(0);
    
    @NotEmpty(message = "search field cannot be empty")
    private String upcName;
    
    /**
     * page to render
     * it is set in methods
     */
    private String page = "index";
    
    /**
     * 
     * boolean value which tells the client whether to look for an album among
     * all active or among user's albums
     */
    private boolean searchId;
    
    /**
     * variable that defines the button to delete or restore an album
     */
    private String albumStatus;
    
    /**
     * variable to know display or not controls on JSF pages
     */
    private String display = "flex";
    
    /**
     * constructor
     */
    public ListManager() {
    }

    /**
     * sets albumlist for page layout in a data table
     * @param al - List recieved from service
     */
    public void setAlbumList(List<Album> al) {
        this.albumList = al;
    }

    /**
     * 
     * @return 
     */
    public List<Album> getAlbumList() {
        return albumList;
    }
    
    /**
     *
     * @param i - index of the album in the list
     * @return Album selected in data table on the page
     */
    public Album getAlbumFromList(int i) {
        return albumList.get(i);
    }

    /**
     *
     * @return
     */
    public Album getSelectedAlbum() {
        return selectedAlbum;
    }

    /**
     *
     * @param selectedAlbum
     */
    public void setSelectedAlbum(Album selectedAlbum) {
        this.selectedAlbum = selectedAlbum;
    }
    
    /**
     *
     * @return
     */
    public String getPage() {
        return page;
    }

    /**
     *
     * @param page
     */
    public void setPage(String page) {
        this.page = page;
    }
    
    /**
     *
     * @return
     */
    public String getUpcName() {
        return upcName;
    }

    /**
     *
     * @param upcName
     */
    public void setUpcName(String upcName) {
        this.upcName = upcName.trim();
    }

    /**
     *
     * @return
     */
    public boolean isSearchId() {
        return searchId;
    }

    /**
     *
     * @param searchId
     */
    public void setSearchId(boolean searchId) {
        this.searchId = searchId;
    }
    
    /**
     *
     * @return
     */
    public String getAlbumStatus() {
       if (selectedAlbum.getActive()) {
            albumStatus = "delete";
        } else {
            albumStatus = "restore";
       }    
        return albumStatus;
    }

    /**
     *
     * @return
     */
    public String getDisplay() {
        if (selectedAlbum.getUser_ownership() == null) {
            return "none";
        }
        if(userBean.getUser_id() == selectedAlbum.getUser_ownership().getUser_id()) {
            display = "flex";
        } else {
            display = "none";
        }
        return display;
    }

    /**
     * 
     * @param display
     */
    public void setDisplay(String display) {
        if (display == null) {
            this.display = "none";
        }
        this.display = display;
    }
    
    /**
     * gets album info selected in data table on a jsf page
     * @param entry - entry value of an album
     * @return String value of a page to render
     * @throws Exception
     */
    public String getAlbumInfo(String entry) throws Exception {
        FacesContext fc = FacesContext.getCurrentInstance();
        entry = getEntryParam(fc);
        selectedAlbum = albumClient.getAlbumByEntry(entry);
        setAlbumBean();
        return page;
    }

    /**
     * getting faces parameter from data table
     * @param fc
     * @return
     */
    public String getEntryParam(FacesContext fc) {

        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        return params.get("entry");

    }

    /**
     * Gets list of album and sends to albumList to render on a page 
     * @return String value of a page to render
     * @throws Exception
     */
    public String getAlbumListByUpc() throws Exception {
        try {
            if (upcName.trim() != null) {
                if (searchId == false) {
                    albumList = albumClient.getAlbumByUpc(upcName);
                    if (albumList.size()>0) {
                    selectedAlbum = albumList.get(0);
                    }     
                } else {
                    albumList = albumClient.getAlbumByUpcAndUserId(String.valueOf(userBean.getUser_id()),upcName);
                    if (albumList.size()>0) {
                        selectedAlbum = albumList.get(0);
                    }
                }
            } 
        } catch (Exception e) {
//            return page;
        }
        return page;
    }
    
    /**
     * gets list of album to render after login
     * @param active 
     * @return String value of a page to render
     * @throws Exception
     */
    public String getAlbumsByUserId(boolean active) throws Exception {
        albumList = albumClient.getActiveAlbumsByUserID(String.valueOf(userBean.getUser_id()), active);
        if (albumList.size() > 0) {
            selectedAlbum = albumList.get(0);
            
            }
        searchId = true;
        return page;
    }
    
    /**
     * Resets list set when user wants to see all active albums in service
     * @return
     */
    public String resetList() {
       albumList = albumClient.getAlbums();
       searchId = false;
       upcName="";
       return page;
    }
    
    /**
     * deactivates or reactivates an album
     * 
     * @return String value of a page to render
     * @throws Exception
     */
    public String deleteAlbum() throws Exception {
        if (selectedAlbum.getActive()) {
            albumClient.deleteAlbum(String.valueOf(selectedAlbum.getEntry()));
            albumList = albumClient.getActiveAlbumsByUserID(String.valueOf(userBean.getUser_id()), false);
            if (albumList.size()>0) {
                selectedAlbum = albumList.get(0);
            }
        } else {
            albumClient.restoreAlbum(String.valueOf(selectedAlbum.getEntry()));
            albumList = albumClient.getActiveAlbumsByUserID(String.valueOf(userBean.getUser_id()), true);
            if (albumList.size()>0) {
                selectedAlbum = albumList.get(0);
            }
        }
        return page;
    }
    
    /**
     * Updates an album on editPage after inserting values
     * @return String value of a page to render
     * @throws Exception
     */
    public String updateAlbum() throws Exception {
        if (albumBean.getAlbum_name().trim().length() < 1) {
            FacesMessage message;
            message = MessagesUtil.getMessage(
                    "xs.client.bundles.messages", "needAlbum", null);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            FacesContext.getCurrentInstance().addMessage(null, message);
            return null;
        } else if (albumBean.getArtist_group().trim().length() < 1) {
            FacesMessage message;
            message = MessagesUtil.getMessage(
                    "xs.client.bundles.messages", "needArtist", null);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            FacesContext.getCurrentInstance().addMessage(null, message);
            return null;
        }
        page = "myPage";
        Album a = new Album(selectedAlbum.getEntry(), albumBean.getAlbum_name(), albumBean.getUpc_code(),
                            albumBean.getPressing_year(), albumBean.getArtist_group(), albumBean.getCondition_state(),
                            albumBean.getNotes(), selectedAlbum.getUser_ownership(), true);
        
        selectedAlbum = albumClient.editAlbum(a);
        albumList = albumClient.getActiveAlbumsByUserID(String.valueOf(userBean.getUser_id()), true);
        return page;
    }
    
    /**
     * sets the page variable for adding Page
     * @return String value of a page to render
     */
    public String addFromBrendan() {
        page = "addFromUPC";
        return "addFromUPC";
    }
    
    /**
     * sets the page variable for editing Page
     * @return String value of a page to render
     */
    public String getFromBrendan() {
        page = "editFromUPC";
        return "editFromUPC";
    }
    
    /**
     * adds an album
     * @return String value of a page to render
     * @throws Exception
     */
    public String addAlbum() throws Exception {
        if (albumBean.getAlbum_name().trim().length() < 1) {
            FacesMessage message;
            message = MessagesUtil.getMessage(
                    "xs.client.bundles.messages", "needAlbum", null);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            FacesContext.getCurrentInstance().addMessage(null, message);
            return null;
        } else if (albumBean.getArtist_group().trim().length() < 1) {
            FacesMessage message;
            message = MessagesUtil.getMessage(
                    "xs.client.bundles.messages", "needArtist", null);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            FacesContext.getCurrentInstance().addMessage(null, message);
            return null;
        }
        page = "myPage";
        Album a = new Album();
        a.setAlbum_name(albumBean.getAlbum_name());
        a.setUpc_code(albumBean.getUpc_code());
        a.setPressing_year(albumBean.getPressing_year());
        a.setArtist_group(albumBean.getArtist_group());
        a.setCondition_state(albumBean.getCondition_state());
        a.setNotes(albumBean.getNotes());
        
        selectedAlbum = albumClient.createAlbum(String.valueOf(userBean.getUser_id()), a);
        albumList = albumClient.getActiveAlbumsByUserID(String.valueOf(userBean.getUser_id()), true);
        setAlbumBean();
        return page;
    }
    
    /**
     * sets album bean for JSF page
     */
    protected void setAlbumBean() {
        
        albumBean.setEntry(selectedAlbum.getEntry());
        albumBean.setAlbum_name(selectedAlbum.getAlbum_name());
        albumBean.setArtist_group(selectedAlbum.getArtist_group());
        albumBean.setUpc_code(selectedAlbum.getUpc_code());
        albumBean.setNotes(selectedAlbum.getNotes());
        albumBean.setPressing_year(selectedAlbum.getPressing_year());
        albumBean.setCondition_state(selectedAlbum.getCondition_state());
        
    }
    
}
