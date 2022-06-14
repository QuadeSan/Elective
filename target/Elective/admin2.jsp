<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
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
        <c:if test="${currentUser.login != 'guest'}">
        <div id="logoutbtn"><a class="ahead" href="logout">Logout</a></div>
        </c:if>
        <c:if test="${currentUser.login == 'guest'}">
            <div id="loginbtn"><a class="ahead" href="login">Login</a></div>
        </c:if>
    </header>
    <div class="main">
        <h1 class="tools"> Admin tools </h1>
        <c:if test="${infoMessage != null}">
        <div class="info-box">
        <span class="info-span"> ${infoMessage}! </span>
        <c:remove var="infoMessage" scope="session" />
        </div>
        </c:if>
        <c:if test="${errorMessage != null}">
        <div class="info-box">
        <span class="error-span"> ${errorMessage} </span>
        <c:remove var="errorMessage" scope="session" />
        </div>
        </c:if>
        <h2 class="main-h2">Create teacher</h2>
         <form class="edit-form" action="admin" method="post">
                <div class="info-container">
                <p>USER INFORMATION</p>
                <div class="col-1-2">
                    <label for="Login"><b>Login</b></label>
                    <input type="text" placeholder="New Login" name="login" id="account-field" required>
                </div>
                <div class="col-1-2">
                    <label for="Email adress"><b>Email</b></label>
                    <input type="text" placeholder="New Email" name="email" id="account-field" required>
                </div>
                <div class="col-1-2">
                    <label for="psw"><b>Password</b></label>
                    <input type="password" placeholder="New Password" name="psw" id="account-field" required>
                </div>
                <div class="col-1-2">
                    <label for="psw-repeat"><b>Repeat Password</b></label>
                    <input type="password" placeholder="Repeat Password" name="psw-repeat" id="account-field" required>
                </div>
                 <button class="applybtn" type="submit"> Create new teacher </button>
                </div>
         </form>
      <%--<h2 class="main-h2">Create new course </h2>
          <form class="edit-form" action="admin" method="post">
                  <div class="info-container">
                  <p>COURSE INFORMATION</p>
                  <div>
                  <label for="Course title"><b>Course title</b></label>
                  <input type="text" placeholder="New Course" name="course-title" style="width:37%">
                  </div>
                  <button class="applybtn" type="submit"> Create new course </button>
                  </div>
          </form> --%>
         <h2 class="main-h2">Create/delete course </h2>
   <div class="info-container">
       <div class="col-1-2" style="background-color:#f1f1f1;">
         <form class="edit-form" action="admin" method="post" style="background-color:#f1f1f1;">

                <p>COURSE INFORMATION</p>
                <div>
                    <label for="Course title"><b>Course title</b></label>
                    <input type="text" placeholder="New Course" name="course-title" id="account-field">
                </div>
                <button class="applybtn" type="submit"> Create new course </button>

         </form>
       </div>
       <div class="col-1-2" style="background-color:#f1f1f1;">
         <form class="edit-form" action="admin" method="post">
                 <p>COURSE INFORMATION</p>
                 <div>
                 <label for="Course ID"><b>Course ID</b></label>
                 <input type="text" placeholder="Course ID" name="course-id" id="account-field">
                 </div>
                 <button class="applybtn" type="submit"> Delete course </button>
         </form>
       </div>
   </div>
        <%-- <h2 class="main-h2">Delete course</h2>
         <form class="edit-form" action="admin" method="post">
                <div class="info-container">
                <p>COURSE INFORMATION</p>
                <div>
                    <label for="Course ID"><b>Course ID</b></label>
                    <input type="text" placeholder="Course ID" name="course-id" style="width:37%">
                </div>
                <button class="applybtn" type="submit"> Delete course </button>
                </div>
         </form> --%>
         <h2 class="main-h2">Assign teacher to course</h2>
         <form class="edit-form" action="admin" method="post">
                <div class="info-container">
                <p>COURSE AND TEACHER INFORMATION</p>
                <div class="col-1-2">
                    <label for="Course ID"><b>Course ID</b></label>
                    <input type="text" placeholder="Course ID" name="course-id" id="account-field">
                </div>
                <div class="col-1-2">
                    <label for="Teacher ID"><b>Teacher ID</b></label>
                    <input type="text" placeholder="Teacher ID" name="teacher-id" id="account-field">
                </div>
                <button class="applybtn" type="submit"> Assign teacher </button>
                </div>
         </form>
         <h2 class="main-h2">Lock/unlock student</h2>
         <form class="edit-form" action="admin" method="post">
                <div class="info-container">
                <p>STUDENT INFORMATION</p>
                <div class="col-1-2">
                    <label for="Student ID"><b>Student ID</b></label>
                    <input type="text" placeholder="Student ID" name="student-id" id="account-field">
                </div>
                <div class="col-1-2">
                    <label for="Student status"><b>Student status</b></label>
                    <input type="text" placeholder="Locked/unlocked" name="student-status" id="account-field">
                </div>
                <button class="applybtn" type="submit"> Apply changes </button>
                </div>
         </form>

    </div>
    <footer>
        Here should be some footer information
    </footer>
</body>
<html>