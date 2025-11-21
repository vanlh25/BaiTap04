package vn.iotstar.service.Impl;

import java.util.ArrayList;
import java.util.List;

import vn.iotstar.entity.Category;
import vn.iotstar.entity.User;
import vn.iotstar.model.CategoryModel;
import vn.iotstar.repository.ICategoryRepository;
import vn.iotstar.repository.Impl.CategoryRepositoryImpl;
import vn.iotstar.service.ICategoryService;

public class CategoryServiceImpl implements ICategoryService {

    private ICategoryRepository categoryRepo = new CategoryRepositoryImpl();

    // Entity -> Model
    private CategoryModel toModel(Category c) {
        if (c == null) return null;

        CategoryModel m = new CategoryModel();
        m.setCategoryId(c.getCategoryId());
        m.setCategoryName(c.getCategoryName());
        m.setCategoryCode(c.getCategoryCode());
        m.setImages(c.getImages());
        m.setStatus(c.getStatus());

        // mapping User quản lý
        m.setUserName(c.getUser() != null ? c.getUser().getUserName() : null);

        return m;
    }

    // Model -> Entity
    private Category toEntity(CategoryModel m) {
        if (m == null) return null;

        Category c = new Category();
        c.setCategoryId(m.getCategoryId());
        c.setCategoryName(m.getCategoryName());
        c.setCategoryCode(m.getCategoryCode()); 
        c.setImages(m.getImages());
        c.setStatus(m.getStatus());

        // mapping User nếu có
        if (m.getUserName() != null) {
            User user = new User();
            user.setUserName(m.getUserName());
            c.setUser(user);
        }

        return c;
    }

    @Override
    public CategoryModel findById(int id) {
        return toModel(categoryRepo.findById(id));
    }

    @Override
    public List<CategoryModel> findAll() {
        List<Category> list = categoryRepo.findAll();
        List<CategoryModel> result = new ArrayList<>();
        for (Category c : list) result.add(toModel(c));
        return result;
    }

    @Override
    public CategoryModel findByName(String name) {
        return toModel(categoryRepo.findByName(name));
    }

    @Override
    public int count() {
        return categoryRepo.count();
    }

    @Override
    public void insert(CategoryModel categoryModel) {
        categoryRepo.insert(toEntity(categoryModel));
    }

    @Override
    public void update(CategoryModel categoryModel) {
        categoryRepo.update(toEntity(categoryModel));
    }

    @Override
    public void delete(int id) {
        categoryRepo.delete(id);
    }
}
