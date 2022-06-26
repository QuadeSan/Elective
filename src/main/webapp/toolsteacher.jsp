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
    </div>
    <footer>
        Here should be some footer information
    </footer>
</body>
<html>