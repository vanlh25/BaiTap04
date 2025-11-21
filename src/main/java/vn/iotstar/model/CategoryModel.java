package vn.iotstar.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryModel {

    private int categoryId;       
    private String categoryName;  
    private String categoryCode; 
    private String images;        
    private int status;           

    // Lưu thông tin user quản lý category
    private String userName;      
}
