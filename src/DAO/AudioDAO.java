package DAO;

import java.util.ArrayList;
import java.util.List;

import Entity.AudioEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

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
    public AudioEntity getById(int id) {
        Session session = null;
        AudioEntity res = null;
        try {
            session = ourSessionFactory.openSession();
            res = ((AudioEntity) session.get(AudioEntity.class, id));
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return res;
    }
}
