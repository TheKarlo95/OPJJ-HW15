package hr.fer.zemris.java.hw15.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.QueryHint;
import javax.persistence.Table;

import hr.fer.zemris.java.hw15.crypto.Sha1Utils;

/**
 * {@code BlogUser} is an {@link Entity} class that is used to as persistent
 * data about a blog user.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see Entity
 * @see Serializable
 */
@Entity
@Table(name = "blog_users")
@NamedQueries({
        @NamedQuery(name = "BlogUser.selectAll", query = "select u from BlogUser as u", hints = {
                @QueryHint(name = "org.hibernate.cacheable", value = "true") }),
        @NamedQuery(name = "BlogUser.selectByNick", query = "select u from BlogUser as u where u.nick=:nick", hints = {
                @QueryHint(name = "org.hibernate.cacheable", value = "true") }),
        @NamedQuery(name = "BlogUser.selectById", query = "select u from BlogUser as u where u.id=:id", hints = {
                @QueryHint(name = "org.hibernate.cacheable", value = "true") })
})
public class BlogUser implements Serializable {

    /** Serial version UID. */
    private static final long serialVersionUID = -4392451828927970411L;

    /** Maximal length of the first name. */
    private static final long FIRST_NAME_MAX_LENGTH = 50;
    /** Maximal length of the last name. */
    private static final long LAST_NAME_MAX_LENGTH = 50;
    /** Maximal length of the nickname. */
    private static final long NICK_MAX_LENGTH = 50;
    /** Maximal length of the e-mail address. */
    private static final long E_MAIL_MAX_LENGTH = 254;

    /**
     * ID of the blog user. This is a auto-incremented number set by database.
     */
    @Id
    @GeneratedValue
    private Long id;

    /** First name of the blog user. */
    @Column(length = 50, nullable = false)
    private String firstName;

    /** Last name of the blog user. */
    @Column(length = 50, nullable = false)
    private String lastName;

    /** Nickname of the blog user. */
    @Column(length = 50, nullable = false, unique = true)
    private String nick;

    /** E-mail of the blog user. */
    @Column(length = 50, nullable = false)
    private String email;

    /** Password of the blog user stored as SHA-1 hash. */
    @Column(length = 50, nullable = false)
    private String passwordHash;

    /** List of all blog entries that this user wrote. */
    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    @OrderBy("createdAt")
    private List<BlogEntry> blogEntries;

    /**
     * Constructs a new {@code BlogUser} with specified first name, last name,
     * nickname, e-mail, password and list of all blog entries that this user
     * posted.
     * <p>
     * <b>Note:</b>Password is stored as a SHA-1 digest.
     * 
     * @param firstName
     *            the first name of the blog user
     * @param lastName
     *            the last name of the blog user
     * @param nick
     *            the nickname of the blog user
     * @param email
     *            the e-mail address of the blog user
     * @param password
     *            the password of the blog user
     * @param blogEntries
     *            blog entries that this user posted
     * @throws NullPointerException
     *             if parameter {@code firstName}, {@code lastName},
     *             {@code nick}, {@code email}, {@code password} or
     *             {@code blogEntries} is a {@code null} reference
     * @throws IllegalArgumentException
     *             if length of the parameter {@code firstName} is greater than
     *             {@value #FIRST_NAME_MAX_LENGTH};<br>
     *             if length of the parameter {@code lastName} is greater than
     *             {@value #LAST_NAME_MAX_LENGTH};<br>
     *             if length of the parameter {@code nick} is greater than
     *             {@value #NICK_MAX_LENGTH};<br>
     *             if length of the parameter {@code email} is greater than
     *             {@value #E_MAIL_MAX_LENGTH}
     */
    public BlogUser(String firstName, String lastName, String nick, String email, String password,
            List<BlogEntry> blogEntries) {
        Objects.requireNonNull(firstName, "You cannot set the first name of the blog user to a null reference.");
        Objects.requireNonNull(lastName, "You cannot set the last name of the blog user to a null reference.");
        Objects.requireNonNull(nick, "You cannot set the nickname of the blog user to a null reference.");
        Objects.requireNonNull(email, "You cannot set the e-mail of the blog user to a null reference.");
        Objects.requireNonNull(password, "You cannot set the password of the blog user to a null reference.");
        Objects.requireNonNull(blogEntries, "You cannot set the blog entries of the blog user to a null reference.");

        if (firstName.length() > FIRST_NAME_MAX_LENGTH)
            throw new IllegalArgumentException(
                    "Length of the first name cannot be greater than " + FIRST_NAME_MAX_LENGTH + ".");
        if (lastName.length() > LAST_NAME_MAX_LENGTH)
            throw new IllegalArgumentException(
                    "Length of the last name cannot be greater than " + LAST_NAME_MAX_LENGTH + ".");
        if (nick.length() > NICK_MAX_LENGTH)
            throw new IllegalArgumentException(
                    "Length of the nickname cannot be greater than " + NICK_MAX_LENGTH + ".");
        if (email.length() > E_MAIL_MAX_LENGTH)
            throw new IllegalArgumentException(
                    "Length of the e-mail cannot be greater than " + E_MAIL_MAX_LENGTH + ".");

        this.firstName = firstName;
        this.lastName = lastName;
        this.nick = nick;
        this.email = email;
        this.passwordHash = Sha1Utils.sha1Hex(password);
        this.blogEntries = blogEntries;
    }

