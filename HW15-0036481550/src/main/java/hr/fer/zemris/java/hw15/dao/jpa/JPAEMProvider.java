package hr.fer.zemris.java.hw15.dao.jpa;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.hw15.dao.DAOException;

/**
 * {@code JPAEMProvider} provides thread-local entity manager of database.
 * <p>
 * These entity manager differ from their normal counterparts in that each
 * thread that accesses one (via its {@code get} or {@code set} method) has its
 * own, independently initialized copy of the entity manager.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 */
public class JPAEMProvider {

    /** Thread-local entity manager. */
    private static ThreadLocal<LocalData> LOCALS = new ThreadLocal<>();

    /**
     * Returns the entity manager of the database in the current thread's copy
     * of this thread-local connection.
     * 
     * @return the current thread's entity manager of the database
     */
    public static EntityManager getEntityManager() {
        LocalData ldata = LOCALS.get();
        if (ldata == null) {
            ldata = new LocalData();
            ldata.em = JPAEMFProvider.getEmf().createEntityManager();
            ldata.em.getTransaction().begin();
            LOCALS.set(ldata);
        }
        return ldata.em;
    }

    /**
     * Closes the the entity manager of the database of the current thread.
     * 
     * @throws DAOException
     *             if some exception occurs
     */
    public static void close() throws DAOException {
        LocalData ldata = LOCALS.get();
        if (ldata == null) {
            return;
        }
        DAOException dex = null;
        try {
            ldata.em.getTransaction().commit();
        } catch (Exception ex) {
            dex = new DAOException("Unable to commit transaction.", ex);
        }
        try {
            ldata.em.close();
        } catch (Exception ex) {
            if (dex != null) {
                dex = new DAOException("Unable to close entity manager.", ex);
            }
        }
        LOCALS.remove();
        if (dex != null)
            throw dex;
    }

    /**
     * {@code LocalData} is wrapper class for the {@code EntityManager}.
     * 
     * @author Karlo Vrbić
     * @version 1.0
     * @see EntityManager
     */
    private static class LocalData {

        /** Entity manager. */
        EntityManager em;

    }

}