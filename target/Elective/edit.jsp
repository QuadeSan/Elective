<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <link rel="stylesheet" type="text/css" href="style.css">
<body>
    <header>
        <div id="homebutton"><a class="ahead" href="main">Home</a></div>
        <div id="coursesearch"><a class="ahead" href="courses">Courses</a></div>
        <div id="cabinet"><a class="ahead" href="account">My account</a></div>
        <c:if test="${userRole == 'Admin'}">
            <div id="god-mode"><a class="ahead" href="admin">God mode on</a></div>
        </c:if>
        <div id="hellouser">
        Hello, <a class="ahead" href="account">${currentUser.login}</a> !
        </div>
        <c:if test="${currentUser.login != 'guest'}">
        <div id="logoutbtn"><a class="ahead" href="logout">Logout</a></div>
        </c:if>
        <c:if test="${currentUser.login == 'guest'}">
            <div id="loginbtn"><a class="ahead" href="login">Login</a></div>
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
                    <input type="text" placeholder="New Login" name="login" id="account-field">
                </div>
                <div class="col-1-2">
                    <label><b>Email</b></label>
                    <input type="text" placeholder="New Email" name="email" id="account-field">
                </div>
                <div class="col-1-2">
                    <label><b>First name</b></label>
                    <input type="text" placeholder="New First name" name="firstname" id="account-field">
                </div>
                <div class="col-1-2">
                    <label><b>Last name</b></label>
                    <input type="text" placeholder="New Last name" name="lastname" id="account-field">
                </div>
                <div class="col-1-2">
                    <label><b>Password</b></label>
                    <input type="password" placeholder="New Password" name="psw" id="account-field">
                </div>
                <div class="col-1-2">
                    <label><b>Repeat Password</b></label>
                    <input type="password" placeholder="Repeat Password" name="psw-repeat" id="account-field">
                </div>
            <hr>
            <p>CONTACT INFORMATION</p>
                <div class="col-1-2">
                    <label><b>Country</b></label>
                    <input type="text" placeholder="New Country" name="country" id="account-field">
                </div>
                <div class="col-1-2">
                    <label><b>City</b></label>
                    <input type="text" placeholder="New City" name="city" id="account-field">
                </div>
                <div class="col-1-2">
                    <label><b>Postal code</b></label>
                    <input type="text" pattern="[0-9]{5}" placeholder="New Postal code" name="postal-code" id="account-field">
                </div>
                <div class="col-1-2">
                    <label><b>Address</b></label>
                    <input type="text" placeholder="New Address" name="city" id="account-field">
                </div>
                <div class="col-1-2">
                    <label><b>Phone number</b></label>
                    <input type="text" placeholder="+38(XXX)-XXX-XX-XX" name="phone-number" id="account-field">
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
    </div>
    <footer>
        Here should be some footer information
    </footer>
</body>
</html>