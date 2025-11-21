package vn.iotstar.repository;

import java.util.List;
import vn.iotstar.entity.User;

public interface IUserRepository {

    User findById(String userName);

    List<User> findAll();

    User findByName(String userName);

    int count();

    void insert(User user);

    void update(User user);

    void delete(String userName);

    
    // Kiểm tra email tồn tại
    boolean checkExistEmail(String email);

    // Kiểm tra username tồn tại
    boolean checkExistUsername(String userName);

    // Kiểm tra phone tồn tại
    boolean checkExistPhone(String phone);
    
    // Kiểm tra role admin (true là admin)
    boolean checkRoleAdmin(String userName);
    
    // Cập nhật mật khẩu theo email
    boolean editPassword(String email, String newPassword);
}
