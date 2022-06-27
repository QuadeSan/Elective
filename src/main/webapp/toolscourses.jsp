<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="style.css">
    <script type="text/javascript" src="myscripts.js"></script>
    <script type="text/javascript" src="tablescripts.js"></script>
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
        <h2 class="main-h2">Create new course </h2>
          <form id="for-validation" action="toolscourses" method="post">
                  <div class="info-container">
                  <p>COURSE INFORMATION</p>
                  <div class="col-1-2">
                  <div class="form-field">
                  <label><b>Course title</b></label>
                  <input type="text" placeholder="Course title" name="title" class="account-field" id="title" autocomplete="off">
                  <small> </small>
                  </div>
                  </div>
                  <div class="col-1-2">
                  <div class="form-field">
                  <label><b>Course topic</b></label>
                  <input type="text" placeholder="Course topic" name="topic" class="account-field" id="topic" autocomplete="off">
                  <small> </small>
                  </div>
                  </div>
                  <input type="hidden" name="adminAction" value="createCourse">
                  <div style="clear:both;">
                  <button class="applybtn" type="submit"> Create new course </button>
                  </div>
                  </div>
          </form>
    <div class="tools">
    <h2> List of courses </h2>
    <input type="text" id="filterInput" onkeyup="adminTableFilter()" placeholder="Filter by topic/title">
    </div>
    <table class="adminCourseTable" id="courseTable">
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
          <th id="courseHead">
            Edit course
          </th>
          <th id="courseHead">
            Delete course
          </th>
        </tr>
      </thead>
      <tbody>
        <c:forEach items="${allCoursesList}" var="course">
           <tr class="courseBody">
           <td class="num"> ${course.courseID} </td>
           <td> ${course.topic} </td>
           <td> ${course.title} </td>
           <td> ${course.status} </td>
           <td> ${course.studentCount} </td>
           <td> ${course.assignedTeacher} </td>
           <td>
               <form action="toolseditcourse" method="post">
               <input type="hidden" name="course-id" value="${course.courseID}">
               <button type="submit" class="lockbtn"> Edit course
               </form>
           </td>
           <td>
               <form action="toolscourses" method="post" onSubmit="return confirmAlert()">
               <input type="hidden" name="course-id" value="${course.courseID}">
               <input type="hidden" name="adminAction" value="deleteCourse">
               <button type="submit" class="lockbtn"> Delete course
               </form>
           </td>
           </tr>
        </c:forEach>
      </tbody>
    </table>
    <script type="text/javascript" src="validationcourse.js"></script>
    </div>
    <footer>
        Here should be some footer information
    </footer>
</body>
</html>