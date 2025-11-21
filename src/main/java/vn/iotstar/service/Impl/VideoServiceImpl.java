package vn.iotstar.service.Impl;

import java.util.ArrayList;
import java.util.List;

import vn.iotstar.entity.Video;
import vn.iotstar.model.VideoModel;
import vn.iotstar.repository.IVideoRepository;
import vn.iotstar.repository.Impl.VideoRepositoryImpl;
import vn.iotstar.service.IVideoService;

public class VideoServiceImpl implements IVideoService {

    private IVideoRepository videoRepo = new VideoRepositoryImpl();

    // Entity -> Model
    private VideoModel toModel(Video v) {
        if (v == null) return null;

        VideoModel m = new VideoModel();
        m.setVideoId(v.getVideoId());
        m.setTitle(v.getTitle());
        m.setPoster(v.getPoster());
        m.setViews(v.getViews());
        m.setDescription(v.getDescription());
        m.setActive(v.getActive());

        if (v.getCategory() != null) {
            m.setCategoryId(v.getCategory().getCategoryId());
            m.setCategoryName(v.getCategory().getCategoryName());
        }

        return m;
    }

    // Model -> Entity
    private Video toEntity(VideoModel m) {
        if (m == null) return null;

        Video v = new Video();
        v.setVideoId(m.getVideoId());
        v.setTitle(m.getTitle());
        v.setPoster(m.getPoster());
        v.setViews(m.getViews());
        v.setDescription(m.getDescription());
        v.setActive(m.getActive());

        // Tạo category tham chiếu nếu categoryId != 0
        if (m.getCategoryId() != 0) {
            vn.iotstar.entity.Category category = new vn.iotstar.entity.Category();
            category.setCategoryId(m.getCategoryId());
            v.setCategory(category);
        }

        return v;
    }

    @Override
    public VideoModel findById(int videoId) {
        return toModel(videoRepo.findById(videoId));
    }

    @Override
    public List<VideoModel> findAll() {
        List<Video> list = videoRepo.findAll();
        List<VideoModel> result = new ArrayList<>();
        for (Video v : list) result.add(toModel(v));
        return result;
    }

    @Override
    public List<VideoModel> findByCategory(int categoryId) {
        List<Video> list = videoRepo.findByCategory(categoryId);
        List<VideoModel> result = new ArrayList<>();
        for (Video v : list) result.add(toModel(v));
        return result;
    }

    @Override
    public List<VideoModel> search(String keyword) {
        List<Video> list = videoRepo.search(keyword);
        List<VideoModel> result = new ArrayList<>();
        for (Video v : list) result.add(toModel(v));
        return result;
    }

    @Override
    public void insert(VideoModel video) {
        videoRepo.insert(toEntity(video));
    }

    @Override
    public void update(VideoModel video) {
        videoRepo.update(toEntity(video));
    }

    @Override
    public void delete(int videoId) {
        videoRepo.delete(videoId);
    }

    @Override
    public int count() {
        List<Video> list = videoRepo.findAll();
        return list.size();
    }
}
