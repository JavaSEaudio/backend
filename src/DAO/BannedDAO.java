package DAO;

import DAO.util.DAO;
import DAO.util.Factory;
import DAO.util.HibernateUtil;
import Entity.BannedEntity;
import Entity.UserEntity;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class BannedDAO {

    private static DAO dao = Factory.getInstance().getDao();

    public void change(BannedEntity banned) {
        dao.change(banned);
    }

    public void add(BannedEntity banned) {
        dao.add(banned);
    }

    public void delete(BannedEntity banned) {
        dao.delete(banned);
    }

    public void delete(UserEntity user) {
        int userid = user.getId();
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createQuery("FROM BannedEntity WHERE userid = :id");
            query.setInteger("id", userid);
            BannedEntity banEntity = null;
            banEntity = (BannedEntity) query.uniqueResult();
            session.getTransaction().commit();
            dao.delete(banEntity);
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
    }

    public void delete(int id) {
        dao.delete(BannedEntity.class, id);
    }

    public List<BannedEntity> getAll() {
        return dao.getAll(BannedEntity.class);
    }

    public BannedEntity getById(int id) {
        return dao.getById(BannedEntity.class, id);
    }

    public boolean isUserId(int id) {
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createQuery("FROM BannedEntity WHERE userid = :id");
            query.setInteger("id", id);
            BannedEntity banEntity = null;
            banEntity = (BannedEntity) query.uniqueResult();
            session.getTransaction().commit();
            if(banEntity == null) return false;
            return true;
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
    }
}
