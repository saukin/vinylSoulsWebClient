
package xs.client.webapp.beans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

/**
 * Bean for uploading images for albums
 * 
 * @author saukin
 */
@Named("fileUploadBean")
@RequestScoped
public class FileUploadBean {
    
    private Part image;
    
    @Inject
    AlbumBean albumBean;
    
    
    /**
     * Uploads a file 
     * @return String value of the next page
     * @throws IOException
     */
    public String upload() throws IOException {
        
        try {
            InputStream iStream = image.getInputStream();
            String fileType = image.getSubmittedFileName();
            
            int i = fileType.indexOf('.');
            fileType = fileType.substring(i, fileType.length());
            
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            String folder = context.getRealPath("");
            
            String[] projectFolder = folder.split("target");
            folder = projectFolder[0] + "src\\main\\webapp\\resources\\posters\\" ;
            
            File f = new File(folder + albumBean.getEntry() + fileType);
            
            f.createNewFile();
//            f.renameTo(f)
            FileOutputStream out = new FileOutputStream(f);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = iStream.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            out.close();
            iStream.close();
            
        } catch (IOException e) {
            System.out.println("oppa");
        }
        
//        Files.write(Paths.get("E:\\STUDIES\\WEB Services\\_PROJECT\\UploadTEst\\src\\main\\webapp\\resources\\"+image.getSubmittedFileName()), f.getInputStream().getBytes(), StandardOpenOption.CREATE);
        return "editPage";
    }

    /**
     * getter of this.object 
     * @return
     */
    public Part getImage() {
        return image;
    }

    /**
     * setter of this.object
     * @param image
     */
    public void setImage(Part image) {
        this.image = image;
    }
    
}
