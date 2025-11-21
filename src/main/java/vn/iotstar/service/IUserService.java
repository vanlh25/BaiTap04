package vn.iotstar.service;

import java.util.List;

import vn.iotstar.model.UserModel;

public interface IUserService {
    // Các phương thức CRUD cơ bản
    UserModel findById(String userName);

    List<UserModel> findAll();

    UserModel findByName(String userName);

    int count();

    void insert(UserModel user);

    void update(UserModel user);

    void delete(String userName);

    // Các tính năng login / register / đổi password
    UserModel login(String userName, String password);

    boolean register(String email, String userName, String fullName, String password, String phone);

    boolean checkExistEmail(String email);

    boolean checkExistUsername(String userName);

    boolean checkExistPhone(String phone);
    
    boolean checkRoleAdmin(String userName);

    boolean editPassword(String email, String newPassword);
}
