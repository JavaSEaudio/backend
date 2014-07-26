package DAO;

import java.util.List;

import DAO.util.DAO;
import DAO.util.Factory;
import Entity.UserEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import DAO.util.HibernateUtil;

public class UserDAO {
    private static DAO dao = Factory.getInstance().getDao();

    public void change(UserEntity user) {
       dao.change(user);
    }

    public void add(UserEntity user) {
        dao.add(user);
    }

    public void delete(UserEntity user) {
       dao.delete(user);
    }

    public void delete(int id) {
        dao.delete(UserEntity.class, id);
    }

    public List<UserEntity> getAll() {
        return dao.getAll(UserEntity.class);
    }

    public UserEntity getById(int id) {
       return dao.getById(UserEntity.class, id);
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

            if(user != null) {
                if(user.getLogin().equals(login)){
                    if(user.getPassword().equals(password)){
                        return user.getId();
                    }
                }
            }
        } catch (Exception e){}
        finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return -1;
    }

    public int emailPassword(String email, String password) {
        Session session = null;
        UserEntity user = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createQuery("FROM UserEntity WHERE email = :email AND password = :pass");
            query.setString("email", email);
            query.setString("pass", password);

            session.getTransaction().commit();
            user = (UserEntity) query.uniqueResult();

            if(user != null) {
                if(user.getPassword().equals(password)){
                    return user.getId();
                }
            }
        } catch (Exception e){}
        finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return -1;
    }

    public UserEntity getByLogin(String login) {
        Session session = null;
        UserEntity user = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createQuery("FROM UserEntity WHERE login = :login");
            query.setString("login", login);
            session.getTransaction().commit();
            user = (UserEntity) query.uniqueResult();
        } catch (Exception e){
            System.out.println("Trouble");
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return user;
    }

    public UserEntity getByEmail(String email) {
        Session session = null;
        UserEntity user = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createQuery("FROM UserEntity WHERE email = :email");
            query.setString("email", email);
            session.getTransaction().commit();
            user = (UserEntity) query.uniqueResult();
        } catch (Exception e){
            System.out.println("Trouble");
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return user;
    }
}
