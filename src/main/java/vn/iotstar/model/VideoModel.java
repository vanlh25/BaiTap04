package vn.iotstar.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VideoModel {

    private int videoId;        
    private String title;       
    private String poster;      
    private int views;          
    private String description; 
    private int active;        

    private int categoryId;     
    private String categoryName; 
}
