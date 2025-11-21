package vn.iotstar.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {

    // Khóa chínhlà userName
    private String userName;

    private String password;
    private String phone;
    private String fullName;
    private String email;

    // 1 = admin, 0 = user thường
    private int admin;

    private int active;

    private String images; // mapping từ images trong entity
}
