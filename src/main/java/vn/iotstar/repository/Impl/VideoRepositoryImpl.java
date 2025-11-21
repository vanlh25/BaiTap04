package vn.iotstar.repository.Impl;

import java.util.List;
import jakarta.persistence.*;
import vn.iotstar.entity.Video;
import vn.iotstar.repository.IVideoRepository;
import vn.iotstar.config.JPAConfig;

public class VideoRepositoryImpl implements IVideoRepository {

    @Override
    public Video findById(int id) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            return em.find(Video.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Video> findAll() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            String jpql = "SELECT v FROM Video v";
            TypedQuery<Video> query = em.createQuery(jpql, Video.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Video> findByCategory(int categoryId) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            String jpql = "SELECT v FROM Video v WHERE v.category.categoryId = :cid";
            TypedQuery<Video> query = em.createQuery(jpql, Video.class);
            query.setParameter("cid", categoryId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Video> search(String keyword) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            String jpql = "SELECT v FROM Video v WHERE v.title LIKE :kw";
            TypedQuery<Video> query = em.createQuery(jpql, Video.class);
            query.setParameter("kw", "%" + keyword + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public void insert(Video video) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(video);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Video video) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(video);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(int id) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            Video video = em.find(Video.class, id);
            if (video != null) {
                tx.begin();
                em.remove(video);
                tx.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
