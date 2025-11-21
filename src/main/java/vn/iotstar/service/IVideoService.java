package vn.iotstar.service;

import java.util.List;
import vn.iotstar.model.VideoModel;

public interface IVideoService {

    // CRUD cơ bản
    VideoModel findById(int videoId);

    List<VideoModel> findAll();

    List<VideoModel> findByCategory(int categoryId);

    List<VideoModel> search(String keyword);

    void insert(VideoModel video);

    void update(VideoModel video);

    void delete(int videoId);

    int count();
}
