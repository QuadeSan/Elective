<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="myLib" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="customLib" uri="http://Elective" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="style.css">
    <script type="text/javascript" src="myscripts.js"></script>
    <script type="text/javascript" src="tablescripts.js"></script>
</head>
<body>
<c:set var="lastpage" value="account" scope="session" />
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
       <div class="account-head">
       <h2 class="main-h2"><fmt:message key='account_page.account.h'/></h2>
       <c:if test="${infoMessage != null}">
       <div style="margin-top:25px;"> <span class="info-span"> ${infoMessage} </span> </div>
       <c:remove var="infoMessage"/>
       </c:if>
       <a href="editaccount">
            <button class="editbtn"> <fmt:message key='account_page.edit_profile.button'/> </button>
       </a>
       </div>
       <div class="info-container">

         <p><fmt:message key='account_page.user_information'/></p>
            <div class="col-1-2">
                <label><b><fmt:message key='account_page.login_field'/></b></label>
                <input type="text" value="${currentUser.login}" name="login" class="account-field" disabled>
            </div>
            <div class="col-1-2">
                <label><b><fmt:message key='account_page.email_field'/></b></label>
                <input type="text" value="${currentUser.email}" name="email" class="account-field" disabled>
            </div>
            <div class="col-1-2">
                <label><b><fmt:message key='account_page.first_name_field'/></b></label>
                <input type="text" value="${currentUser.name}" name="firstname" class="account-field" disabled>
            </div>
            <div class="col-1-2">
                <label><b><fmt:message key='account_page.last_name_field'/></b></label>
                <input type="text" value="${currentUser.lastName}" name="lastname" class="account-field" disabled>
            </div>
         <hr>
         <p><fmt:message key='account_page.contact_info'/></p>
            <div class="col-1-2">
                <label for="Country"><b><fmt:message key='account_page.contact_country'/></b></label>
                <input type="text" placeholder="New Country" name="country" class="account-field" disabled>
            </div>
            <div class="col-1-2">
                <label for="City"><b><fmt:message key='account_page.contact_city'/></b></label>
                <input type="text" placeholder="New City" name="city" class="account-field" disabled>
            </div>
            <div class="col-1-2">
                <label for="Postal code"><b><fmt:message key='account_page.contact_postal_code'/></b></label>
                <input type="text" pattern="[0-9]{5}" placeholder="New Postal code" name="postal-code" class="account-field" disabled>
            </div>
            <div class="col-1-2">
                <label for="Address"><b><fmt:message key='account_page.contact_address'/></b></label>
                <input type="text" placeholder="New Address" name="city" class="account-field" disabled>
            </div>
            <div class="col-1-2">
                 <label for="Phone number"><b><fmt:message key='account_page.contact_phone'/></b></label>
                <input type="text" placeholder="+38(XXX)-XXX-XX-XX" name="phone-number" class="account-field" disabled>
            </div>
            <div class="col-1-1">
                <hr>
                <label for="About me"><b><fmt:message key='account_page.contact_about_me'/><b></label>
                <textarea rows="4" placeholder="<fmt:message key='account_page.contact_about_me.field'/>" name="about-me" disabled></textarea>
            </div>
       </div>
       <div class="tools">
         <myLib:courseList role="${userRole}" courses="${currentUser.courses}"/>
       </div>
    </div>
    <footer>
        <fmt:message key='footer.content'/>
    </footer>
</body>
</html>