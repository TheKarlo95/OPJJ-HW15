<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title>Blog - New/Edit</title>
</head>
<body>

    <div>
        <form action="${pageContext.request.contextPath}/servleti/edit" method="post">
            <input type="text" name="title" placeholder="Title" autofocus="autofocus" value="${entry.title}" /><br>
            <textarea name="text" rows="6" cols="75" ><c:out value="${entry.text}" /></textarea>
            <input type="hidden" name="entry" value="${entry.id}" /><br>
            <input type="submit" value="Accept" />
        </form>
    </div>

</body>
</html>