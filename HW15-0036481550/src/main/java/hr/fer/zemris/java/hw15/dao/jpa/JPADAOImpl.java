package hr.fer.zemris.java.hw15.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.hw15.dao.DAO;
import hr.fer.zemris.java.hw15.dao.DAOException;
import hr.fer.zemris.java.hw15.entities.BlogEntry;
import hr.fer.zemris.java.hw15.entities.BlogUser;

/**
 * {@code JPADAOImpl} is a class that offers some methods to work with a
 * object-relational database.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see DAO
 */
public class JPADAOImpl implements DAO {

    @Override
    public List<BlogUser> getAllBlogUsers() {
        EntityManager em = JPAEMProvider.getEntityManager();

        @SuppressWarnings("unchecked")
        List<BlogUser> users = (List<BlogUser>) em.createNamedQuery("BlogUser.selectAll")
                .getResultList();

        JPAEMProvider.close();
        return users;
    }

    @Override
    public BlogUser getBlogUser(String nick) {
        EntityManager em = JPAEMProvider.getEntityManager();

        @SuppressWarnings("unchecked")
        List<BlogUser> users = (List<BlogUser>) em.createNamedQuery("BlogUser.selectByNick")
                .setParameter("nick", nick)
                .getResultList();
        
        JPAEMProvider.close();
        return users.size() == 1 ? users.get(0) : null;
    }

    @Override
    public BlogUser getBlogUser(Long id) {
        BlogUser user = JPAEMProvider.getEntityManager().find(BlogUser.class, id);
        JPAEMProvider.close();
        return user;
    }

    @Override
    public List<BlogEntry> getAllBlogEntries() {
        EntityManager em = JPAEMProvider.getEntityManager();

        @SuppressWarnings("unchecked")
        List<BlogEntry> users = (List<BlogEntry>) em.createNamedQuery("BlogEntry.selectAll")
                .getResultList();

        JPAEMProvider.close();
        return users;
    }

    @Override
    public List<BlogEntry> getAllUserEntries(BlogUser user) {
        EntityManager em = JPAEMProvider.getEntityManager();

        @SuppressWarnings("unchecked")
        List<BlogEntry> users = (List<BlogEntry>) em.createNamedQuery("BlogEntry.selectByCreator")
                .setParameter("creator", user)
                .getResultList();

        JPAEMProvider.close();
        return users;
    }

    @Override
    public BlogEntry getBlogEntry(Long id) throws DAOException {
        BlogEntry entry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
        JPAEMProvider.close();
        return entry;
    }

}