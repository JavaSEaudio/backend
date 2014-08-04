package DAO;


import DAO.util.DAO;
import DAO.util.Factory;
import DAO.util.HibernateUtil;
import Entity.RestoreEntity;
import Entity.SessionEntity;
import Entity.TmpUserEntity;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class TmpUserDAO {
    private static DAO dao = Factory.getInstance().getDao();

    public void change(TmpUserEntity user) {
        dao.change(user);
    }

    public void add(TmpUserEntity user) {
        dao.add(user);
    }

    public void delete(TmpUserEntity user) {
        dao.delete(user);
    }

    public void delete(int id) {
        dao.delete(TmpUserEntity.class, id);
    }

    public List<TmpUserEntity> getAll() {
        return dao.getAll(TmpUserEntity.class);
    }

    public TmpUserEntity getById(int id) {
        return dao.getById(TmpUserEntity.class, id);
    }

    public TmpUserEntity isLogin(String login) {
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createQuery("FROM TmpUserEntity WHERE login = :login");
            query.setString("login", login);
            TmpUserEntity userEntity = null;
            userEntity = (TmpUserEntity) query.uniqueResult();
            session.getTransaction().commit();
            return userEntity;
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
    }
    public TmpUserEntity isEmail(String email) {
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createQuery("FROM TmpUserEntity WHERE email = :email");
            query.setString("email", email);
            TmpUserEntity userEntity = null;
            userEntity = (TmpUserEntity) query.uniqueResult();
            session.getTransaction().commit();
            return userEntity;
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
    }

    public TmpUserEntity geBytUniq(String uiq) {
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        TmpUserEntity user;
        try {
            session.beginTransaction();
            Query query = session.createQuery("from TmpUserEntity WHERE uniq = :uiq");
            query.setString("uiq", uiq);
            user = (TmpUserEntity) query.uniqueResult();
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return user;
    }

}
