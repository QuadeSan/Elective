<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="style.css">
    <script type="text/javascript" src="myscripts.js"></script>
</head>
<body>
 <div class="info-box"> <span class="error-span"> ${errorMessage} </span> </div>
 <c:remove var="errorMessage" scope="session" />
 <form class="form-centered" id="for-validation" action="login" method="post">
   <div class="container">
     <h1>Register</h1>
     <p>Please fill in this form to create an account.</p>
     <hr>
    <div class="form-field">
     <label for="email"><b>Email</b></label>
     <input type="text" placeholder="Enter Email" name="email" id="email" autocomplete="off">
     <small> </small>
    </div>
    <div class="form-field">
     <label for="name"><b>First name</b></label>
     <input type="text" placeholder="Enter first name" name="name" id="name" autocomplete="off">
     <small> </small>
    </div>
    <div class="form-field">
     <label for="lastName"><b>Last name</b></label>
     <input type="text" placeholder="Enter last name" name="lastName" id="lastName" autocomplete="off">
     <small> </small>
    </div>
    <div class="form-field">
     <label for="login"><b>Login</b></label>
     <input type="text" placeholder="Enter Login" name="login" id="login" autocomplete="off">
     <small> </small>
    </div>
    <div class="form-field">
     <label for="psw"><b>Password</b></label>
     <input type="password" placeholder="Enter Password" name="psw" id="psw" autocomplete="off">
     <small> </small>
    </div>
    <div class="form-field">
     <label for="psw-repeat"><b>Repeat Password</b></label>
     <input type="password" placeholder="Repeat Password" name="psw-repeat" id="psw-repeat" autocomplete="off">
     <small> </small>
    </div>
     <hr>

     <p>By creating an account you agree to our <a class="ablack" href="#">Terms & Privacy</a>.</p>
     <button type="submit">Register</button>
     <p>Already have an account? <a class="ablack" href="login" >Sign in</a>.</p>
   </div>
 </form>
 <script type="text/javascript" src="validation.js"></script>
</body>
<html>