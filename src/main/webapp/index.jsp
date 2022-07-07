<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
    <c:set var="lastpage" value="home" scope="session" />
    <div class="top-corner">
            <form action="changelocale" method="post">
            		<select name="locale" onchange="this.form.submit()">
            		    <option value=""><fmt:message key='header.dropdown.language'/></option>
            			<c:forEach items="${applicationScope.locales}" var="locale">
            				<option value="${locale.key}"> ${locale.value} </option>
            			</c:forEach>
            		</select>
            </form>
    </div>
    <div class="welcome">
    <h2> <fmt:message key='index.welcome.text'/> </h2>
    <a href="logout">
    <button><fmt:message key='index.continue_guest'/></button>
    </a>
    <a href="login">
    <button><fmt:message key='index.continue_user'/></button>
    </a>
    </div>
</body>
</html>
