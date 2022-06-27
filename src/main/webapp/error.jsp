<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <link rel="stylesheet" type="text/css" href="style.css">
<body>
    <header>
        <div id="homebutton"><a class="ahead" href="main">Home</a></div>
        <div id="coursesearch"><a class="ahead" href="courses">Courses</a></div>
        <div id="cabinet"><a class="ahead" href="account">My account</a></div>
        <c:if test="${userRole == 'Admin'}">
        <div class="dropdown" id="god-mode">
            <button class="dropbtn"> Admin tools </button>
            <div class="dropdown-content">
                <a class="ablack" href="toolscourses"> <button>Course tools </button> </a>
                <a class="ablack" href="toolsteacher"> <button>Teacher tools </button> </a>
                <a class="ablack" href="toolsstudents"> <button>Student tools</button> </a>
            </div>
        </div>
        </c:if>
        <div id="hellouser">
        Hello, <a class="ahead" href="account">${currentUser.login}</a>
        <c:if test="${userRole == 'guest'}"> guest </c:if> !
        </div>
        <c:if test="${userRole != 'guest'}">
            <div id="logoutbtn"><a class="ahead" href="logout">Logout</a></div>
        </c:if>
        <c:if test="${userRole == 'guest'}">
            <div id="loginbtn"><a class="ahead" href="login">Login</a></div>
        </c:if>
    </header>
    <div class="main">
        <c:if test="${errorMessage != null}">
        <div class="info-box">
        <span class="error-span"> ${errorMessage} </span>
        <c:remove var="errorMessage" scope="session" />
        </c:if>
        </div>
    </div>
    <footer>
        Here should be some footer information
    </footer>
</body>
</html>