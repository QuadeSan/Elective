<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
    <link rel="stylesheet" type="text/css" href="style.css">
    <script type="text/javascript" src="myscripts.js"></script>
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
       <div class="account-head">
         <h2 class="main-h2">My account</h2>
         <c:if test="${errorMessage != null}">
         <div class="info-box"> <span class="error-span"> ${errorMessage} </span> </div>
         <c:remove var="errorMessage"/>
         </c:if>
         <div> <span class="stealth-span"> hidden </span> </div>
       </div>
         <form class="edit-form" action="account" method="post">
                <div class="info-container">
                <p>USER INFORMATION</p>
                <div class="col-1-2">
                    <label><b>Login</b></label>
                    <input type="text" placeholder="New Login" name="login" class="account-field" autocomplete="off">
                </div>
                <div class="col-1-2">
                    <label><b>Email</b></label>
                    <input type="text" placeholder="New Email" name="email" class="account-field" autocomplete="off">
                </div>
                <div class="col-1-2">
                    <label><b>First name</b></label>
                    <input type="text" placeholder="New First name" name="firstname" class="account-field" autocomplete="off">
                </div>
                <div class="col-1-2">
                    <label><b>Last name</b></label>
                    <input type="text" placeholder="New Last name" name="lastname" class="account-field" autocomplete="off">
                </div>
                <div class="col-1-2">
                    <label><b>Password</b></label>
                    <input type="password" placeholder="New Password" name="psw" class="account-field">
                </div>
                <div class="col-1-2">
                    <label><b>Repeat Password</b></label>
                    <input type="password" placeholder="Repeat Password" name="psw-repeat" class="account-field">
                </div>
            <hr>
            <p>CONTACT INFORMATION</p>
                <div class="col-1-2">
                    <label><b>Country</b></label>
                    <input type="text" placeholder="New Country" name="country" class="account-field" autocomplete="off">
                </div>
                <div class="col-1-2">
                    <label><b>City</b></label>
                    <input type="text" placeholder="New City" name="city" class="account-field" autocomplete="off">
                </div>
                <div class="col-1-2">
                    <label><b>Postal code</b></label>
                    <input type="text" pattern="[0-9]{5}" placeholder="New Postal code" name="postal-code" class="account-field" autocomplete="off">
                </div>
                <div class="col-1-2">
                    <label><b>Address</b></label>
                    <input type="text" placeholder="New Address" name="city" class="account-field" autocomplete="off">
                </div>
                <div class="col-1-2">
                    <label><b>Phone number</b></label>
                    <input type="text" placeholder="+38(XXX)-XXX-XX-XX" name="phone-number" class="account-field" autocomplete="off">
                </div>
                <div class="col-1-1">
                    <hr>
                    <label><b>About me<b></label>
                    <textarea rows="4" placeholder="A few words about you ..." name="about-me"></textarea>
                 </div>
                 <button class="applybtn" type="submit"> Apply changes </button>
                 <button class="cancelbtn" onclick="history.back()">Cancel</button>
            </div>
         </form>
          <form class="container" action="account" method="post" onSubmit="return confirmAlert()">
          <button type="submit" class="cancelbtn"> Delete account
          <input type="hidden" name="user-id" value="${currentUser.userID}">
          </form>
    </div>
    <footer>
        <fmt:message key='footer.content'/>
    </footer>
</body>
</html>