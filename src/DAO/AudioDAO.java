package DAO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import DAO.util.DAO;
import DAO.util.Factory;
import DTO.AlbumsDTO;
import Entity.AudioEntity;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import DAO.util.HibernateUtil;
import rest.file.Audio;

public class AudioDAO {
    private static DAO dao = Factory.getInstance().getDao();

    public void change(AudioEntity audio) {
        dao.change(audio);
    }


    public void add(AudioEntity audio) {
        Calendar calendar = Calendar.getInstance();
        audio.setUpload_date(calendar.getTime());
        dao.add(audio);
    }

    public void delete(AudioEntity audio) {
        dao.delete(audio);
    }


    public void delete(int id) {
        dao.delete(AudioEntity.class, id);
    }

    public List<AudioEntity> getAll() {
        return dao.getAll(AudioEntity.class);
    }

    public AudioEntity getById(int id) {
        return dao.getById(AudioEntity.class, id);
    }

    public Collection<AudioEntity> getSomeAudios(int first, int second) {
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        List<AudioEntity> audio = new ArrayList<AudioEntity>();
        List<AudioEntity> result = new ArrayList<AudioEntity>();
        try {
            session.beginTransaction();
            Query query = session.createQuery("from AudioEntity order by upload_date DESC");
            query.setFirstResult(first);
            query.setMaxResults(second);
            audio = query.list();
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return audio;
    }

    public Collection<AudioEntity> search(String some, int first, int second) {
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        List<AudioEntity> audio = new ArrayList<AudioEntity>();
        List<AudioEntity> result = new ArrayList<AudioEntity>();
        try {
            session.beginTransaction();
            Query query = session.createQuery("from AudioEntity where name LIKE :some OR " +
                                                "artist LIKE :some OR album LIKE :some ");
            some = "%"+some+"%";
            query.setString("some", some);
            query.setFirstResult(first);
            query.setMaxResults(second);
            audio = query.list();
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return audio;
    }

    public List<AudioEntity> getByYear(int year) {
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        List<AudioEntity> audio = null;
        try {
            session.beginTransaction();
            Query query = session.createQuery("FROM AudioEntity WHERE year = :year");
            query.setInteger("year", year);
            session.getTransaction().commit();
            audio = (List<AudioEntity>) query.list();
        } catch (Exception e){
            System.err.println("Trouble");
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return audio;
    }

    public List<AudioEntity> getByUserId(int id) {
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        List<AudioEntity> audio = null;
        try {
            session.beginTransaction();
            Query query = session.createQuery("FROM AudioEntity WHERE userid = :id");
            query.setInteger("id", id);
            session.getTransaction().commit();
            audio = (List<AudioEntity>) query.list();
        } catch (Exception e){
            System.err.println("Trouble");
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return audio;
    }

    public List<AudioEntity> getByGenre(String genre) {
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        List<AudioEntity> audio = null;
        try {
            session.beginTransaction();
            Query query = session.createQuery("FROM AudioEntity WHERE genre = :genre");
            query.setString("genre", genre);
            session.getTransaction().commit();
            audio = (List<AudioEntity>) query.list();
        } catch (Exception e){
            System.err.println("Trouble");
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return audio;
    }

    public List<AudioEntity> getByArtist(String artist) {
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        List<AudioEntity> audio = new ArrayList<AudioEntity>();
        try {
            session.beginTransaction();
            Query query = session.createQuery("FROM AudioEntity WHERE artist LIKE :artist");
            artist = "%"+artist+"%";
            query.setString("artist", artist);
            session.getTransaction().commit();
            audio = query.list();
        } catch (Exception e){
            System.out.println("Trouble");
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return audio;
    }

    public List<AudioEntity> getByName(String name) {
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        List<AudioEntity> audio = new ArrayList<AudioEntity>();
        try {
            session.beginTransaction();
            Query query = session.createQuery("FROM AudioEntity WHERE name LIKE :name");
            name = "%"+name+"%";
            query.setString("name", name);
            session.getTransaction().commit();
            audio = query.list();
        } catch (Exception e){
            System.out.println("Trouble");
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return audio;
    }

    public List<AudioEntity> getByAlbum(String album) {
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        List<AudioEntity> audio = new ArrayList<AudioEntity>();
        try {
            session.beginTransaction();
            Query query = session.createQuery("FROM AudioEntity WHERE album like :album");
            album = "%"+album+"%";
            query.setString("album", album);
            session.getTransaction().commit();
            audio = query.list();
        } catch (Exception e){
            System.out.println("Trouble");
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return audio;
    }

    public List<AudioEntity> getFree(int first, int second) {
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        List<AudioEntity> audio = new ArrayList<AudioEntity>();
        try {
            session.beginTransaction();
            Query query = session.createQuery("FROM AudioEntity WHERE price = :price");
            query.setDouble("price", 0);
            query.setFirstResult(first);
            query.setMaxResults(second);
            audio = (List<AudioEntity>) query.list();
            session.getTransaction().commit();
        } catch (Exception e){
            System.out.println("Trouble");
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return audio;
    }

    public List<String> getArtistAll(int first, int second) {
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        List<String> audio = new ArrayList<String>();
        try {
            session.beginTransaction();
            Query query = session.createQuery("SELECT DISTINCT artist FROM AudioEntity");
            query.setFirstResult(first);
            query.setMaxResults(second);
            audio = (List<String>) query.list();
            session.getTransaction().commit();
        } catch (Exception e){
            System.out.println("Trouble");
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return audio;
    }

    public List<AudioEntity> getbyArtistTracks(String artist, int first, int second) {
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        List<AudioEntity> audio = new ArrayList<AudioEntity>();
        try {
            session.beginTransaction();
            Query query = session.createQuery("SELECT artist FROM AudioEntity WHERE artist = :artist");
            query.setString("artist", artist);
            query.setFirstResult(first);
            query.setMaxResults(second);
            audio = (List<AudioEntity>) query.list();
            session.getTransaction().commit();
        } catch (Exception e){
            System.out.println("Trouble");
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return audio;
    }

    public List<String> getbyArtistAlbum(String artist, int first, int second) {
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        List<String> audio = new ArrayList<String>();
        try {
            session.beginTransaction();
            Query query = session.createQuery("SELECT DISTINCT album FROM AudioEntity WHERE artist = :artist");
            query.setString("artist", artist);
            query.setFirstResult(first);
            query.setMaxResults(second);
            audio = (List<String>) query.list();
            session.getTransaction().commit();
        } catch (Exception e){
            System.out.println("Trouble");
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return audio;
    }

    public List<AlbumsDTO> getAlbums(int first, int second) {
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        List<AlbumsDTO> audio = new ArrayList<AlbumsDTO>();
        try {
            session.beginTransaction();
            Query query = session.createQuery("select distinct album, artist FROM AudioEntity ");
            query.setFirstResult(first);
            query.setMaxResults(second);

            System.out.println(query.list());
            session.getTransaction().commit();
        } catch (Exception e){
            System.out.println("Trouble");
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return audio;
    }

    public List<AudioEntity> getbyAlbumTracks(String album, String artist, int first, int second) {
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        List<AudioEntity> audio = new ArrayList<AudioEntity>();
        try {
            session.beginTransaction();
            Query query = session.createQuery("FROM AudioEntity WHERE album = :album AND artist = :artist");
            query.setString("album", album);
            query.setString("artist", artist);
            query.setFirstResult(first);
            query.setMaxResults(second);
            audio = (List<AudioEntity>) query.list();
            session.getTransaction().commit();
        } catch (Exception e){
            System.out.println("Trouble");
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return audio;
    }
}
