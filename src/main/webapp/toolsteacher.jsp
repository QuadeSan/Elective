<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="style.css">
    <script type="text/javascript" src="myscripts.js"></script>
</head>
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
        <fmt:message key='footer.content'/>
    </footer>
</body>
<html>