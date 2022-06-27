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
         <form class="teacher-form" id="for-validation" action="toolsteacher" method="post">
                <div class="info-container">
                <p>USER INFORMATION</p>
                    <div class="form-field">
                    <label><b>Login</b></label>
                    <input type="text" placeholder="New Login" name="login" id="login" autocomplete="off">
                    <small> </small>
                    </div>
                    <div class="form-field">
                    <label><b>Email</b></label>
                    <input type="text" placeholder="New Email" name="email" id="email" autocomplete="off">
                    <small> </small>
                    </div>
                    <div class="form-field">
                    <label><b>Name</b></label>
                    <input type="text" placeholder="Teacher's name" name="name" id="name" autocomplete="off">
                    <small> </small>
                    </div>
                    <div class="form-field">
                    <label><b>Last name</b></label>
                    <input type="text" placeholder="Teacher's last name" name="lastName" id="lastName" autocomplete="off">
                    <small> </small>
                    </div>
                    <div class="form-field">
                    <label><b>Password</b></label>
                    <input type="password" placeholder="New Password" name="psw" id="psw" autocomplete="off">
                    <small> </small>
                    </div>
                    <div class="form-field">
                    <label><b>Repeat Password</b></label>
                    <input type="password" placeholder="Repeat Password" name="psw-repeat" id="psw-repeat" autocomplete="off">
                    <small> </small>
                    </div>
                <input type="hidden" name="adminAction" value="createTeacher">
                <button class="applybtn" type="submit"> Create new teacher </button>
                </div>
         </form>
          <script type="text/javascript" src="validation.js"></script>
    </div>
    <footer>
        Here should be some footer information
    </footer>
</body>
<html>