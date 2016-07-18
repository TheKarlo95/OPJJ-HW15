package hr.fer.zemris.java.hw15.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.entities.BlogUser;

/**
 * {@code MainServlet} is a {@link HttpServlet} class that gets all users and
 * forwards it to index.jsp.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see HttpServlet
 */
@WebServlet(name = "main", urlPatterns = { "/index.jsp" })
public class MainServlet extends HttpServlet {

    /** Serial version UID. */
    private static final long serialVersionUID = 4012956635407408016L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<BlogUser> users = DAOProvider.getDAO().getAllBlogUsers();
        req.setAttribute("users", users);
        req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
    }

}
