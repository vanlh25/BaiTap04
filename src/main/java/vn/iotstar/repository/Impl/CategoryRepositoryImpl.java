package vn.iotstar.repository.Impl;

import java.util.List;
import jakarta.persistence.*;
import vn.iotstar.entity.Category;
import vn.iotstar.repository.ICategoryRepository;
import vn.iotstar.config.JPAConfig;

public class CategoryRepositoryImpl implements ICategoryRepository {

    @Override
    public Category findById(int id) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            return em.find(Category.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Category> findAll() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            String jpql = "SELECT c FROM Category c";
            TypedQuery<Category> query = em.createQuery(jpql, Category.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Category findByName(String name) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<Category> query = em.createQuery(
                "SELECT c FROM Category c WHERE c.categoryName = :name", Category.class);
            query.setParameter("name", name);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public int count() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            Long total = em.createQuery("SELECT COUNT(c) FROM Category c", Long.class)
                           .getSingleResult();
            return total.intValue();
        } finally {
            em.close();
        }
    }

    @Override
    public void insert(Category category) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(category);
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
    public void update(Category category) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(category);
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
    public void delete(int categoryId) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            // 1. Lấy Category cần xóa
            Category category = em.find(Category.class, categoryId);
            if (category != null) {
                // 2. Set Category = null cho các Video liên quan
                String jpql = "UPDATE Video v SET v.category = null WHERE v.category.categoryId = :cid";
                em.createQuery(jpql).setParameter("cid", categoryId).executeUpdate();

                // 3. Xóa Category
                em.remove(category);
            }

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

}