    /**
     * Constructs a new {@code BlogUser} with specified first name, last name,
     * nickname, e-mail and password.
     * <p>
     * <b>Note:</b>Password is stored as a SHA-1 digest.
     * 
     * @param firstName
     *            the first name of the blog user
     * @param lastName
     *            the last name of the blog user
     * @param nick
     *            the nickname of the blog user
     * @param email
     *            the e-mail address of the blog user
     * @param password
     *            the password of the blog user
     * @throws NullPointerException
     *             if parameter {@code firstName}, {@code lastName},
     *             {@code nick}, {@code email} or {@code password} is a
     *             {@code null} reference
     * @throws IllegalArgumentException
     *             if length of the parameter {@code firstName} is greater than
     *             {@value #FIRST_NAME_MAX_LENGTH};<br>
     *             if length of the parameter {@code lastName} is greater than
     *             {@value #LAST_NAME_MAX_LENGTH};<br>
     *             if length of the parameter {@code nick} is greater than
     *             {@value #NICK_MAX_LENGTH};<br>
     *             if length of the parameter {@code email} is greater than
     *             {@value #E_MAIL_MAX_LENGTH}
     */
    public BlogUser(String firstName, String lastName, String nick, String email, String password) {
        this(firstName, lastName, nick, email, password, new ArrayList<>());
    }

    /**
     * Constructs a new empty {@code BlogUser}.
     */
    protected BlogUser() {
    }

    /**
     * Returns the ID of the blog entry.
     * 
     * @return the ID of the blog entry
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the blog entry.
     * 
     * @param id
     *            new ID of the blog entry
     * @throws NullPointerException
     *             if parameter {@code id} is a {@code null} reference
     */
    public void setId(Long id) {
        this.id = Objects.requireNonNull(id, "You cannot set the ID of the blog entry to a null reference.");
    }

    /**
     * Returns the first name of the blog user.
     * 
     * @return the first name of the blog user
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the blog user.
     * 
     * @param firstName
     *            new first name of the blog user
     */
    public void setFirstName(String firstName) {
        Objects.requireNonNull(firstName, "You cannot set the first name of the blog user to a null reference.");
        if (firstName.length() > FIRST_NAME_MAX_LENGTH)
            throw new IllegalArgumentException(
                    "Length of the first name cannot be greater than " + FIRST_NAME_MAX_LENGTH + ".");

        this.firstName = firstName;
    }

    /**
     * Returns the last name of the blog user.
     * 
     * @return the last name of the blog user
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the blog user.
     * 
     * @param lastName
     *            new last name of the blog user
     */
    public void setLastName(String lastName) {
        Objects.requireNonNull(lastName, "You cannot set the last name of the blog user to a null reference.");
        if (firstName.length() > LAST_NAME_MAX_LENGTH)
            throw new IllegalArgumentException(
                    "Length of the last name cannot be greater than " + LAST_NAME_MAX_LENGTH + ".");

        this.lastName = lastName;
    }

    /**
     * Returns the nick of the blog user.
     * 
     * @return the nick of the blog user
     */
    public String getNick() {
        return nick;
    }

    /**
     * Sets the nick of the blog user.
     * 
     * @param nick
     *            new nick of the blog user
     */
    public void setNick(String nick) {
        Objects.requireNonNull(firstName, "You cannot set the nickname of the blog user to a null reference.");
        if (nick.length() > NICK_MAX_LENGTH)
            throw new IllegalArgumentException(
                    "Length of the nickname cannot be greater than " + NICK_MAX_LENGTH + ".");

        this.nick = nick;
    }

    /**
     * Returns the e-mail of the blog user.
     * 
     * @return the e-mail of the blog user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the e-mail of the blog user.
     * 
     * @param email
     *            new e-mail of the blog user
     */
    public void setEmail(String email) {
        Objects.requireNonNull(email, "You cannot set the e-mail of the blog user to a null reference.");
        if (email.length() > E_MAIL_MAX_LENGTH)
            throw new IllegalArgumentException(
                    "Length of the e-mail cannot be greater than " + E_MAIL_MAX_LENGTH + ".");

        this.email = email;
    }

    /**
     * Returns the hashed password of the blog user.
     * 
     * @return the hashed password of the blog user
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Sets the hashed password of the blog user.
     * 
     * @param passwordHash
     *            new hashed password of the blog user
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = Objects
                .requireNonNull(passwordHash, "You cannot set the password hash of the blog user to a null reference.");
    }

    /**
     * Returns the list of all bog entries posted by this blog user.
     * 
     * @return the list of all bog entries posted by this blog user
     */
    public List<BlogEntry> getBlogEntries() {
        return blogEntries;
    }

    /**
     * Sets the list of all bog entries posted by this blog user.
     * 
     * @param blogEntries
     *            new list of all bog entries posted by this blog user
     */
    public void setBlogEntries(List<BlogEntry> blogEntries) {
        this.blogEntries = Objects
                .requireNonNull(blogEntries, "You cannot set the blog entries of the blog user to a null reference.");
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nick == null) ? 0 : nick.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BlogUser other = (BlogUser) obj;
        if (nick == null) {
            if (other.getNick() != null)
                return false;
        } else if (!nick.equals(other.getNick()))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "BlogUser [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", nick=" + nick
                + ", e-mail=" + email + ", passwordHash=" + passwordHash + "]";
    }

}