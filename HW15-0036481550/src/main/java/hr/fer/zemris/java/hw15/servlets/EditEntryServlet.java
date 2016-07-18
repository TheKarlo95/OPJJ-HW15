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
import hr.fer.zemris.java.hw15.entities.BlogEntry;

/**
 * {@code AuthorServlet} is a {@link HttpServlet} class that can make edit
 * entry.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see HttpServlet
 */
@WebServlet(name = "edit", urlPatterns = { "/servleti/edit" })
public class EditEntryServlet extends HttpServlet {

    /** Serial version UID */
    private static final long serialVersionUID = -517381213281706618L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String text = req.getParameter("text");
        String entryID = req.getParameter("entry");

        DAO dao = DAOProvider.getDAO();
        BlogEntry entry = dao.getBlogEntry(Long.parseLong(entryID));

        entry.setTitle(title);
        entry.setText(text);

        JPAEMProvider.getEntityManager().merge(entry);
        JPAEMProvider.close();

        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }

}
