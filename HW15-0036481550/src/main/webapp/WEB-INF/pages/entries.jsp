<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title>Blog - Blogs</title>
</head>
<body>

    <div>
        <table>
            <thead>
                <tr>
                    <th>Blog entries:</th>
                </tr>
            </thead>

            <tbody>
                <c:forEach var="e" items="${entries}">
                    <tr>
                        <td>
                            <div>
                                <h3>
                                    <c:out value="${e.title}" />
                                </h3>
                                <p>
                                    <c:out value="${e.text}" />
                                </p>
                            </div>

                            <div>
                                <c:if test="${login_status == '0'}">
                                    <form action="servleti/author/${user.nick}/edit" method="post">
                                        <input type="hidden" name="entry" value="${e.id}" /> <input type="submit"
                                            value="Edit" />
                                    </form>
                                </c:if>
                            </div>

                            <div>
                                <c:choose>
                                    <c:when test="${entries.size() == 1}">
                                        <div>
                                            <c:forEach var="comment" items="${entry.comments}">
                                                <p>
                                                    <c:out value="${comment.message}" />
                                                </p>
                                            </c:forEach>
                                        </div>

                                        <div>
                                            <form action="servleti/addComment" method="post">
                                                <textarea name="comment" rows="6" cols="75" placeholder="Comment"></textarea>
                                                <input type="hidden" name="entry" value="${entry.id}" /> <input
                                                    type="hidden" name="user" value="${user.id}" /> <input
                                                    type="submit" value="Comment" />
                                            </form>
                                        </div>
                                    </c:when>
                                </c:choose>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <div>
        <c:choose>
            <c:when test="${login_status == 0}">
                <div>
                    <form action="servleti/author/${user.nick}/new" method="post">
                        <input type="submit" value="New" />
                    </form>
                </div>
            </c:when>
        </c:choose>
    </div>
</body>
</html>