package DAO;

import DAO.util.DAO;
import DAO.util.Factory;
import DAO.util.HibernateUtil;
import Entity.CommentsEntity;
import Entity.LikeEntity;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class CommentsDAO {
    private static DAO dao = Factory.getInstance().getDao();

    public void change(CommentsEntity comment) {
        dao.change(comment);
    }

    public void add(CommentsEntity comment) {
        dao.add(comment);
    }

    public void delete(CommentsEntity comment) {
        dao.delete(comment);
    }

    public void delete(int id) {
        dao.delete(CommentsEntity.class, id);
    }

    public List<CommentsEntity> getAll() {
        return dao.getAll(CommentsEntity.class);
    }

    public CommentsEntity getById(int id) {
        return dao.getById(CommentsEntity.class, id);
    }

    public List<CommentsEntity> getByAudio(int id) {
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createQuery("FROM CommentsEntity WHERE audio = :audio");
            query.setInteger("audio", id);
            List<CommentsEntity> commentEntity = null;
            commentEntity = (List<CommentsEntity>) query.list();
            session.getTransaction().commit();
            return commentEntity;
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
    }
}
