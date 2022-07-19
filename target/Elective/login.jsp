<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="style.css">
    <script type="text/javascript" src="myscripts.js"></script>
</head>
<body>
<c:set var="lastpage" value="login" scope="session" />
    <div class="top-corner">
            <form action="changelocale" method="post">
            		<select name="locale" onchange="this.form.submit()">
            		    <option value=""><fmt:message key='header.dropdown.language'/></option>
            			<c:forEach items="${applicationScope.locales}" var="locale">
            				<option value="${locale.key}"> ${locale.value} </option>
            			</c:forEach>
            		</select>
            </form>
    </div>
 <c:if test="${infoMessage != null}">
    <div class="info-box"> <span class="info-span"> ${infoMessage} </span> </div>
    <c:remove var="infoMessage" scope="session" />
 </c:if>
 <div class="info-box"> <span class="error-span"> ${errorMessage} </span> </div>
 <c:remove var="errorMessage" scope="session" />
 <form class="form-centered" action="main" method="post">

   <div class="container">
     <label for="uname"><b><fmt:message key='register.login'/></b></label>
     <input type="text" placeholder="<fmt:message key='login_page.login.ph'/>" name="uname" id="uname" required>
     <label for="psw"><b><fmt:message key='register.password'/></b></label>
     <input type="password" placeholder="<fmt:message key='login_page.password.ph'/>" name="psw" id="psw" required>
     <label>
       <input class="checkmark" type="checkbox" onclick="showPass()"> <fmt:message key='login_page.show_password'/>
     </label>
     <button type="submit"><fmt:message key='login_page.login.button'/></button>
     <label>
       <input class="checkmark" type="checkbox" name="remember"> <fmt:message key='login_page.remember_me'/>
     </label>
   </div>

    <div class="container" id="flex">
         <button type="button" class="cancelbtn" onclick="history.back()"><fmt:message key='login_page.cancel.button'/></button>
            <a href="register">
                <button type="button" class="registerbtn"><fmt:message key='login_page.no_account_yet'/></button>
            </a>
         <a href="#">
             <button type="button" class="registerbtn"><fmt:message key='login_page.forgot_password'/></button>
         </a>
    </div>
 </form>
</body>
<html>