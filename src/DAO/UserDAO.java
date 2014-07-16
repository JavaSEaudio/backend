package DAO;

import java.util.ArrayList;
import java.util.List;

import Entity.UserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import util.HibernateUtil;

public class UserDAO {

    /**
     * НЕ ЮЗАТЬ!!! НЕ РАБОТАЕТ!!!
     */
    public void change(UserEntity user) {
        Session session = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(user);
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


    /**
     * НЕ ЮЗАТЬ!!! НЕ РАБОТАЕТ!!!
     */
    public boolean loginPassword(String login, String password) {
        Session session = null;
        String query = "SELECT id FROM user WHERE login = "+login+" AND password = "+password;
        List<Integer> id = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            id = (List<Integer>) session.createQuery(query).list();
            if(id != null) return true;
        } catch (Exception e){
            System.out.println("Trouble");
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return false;
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

    /**
     * МОГУТ ВОЗНИКАТЬ ОШИБКИ
     */
    public UserEntity getById(int id) {
        Session session = null;
        UserEntity res = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            res = (UserEntity) session.get(UserEntity.class, id);
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return res;
    }
}