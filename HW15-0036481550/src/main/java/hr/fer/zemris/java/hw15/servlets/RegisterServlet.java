package hr.fer.zemris.java.hw15.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.jpa.JPAEMProvider;
import hr.fer.zemris.java.hw15.entities.BlogUser;

/**
 * {@code LoginServlet} is a {@link HttpServlet} class that handles registration
 * process.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see HttpServlet
 */
@WebServlet(name = "register", urlPatterns = { "/servleti/register" })
public class RegisterServlet extends HttpServlet {

    /** Serial version UID. */
    private static final long serialVersionUID = -552216735163335694L;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String firstName = req.getParameter("firstname");
        String lastName = req.getParameter("lastname");
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        BlogUser user = new BlogUser(firstName, lastName, username, email, password);

        JPAEMProvider.getEntityManager().persist(user);
        JPAEMProvider.close();

        resp.sendRedirect("/blog/index.jsp");
    }

}
