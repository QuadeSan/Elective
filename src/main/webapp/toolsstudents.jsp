<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="style.css">
    <script type="text/javascript" src="myscripts.js"></script>
    <script type="text/javascript" src="tablescripts.js"></script>
</head>
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
        <h1 class="tools"> List of students </h1>
    <div class="tools">
        <c:if test="${allStudentsList == null || empty allStudentsList}">
        <b> There are no students yet </b>
        </c:if>
        <c:if test="${allStudentsList != null && not empty allStudentsList}">
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
                <button> Status
                <span aria-hidden="true"></span>
                </button>
              </th>
              <th id="courseHead">
                Set mark for course
              </th>
              </tr>
            </thead>
            <tbody>
               <c:forEach items="${allStudentsList}" var="student">
                 <tr class="courseBody">
                 <td class="num"> ${student.studentID} </td>
                 <td> ${student.name} </td>
                 <td> ${student.lastName} </td>
                 <td> ${student.status} </td>
                 <c:if test="${student.status == 'locked'}">
                 <td>
                    <form action="toolsstudents" method="post">
                    <input type="hidden" name="student_id" value="${student.studentID}">
                    <input type="hidden" name="status" value="unlocked">
                    <button type="submit" class="lockbtn"> unlock
                    </form>
                 </td>
                 </c:if>
                 <c:if test="${student.status == 'unlocked'}">
                 <td>
                    <form action="toolsstudents" method="post">
                    <input type="hidden" name="student_id" value="${student.studentID}">
                    <input type="hidden" name="status" value="locked">
                    <button type="submit" class="lockbtn"> lock
                    </form>
                 </td>
                 </c:if>
                 </tr>
               </c:forEach>
            </tbody>
          </table>
       </c:if>
    </div>
    </div>
    <footer>
        Here should be some footer information
    </footer>
</body>
<html>