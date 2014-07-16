package DAO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import Entity.AudioEntity;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import util.HibernateUtil;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.EntityManagerFactory;
public class AudioDAO {
    private SessionFactory ourSessionFactory;

    public AudioDAO() {
        Configuration configuration = new Configuration();
        configuration.configure();

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        ourSessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    /**
     * НЕ ЮЗАТЬ!!! НЕ РАБОТАЕТ!!!
     */
    public void change(AudioEntity audio) {
        Session session = null;

        try {
            session = ourSessionFactory.openSession();
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
            Calendar cal = Calendar.getInstance();
            audio.setUpload_date(cal.getTime());
            session = ourSessionFactory.openSession();
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
            session = ourSessionFactory.openSession();
            session.beginTransaction();
            session.delete(audio);
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
    }

    public Collection<AudioEntity> getLastTenAudio() {
        Session session = null;
        List<AudioEntity> audio = new ArrayList<AudioEntity>();
        List<AudioEntity> result = new ArrayList<AudioEntity>();
        try {
            session = ourSessionFactory.openSession();
            SQLQuery query = session.createSQLQuery("select * from audiofile");
            //SQLQuery query = session.createSQLQuery("order by upload_date");
            query.addEntity(AudioEntity.class);
            audio = query.list();

//            for (int i = 0; i < 10; i ++) {
//                //audio.add(getById(i));
//            }
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
    //@SuppressWarnings("unchecked")
    public List<AudioEntity> getAll() {
        Session session = null;
        List<AudioEntity> user = new ArrayList<AudioEntity>();
        try {
            session = ourSessionFactory.openSession();
            user = (List<AudioEntity>) session.createCriteria(AudioEntity.class).list();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return user;
    }

    /**
     * МОГУТ ВОЗНИКАТЬ ОШИБКИ
     */
    public AudioEntity getById(long id) {
        Session session = null;
        AudioEntity res = null;
        try {
            session = ourSessionFactory.openSession();
            res = (AudioEntity) session.get(AudioEntity.class, id);
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return res;
    }
}
