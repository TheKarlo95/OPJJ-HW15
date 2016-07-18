package hr.fer.zemris.java.hw15.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.QueryHint;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * {@code BlogEntry} is an {@link Entity} class that is used to as persistent
 * data about a blog entry.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see Entity
 * @see Serializable
 */
@Entity
@Table(name = "blog_entries")
@Cacheable(true)
@NamedQueries({
        @NamedQuery(name = "BlogEntry.selectAll", query = "select e from BlogEntry as e", hints = {
                @QueryHint(name = "org.hibernate.cacheable", value = "true") }),
        @NamedQuery(name = "BlogEntry.selectById", query = "select e from BlogEntry as e where e.id=:id", hints = {
                @QueryHint(name = "org.hibernate.cacheable", value = "true") }),
        @NamedQuery(name = "BlogEntry.selectByCreator", query = "select e from BlogEntry as e where e.creator=:creator", hints = {
                @QueryHint(name = "org.hibernate.cacheable", value = "true") })
})
public class BlogEntry implements Serializable {

    /** Serial version UID. */
    private static final long serialVersionUID = -1119501897661478680L;

    /** Maximal length of the blog title. */
    private static final int TITLE_MAX_LENGTH = 50;

    /** Maximal length of the blog length. */
    private static final int TEXT_MAX_LENGTH = 4096;

    /**
     * ID of the blog entry. This is a auto-incremented number set by database.
     */
    @Id
    @GeneratedValue
    private Long id;

    /** Creator of this blog entry. */
    @ManyToOne
    @JoinColumn(nullable = false)
    private BlogUser creator;

    /** Title of the blog entry. */
    @Column(nullable = false, length = TITLE_MAX_LENGTH)
    private String title;

    /** Text of the blog entry. */
    @Column(nullable = false, length = 4096)
    private String text;

