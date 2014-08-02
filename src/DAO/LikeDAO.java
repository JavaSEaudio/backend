package DAO;

import DAO.util.Factory;
import DAO.util.DAO;
import DAO.util.HibernateUtil;
import Entity.LikeEntity;
import Entity.SessionEntity;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class LikeDAO {

    private static DAO dao = Factory.getInstance().getDao();

    public void change(LikeEntity user) {
        dao.change(user);
    }

    public void add(LikeEntity user) {
        dao.add(user);
    }

    public void delete(LikeEntity user) {
        dao.delete(user);
    }

    public void delete(int id) {
        dao.delete(LikeEntity.class, id);
    }

    public List<LikeEntity> getAll() {
        return dao.getAll(LikeEntity.class);
    }

    public LikeEntity getById(int id) {
        return dao.getById(LikeEntity.class, id);
    }

    public LikeEntity getByAudio(int id) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createQuery("FROM LikeEntity WHERE audioId = :audioId");
            query.setInteger("audioId", id);
            LikeEntity likeEntity = null;
            likeEntity = (LikeEntity) query.uniqueResult();
            session.getTransaction().commit();
            return likeEntity;
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
    }

    public List<LikeEntity> getMostLikes(int first, int second) {
        Session session = null;
        List<LikeEntity> like = new ArrayList<LikeEntity>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createQuery("from LikeEntity order by counts desc");
            query.setFirstResult(first);
            query.setMaxResults(second);
            like = query.list();
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return like;
    }

}
