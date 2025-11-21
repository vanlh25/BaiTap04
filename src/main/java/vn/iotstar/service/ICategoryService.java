package vn.iotstar.service;

import java.util.List;
import vn.iotstar.model.CategoryModel;

public interface ICategoryService {

    CategoryModel findById(int id);

    List<CategoryModel> findAll();

    CategoryModel findByName(String name);

    int count();

    void insert(CategoryModel categoryModel);

    void update(CategoryModel categoryModel);

    void delete(int id);
}
