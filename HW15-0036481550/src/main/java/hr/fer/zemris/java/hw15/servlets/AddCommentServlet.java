package hr.fer.zemris.java.hw15.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAO;
import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.dao.jpa.JPAEMProvider;
import hr.fer.zemris.java.hw15.entities.BlogComment;
import hr.fer.zemris.java.hw15.entities.BlogEntry;
import hr.fer.zemris.java.hw15.entities.BlogUser;

/**
 * {@code AddCOmmentServlet} is a {@link HttpServlet} class that adds comment to
 * a blog entry.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see HttpServlet
 */
@WebServlet(name = "addComment", urlPatterns = { "/servleti/addComment" })
public class AddCommentServlet extends HttpServlet {

    /** Serial version UID. */
    private static final long serialVersionUID = 5617869630617561057L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String comment = req.getParameter("comment");
        String entryID = req.getParameter("entry");
        String userID = req.getParameter("user");

        DAO dao = DAOProvider.getDAO();
        BlogEntry entry = dao.getBlogEntry(Long.parseLong(entryID));
        BlogUser user = dao.getBlogUser(Long.parseLong(userID));

        BlogComment blogComment = new BlogComment(entry, user.getEmail(), comment);

        JPAEMProvider.getEntityManager().persist(blogComment);
        JPAEMProvider.close();

        resp.sendRedirect("/blog/index.jsp");
    }

}
