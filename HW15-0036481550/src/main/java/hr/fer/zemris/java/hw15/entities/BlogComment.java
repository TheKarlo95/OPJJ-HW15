package hr.fer.zemris.java.hw15.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * {@code BlogComment} is an {@link Entity} class that is used to as persistent
 * data about a blog comment.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see Entity
 * @see Serializable
 */
@Entity
@Table(name = "blog_comments")
public class BlogComment implements Serializable {

    /** Serial version UID. */
    private static final long serialVersionUID = -6500568965097688124L;

    /** Maximal length of the users e-mail. */
    private static final int E_MAIL_MAX_LENGTH = 254;

    /** Maximal length of the blog comment message. */
    private static final int MESSAGE_MAX_LENGTH = 2048;

    /**
     * ID of the blog comment. This is a auto-incremented number set by
     * database.
     */
    @Id
    @GeneratedValue
    private Long id;

    /** Blog entry that this comment refers to. */
    @ManyToOne
    @JoinColumn(nullable = false)
    private BlogEntry blogEntry;

    /** E-mail of the user that created this comment. */
    @Column(nullable = false, length = E_MAIL_MAX_LENGTH)
    private String usersEMail;

    /** Message that was posted as a comment of the blog entry. */
    @Column(nullable = false, length = MESSAGE_MAX_LENGTH)
    private String message;

    /** Date and time when this blog entry was posted. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date postedOn;

    /**
     * Constructs a new {@code BlogComment} with specified blog entry, users
     * e-mail, message and date this comment was posted.
     * 
     * @param blogEntry
     *            the blog entry that this comment refers to
     * @param usersEMail
     *            the e-mail of the user that created this comment
     * @param message
     *            the message that was posted as a comment of the blog entry
     * @param postedOn
     *            the date and time when this blog entry was posted
     * @throws NullPointerException
     *             if parameter {@code id}, {@code blogEntry},
     *             {@code usersEMail}, {@code message} or {@code postedOn} is a
     *             {@code null} reference
     * @throws IllegalArgumentException
     *             if length of the parameter {@code usersEMail} is greater than
     *             {@value #E_MAIL_MAX_LENGTH};<br>
     *             if length of the parameter {@code message} is greater than
     *             {@value #MESSAGE_MAX_LENGTH}
     */
    public BlogComment(BlogEntry blogEntry, String usersEMail, String message, Date postedOn) {
        Objects.requireNonNull(blogEntry, "You cannot set the blog entry of the blog comment to a null reference.");
        Objects.requireNonNull(usersEMail, "You cannot set the users e-mail of the blog comment to a null reference.");
        Objects.requireNonNull(message, "You cannot set the message of the blog comment to a null reference.");
        Objects.requireNonNull(
                postedOn,
                "You cannot set the date when this blog comment was posted of the blog comment to a null reference.");

        if (usersEMail.length() > E_MAIL_MAX_LENGTH)
            throw new IllegalArgumentException(
                    "Length of users e-mail cannot be greater than " + MESSAGE_MAX_LENGTH + ".");
        if (message.length() > MESSAGE_MAX_LENGTH)
            throw new IllegalArgumentException(
                    "Length of the message of the blog comment cannot be greater than " + MESSAGE_MAX_LENGTH + ".");

        this.blogEntry = blogEntry;
        this.usersEMail = usersEMail;
        this.message = message;
        this.postedOn = postedOn;
    }

    /**
     * Constructs a new {@code BlogComment} with specified blog entry, users
     * e-mail and message.
     * <p>
     * The attribute {@code postedOn} is set to {@link Date#Date()}.
     * 
     * @param blogEntry
     *            the blog entry that this comment refers to
     * @param usersEMail
     *            the e-mail of the user that created this comment
     * @param message
     *            the message that was posted as a comment of the blog entry
     * @throws NullPointerException
     *             if parameter {@code blogEntry}, {@code usersEMail} or
     *             {@code message} is a {@code null} reference
     * @throws IllegalArgumentException
     *             if length of the parameter {@code usersEMail} is greater than
     *             {@value #E_MAIL_MAX_LENGTH};<br>
     *             if length of the parameter {@code message} is greater than
     *             {@value #MESSAGE_MAX_LENGTH}
     */
    public BlogComment(BlogEntry blogEntry, String usersEMail, String message) {
        this(blogEntry, usersEMail, message, new Date());
    }

