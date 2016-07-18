package hr.fer.zemris.java.hw15.servlets;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.crypto.Sha1Utils;
import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.entities.BlogUser;

/**
 * {@code LoginServlet} is a {@link HttpServlet} class that handles all logins.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see HttpServlet
 */
@WebServlet(name = "login", urlPatterns = { "/servleti/login" })
public class LoginServlet extends HttpServlet {

    /** Serial version UID. */
    private static final long serialVersionUID = 4608384024913102540L;

    /** Max-age of a cookie. */
    private static final int MAX_AGE = 60 * 10;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("username");
        String password = request.getParameter("password");

        if (checkIfValidPassword(name, password)) {
            BlogUser user = DAOProvider.getDAO().getBlogUser(name);

            Cookie idCookie = new Cookie("current.user.id", Long.toString(user.getId()));
            Cookie firstNameCookie = new Cookie("current.user.fn", user.getFirstName());
            Cookie lastNameCookie = new Cookie("current.user.ln", user.getLastName());
            Cookie nicknameCookie = new Cookie("current.user.nick", user.getNick());

            idCookie.setMaxAge(MAX_AGE);
            firstNameCookie.setMaxAge(MAX_AGE);
            lastNameCookie.setMaxAge(MAX_AGE);
            nicknameCookie.setMaxAge(MAX_AGE);

            response.addCookie(idCookie);
            response.addCookie(firstNameCookie);
            response.addCookie(lastNameCookie);
            response.addCookie(nicknameCookie);

            request.getSession().setAttribute("login_status", "0");
        } else {
            request.getSession().setAttribute("login_status", "1");
        }

        response.sendRedirect("/blog/index.jsp");
    }

    /**
     * Checks if provided password is really the password of the user with
     * specified username.
     * 
     * @param username
     *            username of the user
     * @param password
     *            password of the user
     * @return {@code true} if user is password is valid; {@code false}
     *         otherwise
     */
    private static boolean checkIfValidPassword(String username, String password) {
        Objects.requireNonNull(username, "You cannot check password of a user with null as a username.");
        Objects.requireNonNull(password, "You cannot check password of a user with null as a password.");

        BlogUser user = DAOProvider.getDAO().getBlogUser(username);

        if (user == null)
            return false;
        return user.getPasswordHash().equals(Sha1Utils.sha1Hex(password));
    }

}
