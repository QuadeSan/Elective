<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="style.css">
    <script type="text/javascript" src="myscripts.js"></script>
    <body>
    <header>
        <div id="homebutton"><a class="ahead" href="main">Home</a></div>
        <div id="coursesearch"><a class="ahead" href="courses">Courses</a></div>
        <div id="cabinet"><a class="ahead" href="account">My account</a></div>
        <c:if test="${userRole == 'Admin'}">
        <div class="dropdown" id="god-mode">
            <button class="dropbtn"> Admin tools </button>
            <div class="dropdown-content">
                <a class="ablack" href="allcourses"> <button>Course tools </button> </a>
                <a class="ablack" href="toolsteacher"> <button>Teacher tools </button> </a>
                <a class="ablack" href="allstudents"> <button>Student tools</button> </a>
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
    <table class="editCourseTable" id="editCourseTable">
          <thead>
            <tr>
              <th id="courseHead">
              Course ID
              </th>
              <th id="courseHead">
              Topic
              </th>
              <th id="courseHead">
              Title
              </th>
              <th id="courseHead">
              Status
              </th>
              <th id="courseHead">
              Teacher
              </th>
            </tr>
          </thead>
          <tbody>
               <tr class="courseBody">
               <td> ${editedCourse.courseID} </td>
               <td> ${editedCourse.topic} </td>
               <td> ${editedCourse.title} </td>
               <td> ${editedCourse.status} </td>
               <td> ${editedCourse.assignedTeacher} </td>
               </tr>
          </tbody>
        </table>
    <h2 class="tools"> Course information </h2>
        <h2 class="main-h2">Edit course</h2>
         <form class="edit-form" action="toolscourses" method="post">
                <div class="info-container">
                <p>COURSE INFORMATION</p>
                <div class="col-1-2">
                    <label><b>New title</b></label>
                    <input type="text" placeholder="New title" name="new-title" id="account-field">
                </div>
                <div class="col-1-2">
                    <label><b>New topic</b></label>
                    <input type="text" placeholder="New topic" name="new-topic" id="account-field">
                </div>
                <div class="col-1-2">
                    <label><b>New status</b></label>
                    <input type="text" placeholder="New status" name="new-status" id="account-field">
                </div>
                <div class="col-1-2">
                    <label><b>New teacher assignment</b></label>
                    <select id="teacherSelect" name="teacher-id">
                    <option value="0"> </option>
                    <c:forEach items="${teachers}" var="teacher">
                    <option value="${teacher.teacherID}"> ${teacher.teacherID} ${teacher.name} ${teacher.lastName} </option>
                    </c:forEach>
                    </select>
                </div>
                <input type="hidden" name="adminAction" value="editCourse">
                <button class="applybtn" type="submit"> Apply changes </button>
                </div>
         </form>
    </div>
    <footer>
        Here should be some footer information
    </footer>
</body>
</html>