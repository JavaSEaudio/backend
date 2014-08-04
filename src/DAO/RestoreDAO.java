package DAO;

import DAO.util.DAO;
import DAO.util.Factory;
import DAO.util.HibernateUtil;
import Entity.RestoreEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import java.util.List;

public class RestoreDAO {

    private static DAO dao = Factory.getInstance().getDao();

    public void change(RestoreEntity audio) {
        dao.change(audio);
    }


    public void add(RestoreEntity audio) {
        dao.add(audio);
    }

    public void delete(RestoreEntity audio) {
        dao.delete(audio);
    }


    public void delete(int id) {
        dao.delete(RestoreEntity.class, id);
    }

    public List<RestoreEntity> getAll() {
        return dao.getAll(RestoreEntity.class);
    }

    public RestoreEntity getById(int id) {
        return dao.getById(RestoreEntity.class, id);
    }

    public RestoreEntity getByUserID(int id) {
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        RestoreEntity audio;
        try {
            session.beginTransaction();
            Query query = session.createQuery("from RestoreEntity WHERE userid = :id");
            query.setInteger("id", id);
            audio = (RestoreEntity) query.uniqueResult();
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return audio;
    }

    public RestoreEntity getUniq(String uiq) {
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        RestoreEntity audio;
        try {
            session.beginTransaction();
            Query query = session.createQuery("from RestoreEntity WHERE uniq = :id");
            query.setString("id", uiq);
            audio = (RestoreEntity) query.uniqueResult();
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return audio;
    }
}
