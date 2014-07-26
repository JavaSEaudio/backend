package DAO;

import DAO.util.DAO;
import DAO.util.Factory;
import Entity.SessionEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import DAO.util.HibernateUtil;

import java.util.List;

public class SessionDAO {
    private static DAO dao = Factory.getInstance().getDao();

    public void add(SessionEntity sessionEntity) {
        dao.add(sessionEntity);
    }

    public void delete(SessionEntity audio) {
        dao.delete(audio);
    }

    public void delete(int userId) {
        SessionEntity sessionEntity = this.getByUserId(userId);
        dao.delete(sessionEntity);
    }

    public SessionEntity getById(int id) {
        return dao.getById(SessionEntity.class, id);
    }

    public List<SessionEntity> getAll() {
        return dao.getAll(SessionEntity.class);
    }

    public void delete(String key) {
        Session session = null;
        SessionEntity sessionEntity = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
                Query query = session.createQuery("FROM SessionEntity WHERE skey = :skey");
                query.setText("skey", key);
                sessionEntity = (SessionEntity) query.uniqueResult();
            session.getTransaction().commit();
            dao.delete(sessionEntity);
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
    }

    public SessionEntity getByUserId(int userId) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
                Query query = session.createQuery("FROM SessionEntity WHERE userId = :userId");
                query.setInteger("userId", userId);
                SessionEntity sessionEntity = null;
                sessionEntity = (SessionEntity) query.uniqueResult();
            session.getTransaction().commit();
            return sessionEntity;
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
    }

    public int haveKey(String key) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
                Query query = session.createQuery("FROM SessionEntity WHERE skey = :key");
                query.setString("key", key);
                SessionEntity sessionEntity = null;
                sessionEntity = (SessionEntity) query.uniqueResult();
            session.getTransaction().commit();
            if(sessionEntity == null) return -1;
            return sessionEntity.getUserId();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
    }
}
