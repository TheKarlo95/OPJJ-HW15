package hr.fer.zemris.java.hw15.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * {@code JPAEMFProvider} is a class that stores entity manager factory which
 * can be used by multiple classes.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see EntityManagerFactory
 */
public class JPAEMFProvider {

    /** Entity manager factory for the persistence unit. */
    public static EntityManagerFactory emf;

    /**
     * Returns the entity manager factory.
     * 
     * @return the entity manager factory
     */
    public static EntityManagerFactory getEmf() {
        return emf;
    }

    /**
     * Sets the entity manager factory.
     * 
     * @param emf
     *            the entity manager factory
     */
    public static void setEmf(EntityManagerFactory emf) {
        JPAEMFProvider.emf = emf;
    }
    
}