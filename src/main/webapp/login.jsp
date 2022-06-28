<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="style.css">
    <script type="text/javascript" src="myscripts.js"></script>
</head>
<body>
 <c:if test="${infoMessage != null}">
    <div class="info-box"> <span class="info-span"> ${infoMessage} </span> </div>
    <c:remove var="infoMessage" scope="session" />
 </c:if>
 <div class="info-box"> <span class="error-span"> ${errorMessage} </span> </div>
 <c:remove var="errorMessage" scope="session" />
 <form class="form-centered" action="main" method="post">

   <div class="container">
     <label for="uname"><b>Username</b></label>
     <input type="text" placeholder="Enter Username" name="uname" id="uname" required>
     <label for="psw"><b>Password</b></label>
     <input type="password" placeholder="Enter Password" name="psw" id="psw" required>
     <label>
       <input class="checkmark" type="checkbox" onclick="showPass()"> Show Password
     </label>
     <button type="submit">Login</button>
     <label>
       <input class="checkmark" type="checkbox" name="remember"> Remember me
     </label>
   </div>

    <div class="container" id="flex">
         <button type="button" class="cancelbtn" onclick="history.back()">Cancel</button>
            <a href="register">
                <button type="button" class="registerbtn">Don&#39t have account yet?</button>
            </a>
         <a href="#">
             <button type="button" class="registerbtn">Forgot <u>password?</u></button>
         </a>
    </div>
 </form>
</body>
<html>