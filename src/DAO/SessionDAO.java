package DAO;

import Entity.SessionEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;

public class SessionDAO {

    public void add(SessionEntity sessionEntity) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(sessionEntity);
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
    }

    public void delete(SessionEntity audio) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(audio);
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
    }

    public void delete(int userId) {
        Session session = null;
        SessionEntity audio = this.getByUserId(userId);
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(audio);
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
    }

    public void delete(String key) {
        Session session = null;
        SessionEntity audio = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
                Query query = session.createQuery("FROM SessionEntity WHERE skey = :skey");
                query.setText("skey", key);
                audio = (SessionEntity) query.uniqueResult();
            session.getTransaction().commit();
            session.beginTransaction();
                session.delete(audio);
            session.getTransaction().commit();
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

    //@SuppressWarnings("unchecked")
    public List<SessionEntity> getAll() {
        Session session = null;
        List<SessionEntity> sessionEntities = new ArrayList<SessionEntity>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            sessionEntities = (List<SessionEntity>) session.createCriteria(SessionEntity.class).list();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return sessionEntities;
    }


}
