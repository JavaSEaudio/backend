package db;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class AudiofileOperation{

    private SessionFactory ourSessionFactory;

    public AudiofileOperation() {
        Configuration configuration = new Configuration();
        configuration.configure();

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        ourSessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }



    public void add(AudiofileEntity audio) {
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

    public void delete(AudiofileEntity audio) {
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
    public List<AudiofileEntity> getAll() {
        Session session = null;
        List<AudiofileEntity> user = new ArrayList<AudiofileEntity>();
        try {
            session = ourSessionFactory.openSession();
            user = (List<AudiofileEntity>) session.createCriteria(UserEntity.class).list();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return user;
    }

    public AudiofileEntity getById(long id) {
        Session session = null;
        AudiofileEntity res = null;
        try {
            session = ourSessionFactory.openSession();
            res = (AudiofileEntity) session.get(AudiofileEntity.class, id);
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return res;
    }
}