    /**
     * Constructs a new empty {@code BlogComment}.
     */
    protected BlogComment() {
    }

    /**
     * Returns the ID of the blog comment.
     * 
     * @return the ID of the blog comment
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the blog comment.
     * 
     * @param id
     *            new ID of the blog comment
     * @throws NullPointerException
     *             if parameter {@code id} is a {@code null} reference
     */
    public void setId(Long id) {
        this.id = Objects.requireNonNull(id, "You cannot set the ID of the blog comment to a null reference.");
    }

    /**
     * Returns the blog entry that this comment refers to.
     * 
     * @return the blog entry that this comment refers to
     */
    public BlogEntry getBlogEntry() {
        return blogEntry;
    }

    /**
     * Sets the blog entry that this comment refers to.
     * 
     * @param blogEntry
     *            new blog entry that this comment refers to
     * @throws NullPointerException
     *             if parameter {@code blogEntry} is a {@code null} reference
     */
    public void setBlogEntry(BlogEntry blogEntry) {
        this.blogEntry = Objects
                .requireNonNull(blogEntry, "You cannot set the blog entry of the blog comment to a null reference.");
    }

    /**
     * Returns the e-mail of the user that created this comment.
     * 
     * @return the e-mail of the user that created this comment
     */
    public String getUsersEMail() {
        return usersEMail;
    }

    /**
     * Sets the e-mail of the user that created this comment.
     * 
     * @param usersEMail
     *            new e-mail of the user that created this comment
     * @throws NullPointerException
     *             if parameter {@code usersEMail} is a {@code null} reference
     * @throws IllegalArgumentException
     *             if length of the parameter {@code usersEMail} is greater than
     *             {@value #E_MAIL_MAX_LENGTH}
     */
    public void setUsersEMail(String usersEMail) {
        Objects.requireNonNull(usersEMail, "You cannot set the users e-mail of the blog comment to a null reference.");

        if (usersEMail.length() > E_MAIL_MAX_LENGTH)
            throw new IllegalArgumentException(
                    "Length of users e-mail cannot be greater than " + MESSAGE_MAX_LENGTH + ".");

        this.usersEMail = usersEMail;
    }

    /**
     * Returns the message that was posted as a comment of the blog entry.
     * 
     * @return the message that was posted as a comment of the blog entry
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message that was posted as a comment of the blog entry.
     * 
     * @param message
     *            new message that was posted as a comment of the blog entry
     * @throws NullPointerException
     *             if parameter {@code message} is a {@code null} reference
     * @throws IllegalArgumentException
     *             if length of the parameter {@code message} is greater than
     *             {@value #MESSAGE_MAX_LENGTH}
     */
    public void setMessage(String message) {
        Objects.requireNonNull(message, "You cannot set the message of the blog comment to a null reference.");

        if (message.length() > MESSAGE_MAX_LENGTH)
            throw new IllegalArgumentException(
                    "Length of the message of the blog comment cannot be greater than " + MESSAGE_MAX_LENGTH + ".");

        this.message = message;
    }

    /**
     * Returns the date and time when this blog entry was posted.
     * 
     * @return the date and time when this blog entry was posted
     */
    public Date getPostedOn() {
        return postedOn;
    }

    /**
     * Sets the date and time when this blog comment was posted.
     * 
     * @param postedOn
     *            new date and time when this blog comment was posted.
     * @throws NullPointerException
     *             if parameter {@code postedOn} is a {@code null} reference
     */
    public void setPostedOn(Date postedOn) {
        this.postedOn = Objects.requireNonNull(
                postedOn,
                "You cannot set the date when this blog comment was posted of the blog comment to a null reference.");
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        BlogComment other = (BlogComment) obj;
        if (id == null) {
            if (other.getId() != null)
                return false;
        } else if (!id.equals(other.getId()))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "BlogComment [id=" + id + ", blogEntry=" + blogEntry + ", usersEMail=" + usersEMail + ", message="
                + message + ", postedOn=" + postedOn + "]";
    }

}