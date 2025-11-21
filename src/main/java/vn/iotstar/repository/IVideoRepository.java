package vn.iotstar.repository;

import java.util.List;
import vn.iotstar.entity.Video;

public interface IVideoRepository {

    Video findById(int id);

    List<Video> findAll();

    List<Video> findByCategory(int categoryId);

    List<Video> search(String keyword);

    void insert(Video video);

    void update(Video video);

    void delete(int id);
}
