package DAO.util;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
    private static HibernateUtil instance = null;
    private static SessionFactory ourSessionFactory = null;
    private HibernateUtil(){}

    public static synchronized HibernateUtil getInstance(){
        if (instance == null){
            instance = new HibernateUtil();
        }
        return instance;
    }

    public SessionFactory getSessionFactory() {
        if(ourSessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                configuration.configure();
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
                ourSessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Throwable ex) {
                throw new ExceptionInInitializerError(ex);
            }
        }
        return ourSessionFactory;
    }
}
