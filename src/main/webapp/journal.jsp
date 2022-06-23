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
       <div class="account-head">
         <h2 class="main-h2">Journal of course # ${viewedCourse}</h2>
         <c:if test="${infoMessage != null}">
         <div class="info-box"> <span class="info-span"> ${infoMessage} </span> </div>
         <c:remove var="infoMessage"/>
         </c:if>
         <c:if test="${errorMessage != null}">
         <div class="info-box"> <span class="error-span"> ${errorMessage} </span> </div>
         <c:remove var="errorMessage"/>
         </c:if>
         <div> <span class="stealth-span"> hidden </span> </div>
       </div>
       <div class="tools">
         <c:if test="${userRole == 'Teacher' || userRole == 'Admin'}">
         <c:if test="${studentList == null || empty studentList}">
         <b> There are no students on current course yet </b>
         </c:if>
         <c:if test="${studentList != null && not empty studentList}">
          <table class="courseTable">
            <thead>
              <tr>
              <th id="courseHead" class="num" aria-sort="ascending">
                <button> Student ID
                <span aria-hidden="true"></span>
                </button>
                </th>
              <th id="courseHead">
                <button> Name
                <span aria-hidden="true"></span>
                </button>
              </th>
              <th id="courseHead">
                <button> Last name
                <span aria-hidden="true"></span>
                </button>
              </th>
              <th id="courseHead">
                Current mark
              </th>
              <th id="courseHead">
                Set mark for course
              </th>
              </tr>
            </thead>
            <tbody>
               <c:forEach items="${studentList}" var="student">
                 <tr class="courseBody">
                 <td class="num"> ${student.studentID} </td>
                 <td> ${student.name} </td>
                 <td> ${student.lastName} </td>
                 <td> ${student.markForCurrentCourse} </td>
                 <td>
                    <form action="journal?student_id=${student.studentID}" method="post">
                    <input type="text" name="mark" id="mark" required>
                    <button type="submit" class="markbtn"> set mark
                    </form>
                 </td>
                 </tr>
               </c:forEach>
            </tbody>
          </table>
       </c:if>
       </c:if>
       </div>
    </div>
    <footer>
        Here should be some footer information
    </footer>
</body>
</html>