    /** Date and time when this blog entry was created. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createdAt;

    /** Date and time when this blog entry was last modified. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true)
    private Date lastModifiedAt;

    /** List of all comments for this blog entry. */
    @OneToMany(mappedBy = "blogEntry", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    @OrderBy("postedOn")
    private List<BlogComment> comments = new ArrayList<>();

    /**
     * Constructs a new {@code BlogEntry} with specified creator, blog title,
     * blog text, date when this blog entry was created, date when this blog
     * entry was last modified and list of all comments.
     * 
     * @param creator
     *            the creator of the blog entry
     * @param title
     *            the title of the blog entry
     * @param text
     *            the text of the blog entry
     * @param createdAt
     *            the ate and time when this blog entry was created
     * @param lastModifiedAt
     *            the date and time when this blog entry was last modified
     * @param comments
     *            the list of all comments for this blog entry
     * @throws NullPointerException
     *             if parameter {@code creator}, {@code title}, {@code text},
     *             {@code createdAt}, {@code lastModifiedAt} or {@code comments}
     *             is a {@code null} reference
     * @throws IllegalArgumentException
     *             if length of the parameter {@code title} is greater than
     *             {@value #TITLE_MAX_LENGTH};<br>
     *             if length of the parameter {@code text} is greater than
     *             {@value #TEXT_MAX_LENGTH}
     */
    public BlogEntry(BlogUser creator, String title, String text, Date createdAt, Date lastModifiedAt,
            List<BlogComment> comments) {
        Objects.requireNonNull(creator, "You cannot set the creator of the blog entry to a null reference.");
        Objects.requireNonNull(title, "You cannot set the title of the blog entry to a null reference.");
        Objects.requireNonNull(text, "You cannot set the text of the blog entry to a null reference.");
        Objects.requireNonNull(
                createdAt,
                "You cannot set the date when this blog entry was created to a null reference.");
        Objects.requireNonNull(
                lastModifiedAt,
                "You cannot set the date when this blog entry was last modified to a null reference.");
        Objects.requireNonNull(
                comments,
                "You cannot set the list of the comments of the blog entry to a null reference.");

        if (title.length() > TITLE_MAX_LENGTH)
            throw new IllegalArgumentException(
                    "Length of the title of the blog entry cannot be greater than " + TITLE_MAX_LENGTH + ".");
        if (text.length() > TEXT_MAX_LENGTH)
            throw new IllegalArgumentException(
                    "Length of the text of the blog entry cannot be greater than " + TEXT_MAX_LENGTH + ".");

        this.creator = creator;
        this.title = title;
        this.text = text;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
        this.comments = comments;
    }

    /**
     * Constructs a new {@code BlogEntry} with specified creator, blog title,
     * blog text, date when this blog entry was created and list of all
     * comments.
     * <p>
     * The attribute {@code lastModifiedAt} is set to the same date as the
     * attribute {@code createdAt}.
     * 
     * @param creator
     *            the creator of the blog entry
     * @param title
     *            the title of the blog entry
     * @param text
     *            the text of the blog entry
     * @param createdAt
     *            the ate and time when this blog entry was created
     * @param comments
     *            the list of all comments for this blog entry
     * @throws NullPointerException
     *             if parameter {@code creator}, {@code title}, {@code text},
     *             {@code createdAt} or {@code comments} is a {@code null}
     *             reference
     * @throws IllegalArgumentException
     *             if length of the parameter {@code title} is greater than
     *             {@value #TITLE_MAX_LENGTH};<br>
     *             if length of the parameter {@code text} is greater than
     *             {@value #TEXT_MAX_LENGTH}
     */
    public BlogEntry(BlogUser creator, String title, String text, Date createdAt, List<BlogComment> comments) {
        this(creator, title, text, createdAt, createdAt, comments);
    }

    /**
     * Constructs a new {@code BlogEntry} with specified creator, blog title,
     * blog text and date when this blog entry was created.
     * <p>
     * The attribute {@code lastModifiedAt} is set to the same date as the
     * attribute {@code createdAt} and attribute {@code comments} is set to
     * empty list.
     * 
     * @param creator
     *            the creator of the blog entry
     * @param title
     *            the title of the blog entry
     * @param text
     *            the text of the blog entry
     * @param createdAt
     *            the ate and time when this blog entry was created
     * @throws NullPointerException
     *             if parameter {@code creator}, {@code title}, {@code text} or
     *             {@code createdAt} is a {@code null} reference
     * @throws IllegalArgumentException
     *             if length of the parameter {@code title} is greater than
     *             {@value #TITLE_MAX_LENGTH};<br>
     *             if length of the parameter {@code text} is greater than
     *             {@value #TEXT_MAX_LENGTH}
     */
    public BlogEntry(BlogUser creator, String title, String text, Date createdAt) {
        this(creator, title, text, createdAt, new ArrayList<>());
    }

    /**
     * Constructs a new {@code BlogEntry} with specified creator, blog title and
     * blog text.
     * <p>
     * The attribute {@code lastModifiedAt} and {@code createdAt} are set to
     * {@link Date#Date()} and attribute {@code comments} is set to empty list.
     * 
     * @param creator
     *            the creator of the blog entry
     * @param title
     *            the title of the blog entry
     * @param text
     *            the text of the blog entry
     * @throws NullPointerException
     *             if parameter {@code creator}, {@code title} or {@code text}
     *             is a {@code null} reference
     * @throws IllegalArgumentException
     *             if length of the parameter {@code title} is greater than
     *             {@value #TITLE_MAX_LENGTH};<br>
     *             if length of the parameter {@code text} is greater than
     *             {@value #TEXT_MAX_LENGTH}
     */
    public BlogEntry(BlogUser creator, String title, String text) {
        this(creator, title, text, new Date());
    }

    /**
     * Constructs a new empty {@code BlogEntry}.
     */
    protected BlogEntry() {
    }

    /**
     * Returns the creator of the blog entry.
     * 
     * @return the creator of the blog entry
     */
    public BlogUser getCreator() {
        return creator;
    }

    /**
     * Sets the creator of the blog entry
     * 
     * @param creator
     *            new creator of the blog entry
     * @throws NullPointerException
     *             if parameter {@code creator} is a {@code null} reference
     */
    public void setCreator(BlogUser creator) {
        this.creator = Objects.requireNonNull(creator, "You cannot set the ID of the blog entry to a null reference.");
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
     * Returns the list of all comments for this blog entry.
     * 
     * @return the list of all comments for this blog entry
     */
    public List<BlogComment> getComments() {
        return comments;
    }

    /**
     * Sets the list of all comments for this blog entry.
     * 
     * @param comments
     *            new list of all comments for this blog entry
     * @throws NullPointerException
     *             if parameter {@code comments} is a {@code null} reference
     */
    public void setComments(List<BlogComment> comments) {
        this.comments = Objects.requireNonNull(
                comments,
                "You cannot set the list of the comments of the blog entry to a null reference.");
    }

    /**
     * Returns the date and time when this blog entry was created.
     * 
     * @return the date and time when this blog entry was created
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the date and time when this blog entry was created.
     * 
     * @param createdAt
     *            new date and time when this blog entry was created
     * @throws NullPointerException
     *             if parameter {@code createdAt} is a {@code null} reference
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = Objects.requireNonNull(
                createdAt,
                "You cannot set the date when this blog entry was created to a null reference.");
    }

    /**
     * Returns the date and time when this blog entry was last modified.
     * 
     * @return the date and time when this blog entry was last modified
     */
    public Date getLastModifiedAt() {
        return lastModifiedAt;
    }

    /**
     * Sets the date and time when this blog entry was last modified.
     * 
     * @param lastModifiedAt
     *            new date and time when this blog entry was last modified
     * @throws NullPointerException
     *             if parameter {@code lastModifiedAt} is a {@code null}
     *             reference
     */
    public void setLastModifiedAt(Date lastModifiedAt) {
        this.lastModifiedAt = Objects.requireNonNull(
                lastModifiedAt,
                "You cannot set the date when this blog entry was last modified to a null reference.");
    }

    /**
     * Returns the title of the blog entry.
     * 
     * @return the title of the blog entry
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the blog entry.
     * 
     * @param title
     *            new title of the blog entry
     * @throws NullPointerException
     *             if parameter {@code title} is a {@code null} reference
     * @throws IllegalArgumentException
     *             if length of the parameter {@code title} is greater than
     *             {@value #TITLE_MAX_LENGTH}
     */
    public void setTitle(String title) {
        Objects.requireNonNull(title, "You cannot set the title of the blog entry to a null reference.");

        if (title.length() > TITLE_MAX_LENGTH)
            throw new IllegalArgumentException(
                    "Length of the title of the blog entry cannot be greater than " + TITLE_MAX_LENGTH + ".");

        this.title = title;
    }

    /**
     * Returns the text of the blog entry.
     * 
     * @return the text of the blog entry
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text of the blog entry.
     * 
     * @param text
     *            new text of the blog entry
     * @throws NullPointerException
     *             if parameter {@code text} is a {@code null} reference
     * @throws IllegalArgumentException
     *             if length of the parameter {@code text} is greater than
     *             {@value #TEXT_MAX_LENGTH}
     */
    public void setText(String text) {
        Objects.requireNonNull(text, "You cannot set the text of the blog entry to a null reference.");

        if (text.length() > TEXT_MAX_LENGTH)
            throw new IllegalArgumentException(
                    "Length of the text of the blog entry cannot be greater than " + TEXT_MAX_LENGTH + ".");

        this.text = text;
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
        BlogEntry other = (BlogEntry) obj;
        if (id == null) {
            if (other.getId() != null)
                return false;
        } else if (!id.equals(other.getId()))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "BlogEntry [id=" + id + ", title=" + title + ", text=" + text + ", createdAt=" + createdAt
                + ", lastModifiedAt=" + lastModifiedAt + "]";
    }

}