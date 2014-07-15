package db;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class UserOperation {

    private SessionFactory ourSessionFactory;

    public UserOperation() {
        Configuration configuration = new Configuration();
        configuration.configure();

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        ourSessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }



    public void add(UserEntity user) {
        Session session = null;
        try {
            session = ourSessionFactory.openSession();
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
    }

    public void delete(UserEntity user) {
        Session session = null;
        try {
            session = ourSessionFactory.openSession();
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
    }

    //@SuppressWarnings("unchecked")
    public List<UserEntity> getAll() {
        Session session = null;
        List<UserEntity> user = new ArrayList<UserEntity>();
        try {
            session = ourSessionFactory.openSession();
            user = (List<UserEntity>) session.createCriteria(UserEntity.class).list();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return user;
    }

    public UserEntity getById(long id) {
        Session session = null;
        UserEntity res = null;
        try {
            session = ourSessionFactory.openSession();
            res = (UserEntity) session.get(UserEntity.class, id);
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return res;
    }
}