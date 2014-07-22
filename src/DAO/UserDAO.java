package DAO;

import java.util.ArrayList;
import java.util.List;
import Entity.UserEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;

public class UserDAO {

    public void change(UserEntity endUser) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(endUser);
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
    }

    public void add(UserEntity user) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
    }

    public void delete(UserEntity user) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
    }

    public void delete(int id) {
        Session session = null;
        UserEntity user = this.getById(id);
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
    }

    public int loginPassword(String login, String password) {
        Session session = null;
        UserEntity user = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
                Query query = session.createQuery("FROM UserEntity WHERE login = :login AND password = :pass");
                query.setString("login", login);
                query.setString("pass", password);
            session.getTransaction().commit();
            user = (UserEntity) query.uniqueResult();
            if(user != null) return user.getId();
        } catch (Exception e){
            System.out.println("Trouble");
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return -1;
    }

    //@SuppressWarnings("unchecked")
    public List<UserEntity> getAll() {
        Session session = null;
        List<UserEntity> user = new ArrayList<UserEntity>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            user = (List<UserEntity>) session.createCriteria(UserEntity.class).list();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return user;
    }

    public UserEntity getById(int id) {
        Session session = null;
        UserEntity res = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            res = ((UserEntity) session.get(UserEntity.class, id));
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return res;
    }
}