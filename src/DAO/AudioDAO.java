package DAO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import Entity.AudioEntity;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import util.HibernateUtil;

public class AudioDAO {

    public void change(AudioEntity audio) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(audio);
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
    }


    public void add(AudioEntity audio) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(audio);
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
    }

    public void delete(AudioEntity audio) {
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

    public void delete(int id) {
        Session session = null;
        AudioEntity audio = this.getById(id);
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

    //@SuppressWarnings("unchecked")
    public List<AudioEntity> getAll() {
        Session session = null;
        List<AudioEntity> user = new ArrayList<AudioEntity>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            user = (List<AudioEntity>) session.createCriteria(AudioEntity.class).list();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return user;
    }

    public AudioEntity getById(int id) {
        Session session = null;
        AudioEntity res = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            res = ((AudioEntity) session.get(AudioEntity.class, id));
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return res;
    }
    public Collection<AudioEntity> getLastTenAudio() {
        Session session = null;
        List<AudioEntity> audio = new ArrayList<AudioEntity>();
        List<AudioEntity> result = new ArrayList<AudioEntity>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            SQLQuery query = session.createSQLQuery("select * from audiofile" + " limit 10");
            query.addEntity(AudioEntity.class);
            audio = query.list();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        for (int i = audio.size() - 1; i > audio.size() - 11; i --) {
            if (i < 0) return result;
            else {
                result.add(audio.get(i));
            }
        }
        return result;
    }
}
