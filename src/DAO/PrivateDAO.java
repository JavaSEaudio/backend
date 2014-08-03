package DAO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import DAO.util.DAO;
import DAO.util.Factory;
import Entity.PrivateEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import DAO.util.HibernateUtil;

public class PrivateDAO {
    private static DAO dao = Factory.getInstance().getDao();

    public void change(PrivateEntity audio) {
        dao.change(audio);
    }


    public void add(PrivateEntity audio) {
        Calendar calendar = Calendar.getInstance();
        audio.setUpload_date(calendar.getTime());
        dao.add(audio);
    }

    public void delete(PrivateEntity audio) {
        dao.delete(audio);
    }


    public void delete(int id) {
        dao.delete(PrivateEntity.class, id);
    }

    public List<PrivateEntity> getAll() {
        return dao.getAll(PrivateEntity.class);
    }

    public PrivateEntity getById(int id) {
        return dao.getById(PrivateEntity.class, id);
    }

    public Collection<PrivateEntity> getSomePrivates(int first, int second, int id) {
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        List<PrivateEntity> audio = new ArrayList<PrivateEntity>();
        try {
            session.beginTransaction();
            Query query = session.createQuery("from PrivateEntity WHERE userid = :id order by upload_date DESC");
            query.setFirstResult(first);
            query.setMaxResults(second);
            query.setInteger("id", id);
            audio = query.list();
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return audio;
    }
}
