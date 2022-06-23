<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="style.css">
    <script type="text/javascript" src="myscripts.js"></script>
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
        <c:if test="${userRole != 'guest'}">
            <div id="logoutbtn"><a class="ahead" href="logout">Logout</a></div>
        </c:if>
        <c:if test="${userRole == 'guest'}">
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
        <h2 class="main-h2">Create new course </h2>
          <form class="edit-form" action="admin" method="post">
                  <div class="info-container">
                  <p>COURSE INFORMATION</p>
                  <div class="col-1-2">
                  <label><b>Course title</b></label>
                  <input type="text" placeholder="Course title" name="title" id="account-field" required>
                  </div>
                  <div class="col-1-2">
                  <label><b>Course topic</b></label>
                  <input type="text" placeholder="Course topic" name="topic" id="account-field" required>
                  </div>
                  <input type="hidden" name="adminAction" value="createCourse">
                  <button class="applybtn" type="submit"> Create new course </button>
                  </div>
          </form>
         <h2 class="main-h2">Edit course</h2>
         <form class="edit-form" action="admin" method="post">
                <div class="info-container">
                <p>COURSE INFORMATION</p>
                <div class="col-1-2">
                    <label><b>Course ID</b></label>
                    <input type="text" placeholder="Course ID" name="course-id" id="account-field" required>
                </div>
                <div class="col-1-2">
                    <label><b>New title</b></label>
                    <input type="text" placeholder="New title" name="new-title" id="account-field">
                </div>
                <div class="col-1-2">
                    <label><b>New topic</b></label>
                    <input type="password" placeholder="Teacher ID" name="new-topic" id="account-field">
                </div>
                <div class="col-1-2">
                    <label><b>New teacher assignment</b></label>
                    <input type="password" placeholder="Teacher ID" name="nteacher-id" id="account-field">
                </div>
                <input type="hidden" name="adminAction" value="editCourse">
                <button class="applybtn" type="submit"> Apply changes </button>
                </div>
         </form>
         <h2 class="main-h2">Delete course</h2>
         <form class="edit-form" action="admin" method="post" onSubmit="confirmAlert()">
                <div class="info-container">
                <p>COURSE INFORMATION</p>
                <div>
                    <label><b>Course ID</b></label>
                    <input type="text" placeholder="Course ID" name="course-id" style="width:37%">
                </div>
                <input type="hidden" name="adminAction" value="deleteCourse">
                <button class="applybtn" type="submit" > Delete course </button>
                </div>
         </form>
        <h2 class="main-h2">Create teacher</h2>
         <form class="edit-form" action="admin" method="post">
                <div class="info-container">
                <p>USER INFORMATION</p>
                <div class="col-1-2">
                    <label><b>Login</b></label>
                    <input type="text" placeholder="New Login" name="login" id="account-field" required>
                </div>
                <div class="col-1-2">
                    <label><b>Email</b></label>
                    <input type="text" placeholder="New Email" name="email" id="account-field" required>
                </div>
                <div class="col-1-2">
                    <label><b>Name</b></label>
                    <input type="text" placeholder="Teacher's name" name="name" id="account-field" required>
                </div>
                <div class="col-1-2">
                    <label><b>Last name</b></label>
                    <input type="text" placeholder="Teacher's last name" name="lastName" id="account-field" required>
                </div>
                <div class="col-1-2">
                    <label><b>Password</b></label>
                    <input type="password" placeholder="New Password" name="psw" id="account-field" required>
                </div>
                <div class="col-1-2">
                    <label><b>Repeat Password</b></label>
                    <input type="password" placeholder="Repeat Password" name="psw-repeat" id="account-field" required>
                </div>
                <input type="hidden" name="adminAction" value="createTeacher">
                <button class="applybtn" type="submit"> Create new teacher </button>
                </div>
         </form>
         <h2 class="main-h2">Assign teacher to course</h2>
         <form class="edit-form" action="admin" method="post">
                <div class="info-container">
                <p>COURSE AND TEACHER INFORMATION</p>
                <div class="col-1-2">
                    <label><b>Course ID</b></label>
                    <input type="text" placeholder="Course ID" name="course-id" id="account-field" required>
                </div>
                <div class="col-1-2">
                    <label><b>Teacher ID</b></label>
                    <input type="text" placeholder="Teacher ID" name="teacher-id" id="account-field"required>
                </div>
                <input type="hidden" name="adminAction" value="assignTeacher">
                <button class="applybtn" type="submit"> Assign teacher </button>
                </div>
         </form>
         <h2 class="main-h2">Lock/unlock student</h2>
         <form class="edit-form" action="admin" method="post">
                <div class="info-container">
                <p>STUDENT INFORMATION</p>
                <div class="col-1-2">
                    <label><b>Student ID</b></label>
                    <input type="text" placeholder="Student ID" name="student-id" id="account-field" required>
                </div>
                <div class="col-1-2">
                    <label><b>Student status</b></label>
                    <input type="text" placeholder="Locked/unlocked" name="student-status" id="account-field" required>
                </div>
                <input type="hidden" name="adminAction" value="lockStudent">
                <button class="applybtn" type="submit" onclick="confirmAlert()"> Apply changes </button>
                </div>
         </form>

    </div>
    <footer>
        Here should be some footer information
    </footer>
</body>
<html>