<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="style.css">
    <script type="text/javascript" src="myscripts.js"></script>
</head>
<body>
<c:set var="lastpage" value="register" scope="session" />
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
 <div class="info-box"> <span class="error-span"> ${errorMessage} </span> </div>
 <c:remove var="errorMessage" scope="session" />
 <form class="form-centered" id="for-validation" action="login" method="post">
   <div class="container">
     <h1><fmt:message key='register.register.h'/></h1>
     <p><fmt:message key='register.fill_form.text'/></p>
     <hr>
    <div class="form-field">
     <label for="email"><b><fmt:message key='register.email'/></b></label>
     <input type="text" placeholder="<fmt:message key='register.email.ph'/>" name="email" id="email" autocomplete="off">
     <small> </small>
    </div>
    <div class="form-field">
     <label for="name"><b><fmt:message key='register.name'/></b></label>
     <input type="text" placeholder="<fmt:message key='register.name.ph'/>" name="name" id="name" autocomplete="off">
     <small> </small>
    </div>
    <div class="form-field">
     <label for="lastName"><b><fmt:message key='register.last_name'/></b></label>
     <input type="text" placeholder="<fmt:message key='register.last_name.ph'/>" name="lastName" id="lastName" autocomplete="off">
     <small> </small>
    </div>
    <div class="form-field">
     <label for="login"><b><fmt:message key='register.login'/></b></label>
     <input type="text" placeholder="<fmt:message key='register.login'/>" name="login" id="login" autocomplete="off">
     <small> </small>
    </div>
    <div class="form-field">
     <label for="psw"><b><fmt:message key='register.password'/></b></label>
     <input type="password" placeholder="<fmt:message key='register.password.ph'/>" name="psw" id="psw" autocomplete="off">
     <small> </small>
    </div>
    <div class="form-field">
     <label for="psw-repeat"><b><fmt:message key='register.password_repeat'/></b></label>
     <input type="password" placeholder="<fmt:message key='register.password_repeat.ph'/>" name="psw-repeat" id="psw-repeat" autocomplete="off">
     <small> </small>
    </div>
     <hr>

     <p><fmt:message key='register.terms.text'/> <a class="ablack" href="#"><fmt:message key='register.terms.link'/></a>.</p>
     <button type="submit"><fmt:message key='register.register.button'/></button>
     <p><fmt:message key='register.have_account.text'/> <a class="ablack" href="login" ><fmt:message key='register.sign_in.link'/></a>.</p>
   </div>
 </form>
 <script type="text/javascript" src="validation.js"></script>
</body>
<html>