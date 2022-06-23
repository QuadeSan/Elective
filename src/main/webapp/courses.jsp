<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <link rel="stylesheet" type="text/css" href="style.css">
    <script type="text/javascript" src="myscripts.js"></script>
<body>
    <header>
        <div id="homebutton"><a class="ahead" href="main">Home</a></div>
        <div id="coursesearch"><a class="ahead" href="courses">Courses</a></div>
        <div id="cabinet"><a class="ahead" href="account">My account</a></div>
        <c:if test="${userRole == 'Admin'}">
            <div id="god-mode"><a class="ahead" href="admin">God mode on</a></div>
        </c:if>
        <div id="hellouser">
        Hello, <a class="ahead" href="account">${currentUser.login}</a> !
        </div>
        <c:if test="${userRole != 'guest'}">
            <div id="logoutbtn"><a class="ahead" href="logout">Logout</a></div>
        </c:if>
        <c:if test="${userRole == 'guest'}">
            <div id="loginbtn"><a class="ahead" href="login">Login</a></div>
        </c:if>
    </header>
    <div class="main">
    <div class="info-box">
        <c:if test="${infoMessage != null}">
        <span class="info-span">  ${infoMessage} </span>
        </c:if>
        <c:if test="${errorMessage != null}">
        <span class="error-span">  ${errorMessage} </span>
        </c:if>
        <c:if test="${infoMessage == null}">
        <span class="stealth-span"> ${infoMessage} </span>
        </c:if>
        <c:remove var="infoMessage"/>
        <c:remove var="errorMessage"/>
    </div>
    <div class="tools">
    <h2> List of courses </h2>
    <input type="text" id="filterInput" onkeyup="tableFilter()" placeholder="Filter by topic/teacher">
    </div>
    <table class="courseTable" id="courseTable">
      <thead>
        <tr>
          <th id="courseHead" class="num" aria-sort="ascending">
            <button> Course ID
            <span aria-hidden="true"></span>
            </button>
          </th>
          <th id="courseHead">
            <button> Topic
            <span aria-hidden="true"></span>
            </button>
          </th>
          <th id="courseHead">
            <button> Title
            <span aria-hidden="true"></span>
            </button>
          </th>
          <th id="courseHead">
            <button> Status
            <span aria-hidden="true"></span>
            </button>
          </th>
          <th id="courseHead">
            <button> Students on course
            <span aria-hidden="true"></span>
            </button>
          </th>
          <th id="courseHead">
            <button> Teacher
            <span aria-hidden="true"></span>
            </button>
          </th>
          <c:if test="${userRole == 'Student'}">
          <th id="courseHead">
            Available for entry
          </th>
          </c:if>
        </tr>
      </thead>
      <tbody>
        <c:forEach items="${Courses}" var="course">
           <tr class="courseBody">
           <td class="num"> ${course.courseID} </td>
           <td> ${course.topic} </td>
           <td> ${course.title} </td>
           <td> ${course.status} </td>
           <td> ${course.studentCount} </td>
           <td> ${course.assignedTeacher} </td>
           <c:if test="${userRole == 'Student' && course.status == 'New'}">
           <td> <a class="ablack" href="courses?course_id=${course.courseID}"> sign for course </a> </td>
           </c:if>
           <c:if test="${userRole == 'Student' && course.status == 'In progress'}">
           <td> Not available </td>
           </c:if>
           </tr>
        </c:forEach>
      </tbody>
    </table>
    </div>
    <footer>
        Here should be some footer information
    </footer>
</body>
</html>