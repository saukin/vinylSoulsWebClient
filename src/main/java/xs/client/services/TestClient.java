/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xs.client.services;

import java.time.LocalDate;
import java.util.List;
import xs.client.entities.Album;
import xs.client.entities.User;

/**
 *
 * @author admin
 */
public class TestClient {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
//        
        AlbumClient ac = new AlbumClient();
        UserClient uc = new UserClient();
//      
        
                
                
        System.out.println("CreateUser");
        System.out.println(uc.createUser("Msdfafdax", "NewPassword").toString());
//        
//        System.out.println("get user by id");
//        System.out.println(uc.getUserById("2").toString());

//        System.out.println("get user by email");        
//        System.out.println(uc.getUserByEmail("Email"));

//
//        System.out.println("EDIT ALBUM");
//        User u = new User();
//        u.setUser_id(1);
//        Album a = new Album(1, "Face The Heat", "upc1", 1994, "SCORPIONS", "M", "NOTES NOTS", u, true);
//        System.out.println(ac.editAlbum(a).toString());
//        

//        System.out.println("CREATE ALBUM");
//        User u = new User();
//        u.setUser_id(2);
//        Album a = new Album(1, "album_name", "upc", 1989, "Group", "M", "NOTES NOTS", u, true);        
//        System.out.println(ac.createAlbum(String.valueOf(u.getUser_id()), a).toString());
        
//        System.out.println("GET ALL ACTIVE ALBUMS");
//        List<Album> la = ac.getAlbums();
//        for (Album a : la) {
//            System.out.println(a.toString());
//        }
        
//        System.out.println("ALL ACTIVE ALBUMS BY USER ID");
//        List<Album> la = ac.getActiveAlbumsByUserID("1");
//        for (Album a : la) {
//            System.out.println(a.toString());
//        }
//        
//        System.out.println("ALL INACTIVE ALBUMS BY USER ID");
//        List<Album> li = ac.getInactiveAlbumsByUserID("2");
//        for (Album a : li) {
//            System.out.println(a.toString());
//        }

//        System.out.println("GET ALBUM BY UPC");
//        List<Album> la = ac.getAlbumByUpc("wow");
//        for (Album a : la) {
//            System.out.println(a.toString());
//        }

//        System.out.println("GET ALBUM BY UPC AAAND USER ID");
//        List<Album> la = ac.getAlbumByUpcAndUserId("wow","1");
//        for (Album a : la) {
//            System.out.println(a.toString());
//        }
        
//        System.out.println("COUNT ACTIVE BY USER ID");
//        System.out.println(ac.countActive("1"));
//        
//        System.out.println("COUNT INACTIVE BY USER ID");
//        System.out.println(ac.countInactive("1"));
        
//        Sy

        
//        System.out.println("GET ALBUM BY ENTRY");
//        System.out.println(ac.getAlbumByEntry("1"));

       
//        System.out.println("Password01".equals(uc.getUserByEmail("someuser").getPassword()));
    }
    
}
