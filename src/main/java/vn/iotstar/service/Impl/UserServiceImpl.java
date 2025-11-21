package vn.iotstar.service.Impl;

import java.util.ArrayList;
import java.util.List;

import vn.iotstar.entity.User;
import vn.iotstar.model.UserModel;
import vn.iotstar.repository.IUserRepository;
import vn.iotstar.repository.Impl.UserRepositoryImpl;
import vn.iotstar.service.IUserService;

public class UserServiceImpl implements IUserService {

    private IUserRepository userRepo = new UserRepositoryImpl();

    //Chức năng CRUD

    @Override
    public UserModel findById(String userName) {
        User user = userRepo.findById(userName);
        return user != null ? toModel(user) : null;
    }

    @Override
    public List<UserModel> findAll() {
        List<User> users = userRepo.findAll();
        List<UserModel> list = new ArrayList<>();
        for (User u : users) {
            list.add(toModel(u));
        }
        return list;
    }

    @Override
    public UserModel findByName(String userName) {
        User user = userRepo.findByName(userName);
        return user != null ? toModel(user) : null;
    }

    @Override
    public int count() {
        return userRepo.count();
    }

    @Override
    public void insert(UserModel model) {
        User user = toEntity(model);
        userRepo.insert(user);
    }

    @Override
    public void update(UserModel model) {
        User user = toEntity(model);
        userRepo.update(user);
    }

    @Override
    public void delete(String userName) {
        userRepo.delete(userName);
    }

    // Chức năng Login / Register / Edit

    @Override
    public UserModel login(String userName, String password) {
        User user = userRepo.findByName(userName);
        if (user != null && user.getPassword().equals(password)) {
            return toModel(user);
        }
        return null;
    }

    @Override
    public boolean register(String email, String userName, String fullName, String password, String phone) {
        // Kiểm tra trùng lặp
        if (userRepo.checkExistEmail(email) || userRepo.checkExistUsername(userName) || userRepo.checkExistPhone(phone)) {
            return false;
        }

        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAdmin(false); // Mặc định không phải admin
        user.setActive(1);
        user.setImages(null);

        userRepo.insert(user);
        return true;
    }

    @Override
    public boolean editPassword(String email, String newPassword) {
        return userRepo.editPassword(email, newPassword);
    }

    // Kiểm tra trước khi đăng kí Check 
    @Override
    public boolean checkExistEmail(String email) {
        return userRepo.checkExistEmail(email);
    }

    @Override
    public boolean checkExistUsername(String userName) {
        return userRepo.checkExistUsername(userName);
    }

    @Override
    public boolean checkExistPhone(String phone) {
        return userRepo.checkExistPhone(phone);
    }

    @Override
    public boolean checkRoleAdmin(String userName) {
        return userRepo.checkRoleAdmin(userName);
    }

    //  Mapper
 // Entity -> Model
    private UserModel toModel(User user) {
        if (user == null) return null;

        UserModel model = new UserModel();
        model.setUserName(user.getUserName());
        model.setPassword(user.getPassword());
        model.setFullName(user.getFullName());
        model.setEmail(user.getEmail());
        model.setPhone(user.getPhone());
        model.setAdmin(user.isAdmin() ? 1 : 0); // 1 = admin
        model.setImages(user.getImages());

        return model;
    }

    // Model -> Entity
    private User toEntity(UserModel model) {
        if (model == null) return null;

        User user = new User();
        user.setUserName(model.getUserName());
        user.setPassword(model.getPassword());
        user.setFullName(model.getFullName());
        user.setEmail(model.getEmail());
        user.setPhone(model.getPhone());
        user.setAdmin(model.getAdmin() == 1);
        user.setImages(model.getImages());
        user.setActive(1); // mặc định active = 0

        return user;
    }

}
