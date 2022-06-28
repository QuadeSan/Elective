<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
    <link rel="stylesheet" type="text/css" href="style.css">
    <script type="text/javascript" src="myscripts.js"></script>
    <script type="text/javascript" src="tablescripts.js"></script>
<body>
    <header>
        <div id="homebutton"><a class="ahead" href="main"><fmt:message key='header.button.home'/></a></div>
        <div id="coursesearch"><a class="ahead" href="courses"><fmt:message key='header.button.courses'/></a></div>
        <div id="cabinet"><a class="ahead" href="account"><fmt:message key='header.button.my_account'/></a></div>
        <c:if test="${userRole == 'Admin'}">
        <div class="dropdown" id="god-mode">
            <button class="dropbtn"> <fmt:message key='header.button.admin_tools'/> </button>
            <div class="dropdown-content">
                <a class="ablack" href="toolscourses"> <button><fmt:message key='header.button.course_tools'/> </button> </a>
                <a class="ablack" href="toolsteacher"> <button><fmt:message key='header.button.teacher_tools'/> </button> </a>
                <a class="ablack" href="toolsstudents"> <button><fmt:message key='header.button.student_tools'/></button> </a>
            </div>
        </div>
        </c:if>
        <div id="right-side"> </div>
        <div id="change-locale">
        <form id="locale" action="changelocale" method="post">
        		<select name="locale" onchange="this.form.submit()">
        		    <option value=""><fmt:message key='header.dropdown.language'/></option>
        			<c:forEach items="${applicationScope.locales}" var="locale">
        				<option value="${locale.key}"> ${locale.value} </option>
        			</c:forEach>
        		</select>
        	</form>
        </div>
        <div id="hellouser">
        <fmt:message key='header.hello'/>, <a class="ahead" href="account">${currentUser.login}</a>
        <c:if test="${userRole == 'guest'}"> <fmt:message key='header.guest'/> </c:if> !
        </div>
        <c:if test="${userRole != 'guest'}">
            <div id="logoutbtn"><a class="ahead" href="logout"><fmt:message key='header.logout'/></a></div>
        </c:if>
        <c:if test="${userRole == 'guest'}">
            <div id="loginbtn"><a class="ahead" href="login"><fmt:message key='header.login'/></a></div>
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
        <fmt:message key='footer.content'/>
    </footer>
</body>
</html>