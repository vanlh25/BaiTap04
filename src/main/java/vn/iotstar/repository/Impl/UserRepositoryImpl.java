package vn.iotstar.repository.Impl;

import java.util.List;
import jakarta.persistence.*;
import vn.iotstar.entity.User;
import vn.iotstar.repository.IUserRepository;
import vn.iotstar.config.JPAConfig;

public class UserRepositoryImpl implements IUserRepository {

    @Override
    public User findById(String userName) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            return em.find(User.class, userName);
        } finally {
            em.close();
        }
    }

    @Override
    public List<User> findAll() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            String jpql = "SELECT u FROM User u";
            TypedQuery<User> query = em.createQuery(jpql, User.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public User findByName(String userName) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u WHERE u.userName = :userName", User.class);
            query.setParameter("userName", userName);
            List<User> list = query.getResultList();
            return list.isEmpty() ? null : list.get(0);
        } finally {
            em.close();
        }
    }

    @Override
    public int count() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            Long total = em.createQuery("SELECT COUNT(u) FROM User u", Long.class)
                           .getSingleResult();
            return total.intValue();
        } finally {
            em.close();
        }
    }

    @Override
    public void insert(User user) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(user);
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
    public void update(User user) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(user);
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
    public void delete(String userName) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            User user = em.find(User.class, userName);
            if (user != null) {
                tx.begin();
                em.remove(user);
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

    @Override
    public boolean checkExistEmail(String email) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            Long count = em.createQuery(
                "SELECT COUNT(u) FROM User u WHERE u.email = :email", Long.class)
                .setParameter("email", email)
                .getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean checkExistUsername(String userName) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            Long count = em.createQuery(
                "SELECT COUNT(u) FROM User u WHERE u.userName = :userName", Long.class)
                .setParameter("userName", userName)
                .getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean checkExistPhone(String phone) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            Long count = em.createQuery(
                "SELECT COUNT(u) FROM User u WHERE u.phone = :phone", Long.class)
                .setParameter("phone", phone)
                .getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean checkRoleAdmin(String userName) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            User user = em.find(User.class, userName);
            if (user != null) {
                return user.isAdmin(); // trả về true nếu là admin
            }
            return false; // user không tồn tại hoặc không phải admin
        } finally {
            em.close();
        }
    }

    @Override
    public boolean editPassword(String email, String newPassword) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            List<User> list = em.createQuery(
                "SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email)
                .getResultList();

            if (list.isEmpty()) return false;

            User user = list.get(0);

            tx.begin();
            user.setPassword(newPassword);
            em.merge(user);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }
}
