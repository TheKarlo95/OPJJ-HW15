package hr.fer.zemris.java.hw15.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAO;
import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.dao.jpa.JPAEMProvider;
import hr.fer.zemris.java.hw15.entities.BlogEntry;
import hr.fer.zemris.java.hw15.entities.BlogUser;

/**
 * {@code AuthorServlet} is a {@link HttpServlet} class that can make new or
 * edit existing blog entries.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see HttpServlet
 */
@WebServlet(name = "author", urlPatterns = { "/servleti/author/*" })
public class AuthorServlet extends HttpServlet {

    /** Serial version UID */
    private static final long serialVersionUID = -1333336395144850286L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo().substring(1).replace("servleti/", "").replace("author/", "");
        path = path.replace("servleti/", "");
        path = path.replace("author/", "");

        int index = path.contains("/") ? path.indexOf('/') : path.length();
        String username = path.substring(0, index);
        path = path.substring(index).replace("/", "");

        if (path.equals("new")) {
            prepareNew(req, resp, username);
        } else if (path.equals("edit")) {
            prepareEdit(req, resp, username);
        } else if (path.matches("^[0-9]+$")) {
            prepareBlog(req, Long.parseLong(path));
            req.getRequestDispatcher("/WEB-INF/pages/entries.jsp").forward(req, resp);
        } else if (path.equals("")) {
            prepareBlogs(req, username);
            req.getRequestDispatcher("/WEB-INF/pages/entries.jsp").forward(req, resp);
        }
    }

    /**
     * Prepares the data for the new entry.
     * 
     * @param req
     *            an {@link HttpServletRequest} object that contains the request
     *            the client has made of the servlet
     *
     * @param resp
     *            an {@link HttpServletResponse} object that contains the
     *            response the servlet sends to the client
     * @param username
     *            username of the user
     * @exception IOException
     *                if an input or output error is detected when the servlet
     *                handles the request
     *
     * @exception ServletException
     *                if the request for the POST could not be handled
     */
    private static void prepareNew(HttpServletRequest req, HttpServletResponse resp, String username)
            throws ServletException, IOException {
        DAO dao = DAOProvider.getDAO();
        BlogUser user = dao.getBlogUser(username);
        BlogEntry entry = new BlogEntry(user, "", "");

        JPAEMProvider.getEntityManager().persist(entry);
        JPAEMProvider.close();

        if (checkIfLoggedIn(req, user.getId())) {
            req.getSession().setAttribute("user", user);
            req.getSession().setAttribute("entry", entry);
            req.getRequestDispatcher("/WEB-INF/pages/editBlog.jsp").forward(req, resp);
        }
    }

    /**
     * Prepares the data for the editing existing entry.
     * 
     * @param req
     *            an {@link HttpServletRequest} object that contains the request
     *            the client has made of the servlet
     *
     * @param resp
     *            an {@link HttpServletResponse} object that contains the
     *            response the servlet sends to the client
     * @param username
     *            username of the user
     * @exception IOException
     *                if an input or output error is detected when the servlet
     *                handles the request
     *
     * @exception ServletException
     *                if the request for the POST could not be handled
     */
    private static void prepareEdit(HttpServletRequest req, HttpServletResponse resp, String username)
            throws ServletException, IOException {
        DAO dao = DAOProvider.getDAO();
        BlogUser user = dao.getBlogUser(username);
        String entryID = req.getParameter("entry");
        BlogEntry entry = dao.getBlogEntry(Long.parseLong(entryID));

        if (checkIfLoggedIn(req, user.getId())) {
            req.getSession().setAttribute("user", user);
            req.getSession().setAttribute("entry", entry);
            req.getRequestDispatcher("/WEB-INF/pages/editBlog.jsp").forward(req, resp);
        }
    }

    /**
     * Prepares the data for listing one blog entry.
     * 
     * @param req
     *            an {@link HttpServletRequest} object that contains the request
     *            the client has made of the servlet
     * @param id
     *            id of the entry
     */
    private static void prepareBlog(HttpServletRequest req, long id) {
        BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);
        List<BlogEntry> entries = new ArrayList<>();
        entries.add(entry);

        req.getSession().setAttribute("user", entry.getCreator());
        req.getSession().setAttribute("entries", entries);
    }

    /**
     * Prepares the data for listing all blog entries of one user.
     * 
     * @param req
     *            an {@link HttpServletRequest} object that contains the request
     *            the client has made of the servlet
     * @param username
     *            username of the user
     */
    private static void prepareBlogs(HttpServletRequest req, String username) {
        DAO dao = DAOProvider.getDAO();
        BlogUser user = dao.getBlogUser(username);
        List<BlogEntry> entries = dao.getAllUserEntries(user);

        req.getSession().setAttribute("user", user);
        req.getSession().setAttribute("entries", entries);
    }

    /**
     * Checks if specifeid user is logged in.
     * 
     * @param req
     *            an {@link HttpServletRequest} object that contains the request
     *            the client has made of the servlet
     * @param userID
     *            id of the user
     * @return {@code true} if user is logged in; {@code false} otherwise
     */
    private static boolean checkIfLoggedIn(HttpServletRequest req, long userID) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String cookieName = cookie.getName();

                if (cookieName.equals("current.user.id")) {
                    return cookie.getValue().equals(Long.toString(userID));
                }
            }
        }

        return false;
    }
}
