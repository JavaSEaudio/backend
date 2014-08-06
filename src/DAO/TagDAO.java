package DAO;

import DAO.util.DAO;
import DAO.util.Factory;
import Entity.AudioEntity;
import Entity.LikeEntity;
import Entity.SessionEntity;
import Entity.TagEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import DAO.util.HibernateUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class TagDAO {
    private static DAO dao = Factory.getInstance().getDao();

    public void add(TagEntity tagEntity) {
        dao.add(tagEntity);
    }
    public void change(TagEntity tag) {
        dao.change(tag);
    }

    public void delete(TagEntity tagEntity) {
        dao.delete(tagEntity);
    }

    public TagEntity getById(int id) {
        return dao.getById(TagEntity.class, id);
    }

    public List<TagEntity> getAll() {
        return dao.getAll(TagEntity.class);
    }

    public TagEntity getByName(String name) {
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        TagEntity tag = null;
        try {
            session.beginTransaction();
            Query query = session.createQuery("FROM TagEntity WHERE name = :name");
            query.setString("name", name);
            session.getTransaction().commit();
            tag = (TagEntity) query.uniqueResult();
        } catch (Exception e){
            System.out.println("Trouble");
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return tag;
    }

    public List<TagEntity> getPopularTags(int first, int second) {
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        List<TagEntity> tag = new ArrayList<TagEntity>();
        try {
            session.beginTransaction();
            Query query = session.createQuery("from TagEntity order by counts desc");
            query.setFirstResult(first);
            query.setMaxResults(second);
            tag = query.list();
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return tag;
    }

    public TagEntity tagSearch(String some) {
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        TagEntity tag = new TagEntity();
        List<TagEntity> result = new ArrayList<TagEntity>();
        try {
            session.beginTransaction();
            Query query = session.createQuery("from TagEntity where name =:some ");
            //some = "%"+some+"%";
            query.setString("some", some);
            tag = (TagEntity)query.uniqueResult();
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return tag;
    }
}
