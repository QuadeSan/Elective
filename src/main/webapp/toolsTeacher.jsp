<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="customLib" uri="http://Elective" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="style.css">
    <script type="text/javascript" src="myscripts.js"></script>
</head>
<body>
<c:set var="lastpage" value="toolsteacher" scope="session" />
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
        <customLib:hello login="${currentUser.login}" locale="${currentLocale}"/>
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
        <h2 class="main-h2"><fmt:message key='tools_teacher.create_teacher'/></h2>
         <form class="teacher-form" id="for-validation" action="toolsteacher" method="post">
                <div class="info-container">
                <p><fmt:message key='account_page.user_information'/></p>
                    <div class="form-field">
                    <label><b><fmt:message key='register.login'/></b></label>
                    <input type="text" placeholder="<fmt:message key='register.login.ph'/>" name="login" id="login" autocomplete="off">
                    <small> </small>
                    </div>
                    <div class="form-field">
                    <label><b><fmt:message key='register.email'/></b></label>
                    <input type="text" placeholder="<fmt:message key='register.email.ph'/>" name="email" id="email" autocomplete="off">
                    <small> </small>
                    </div>
                    <div class="form-field">
                    <label><b><fmt:message key='register.name'/></b></label>
                    <input type="text" placeholder="<fmt:message key='register.teacher_name.ph'/>" name="name" id="name" autocomplete="off">
                    <small> </small>
                    </div>
                    <div class="form-field">
                    <label><b><fmt:message key='register.last_name'/></b></label>
                    <input type="text" placeholder="<fmt:message key='register.teacher_last_name.ph'/>" name="lastName" id="lastName" autocomplete="off">
                    <small> </small>
                    </div>
                    <div class="form-field">
                    <label><b><fmt:message key='register.password'/></b></label>
                    <input type="password" placeholder="<fmt:message key='register.password.ph'/>" name="psw" id="psw" autocomplete="off">
                    <small> </small>
                    </div>
                    <div class="form-field">
                    <label><b><fmt:message key='register.password_repeat'/></b></label>
                    <input type="password" placeholder="<fmt:message key='register.password_repeat.ph'/>" name="psw-repeat" id="psw-repeat" autocomplete="off">
                    <small> </small>
                    </div>
                <input type="hidden" name="adminAction" value="createTeacher">
                <button class="applybtn" type="submit"> <fmt:message key='register.create_teacher.button'/> </button>
                </div>
         </form>
          <script type="text/javascript" src="validation.js"></script>
    </div>
    <footer>
        <fmt:message key='footer.content'/>
    </footer>
</body>
<html>