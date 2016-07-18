package hr.fer.zemris.java.hw15.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {@code LogoutServlet} is a {@link HttpServlet} class that handles all
 * logouts.
 * 
 * @author Karlo Vrbić
 * @version 1.0
 * @see HttpServlet
 */
@WebServlet(name = "logout", urlPatterns = { "/servleti/logout" })
public class LogoutServlet extends HttpServlet {

    /** Serial version UID. */
    private static final long serialVersionUID = 8821982074511377985L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String cookieName = cookie.getName();

                if (cookieName.equals("current.user.id") || cookieName.equals("current.user.fn")
                        || cookieName.equals("current.user.ln") || cookieName.equals("current.user.nick")) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }

        request.getSession().removeAttribute("login_status");
        request.getSession().removeAttribute("login_message");

        response.sendRedirect("/blog/index.jsp");
    }

}
