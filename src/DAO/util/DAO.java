package DAO.util;

import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class DAO {

    public <T> T change(final T object) {
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.update(object);
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return null;
    }


    public <T> T add(final T object) {
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(object);
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return null;
    }

    public <T> T delete(final T object){
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.delete(object);
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return null;
    }

    public <T> T getById(final Class<T> type, final int id){
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        T res = null;
        try {
            res = ((T) session.get(type, id));
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return res;
    }

    public <T> T delete(final Class<T> type, int id) {
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        T some = this.getById(type, id);
        try {
            session.beginTransaction();
            session.delete(some);
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return some;
    }

    public <T> List<T> getAll(final Class<T> type) {
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        List<T> some = new ArrayList<T>();
        try {
            some = (List<T>) session.createCriteria(type).list();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return some;
    }
}
