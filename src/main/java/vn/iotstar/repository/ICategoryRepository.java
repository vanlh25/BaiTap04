package vn.iotstar.repository;

import java.util.List;
import vn.iotstar.entity.Category;

public interface ICategoryRepository {

    Category findById(int id);

    List<Category> findAll();

    Category findByName(String name);

    int count();

    void insert(Category category);

    void update(Category category);

    void delete(int id);
}
