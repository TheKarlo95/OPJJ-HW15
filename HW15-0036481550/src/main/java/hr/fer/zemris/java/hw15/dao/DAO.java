package hr.fer.zemris.java.hw15.dao;

import java.util.List;

import hr.fer.zemris.java.hw15.entities.BlogEntry;
import hr.fer.zemris.java.hw15.entities.BlogUser;

/**
 * {@code DAO}(Direct Access Object) is an interface of a persistent data
 * subsystem.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 */
public interface DAO {

    /**
     * Returns the list of all blog users.
     * 
     * @return the list of all blog users
     * @throws DAOException
     *             if some exception occurs
     */
    public List<BlogUser> getAllBlogUsers() throws DAOException;

    /**
     * Returns the blog user with the specified {@code nick}. If that blog user
     * doesn't exist {@code null} is returned.
     * 
     * @param nick
     *            user nick
     * @return blog user or {@code null} if user doesn't exist
     * @throws DAOException
     *             if some exception occurs
     */
    public BlogUser getBlogUser(String nick) throws DAOException;

    /**
     * Returns the blog user with the specified {@code id}. If that blog user
     * doesn't exist {@code null} is returned.
     * 
     * @param id
     *            user id
     * @return blog user or {@code null} if user doesn't exist
     * @throws DAOException
     *             if some exception occurs
     */
    public BlogUser getBlogUser(Long id) throws DAOException;

    /**
     * Returns the list of all blog entries.
     * 
     * @return the list of all blog entries
     * @throws DAOException
     *             if some exception occurs
     */
    public List<BlogEntry> getAllBlogEntries() throws DAOException;

    /**
     * Returns the list of all blog entries with specified {@code user} as a
     * creator.
     * 
     * @param user
     *            user that created blog entries
     * @return the list of all blog entries with specified {@code user}
     * @throws DAOException
     *             if some exception occurs
     */
    public List<BlogEntry> getAllUserEntries(BlogUser user) throws DAOException;

    /**
     * Returns the blog entry with the specified {@code id}. If that blog entry
     * doesn't exist {@code null} is returned.
     * 
     * @param id
     *            entry id
     * @return blog entry or {@code null} if entry doesn't exist
     * @throws DAOException
     *             if some exception occurs
     */
    public BlogEntry getBlogEntry(Long id) throws DAOException;

}