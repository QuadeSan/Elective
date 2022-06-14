<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <link rel="stylesheet" type="text/css" href="style.css">
    <script type="text/javascript" src="myscripts.js"></script>
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
       <c:if test="${infoMessage != null}">
       <div style="margin-top:25px;"> <span class="info-span"> ${infoMessage} </span> </div>
       <c:remove var="infoMessage"/>
       </c:if>
       <a href="editaccount">
            <button class="editbtn"> Edit profile </button>
       </a>
       </div>
       <div class="info-container">

         <p>USER INFORMATION</p>
            <div class="col-1-2">
                <label><b>Login</b></label>
                <input type="text" value="${currentUser.login}" name="login" id="account-field" disabled>
            </div>
            <div class="col-1-2">
                <label><b>Email</b></label>
                <input type="text" value="${currentUser.email}" name="email" id="account-field" disabled>
            </div>
            <div class="col-1-2">
                <label><b>First name</b></label>
                <input type="text" value="${currentUser.name}" name="firstname" id="account-field" disabled>
            </div>
            <div class="col-1-2">
                <label><b>Last name</b></label>
                <input type="text" value="${currentUser.lastName}" name="lastname" id="account-field" disabled>
            </div>
         <hr>
         <p>CONTACT INFORMATION</p>
            <div class="col-1-2">
                <label for="Country"><b>Country</b></label>
                <input type="text" placeholder="New Country" name="country" id="account-field" disabled>
            </div>
            <div class="col-1-2">
                <label for="City"><b>City</b></label>
                <input type="text" placeholder="New City" name="city" id="account-field" disabled>
            </div>
            <div class="col-1-2">
                <label for="Postal code"><b>Postal code</b></label>
                <input type="text" pattern="[0-9]{5}" placeholder="New Postal code" name="postal-code" id="account-field" disabled>
            </div>
            <div class="col-1-2">
                <label for="Address"><b>Address</b></label>
                <input type="text" placeholder="New Address" name="city" id="account-field" disabled>
            </div>
            <div class="col-1-2">
                 <label for="Phone number"><b>Phone number</b></label>
                <input type="text" placeholder="+38(XXX)-XXX-XX-XX" name="phone-number" id="account-field" disabled>
            </div>
            <div class="col-1-1">
                <hr>
                <label for="About me"><b>About me<b></label>
                <textarea rows="4" placeholder="A few words about you ..." name="about-me" disabled></textarea>
            </div>
       </div>
       <c:if test="${userRole != 'Admin'}">
       <h2 class="main-h2"> My courses </h2> <br>
       <c:if test="${empty currentUser.courses}">
       <div class="myCourses">
       You have not assigned for any course yet
       </div>
       </c:if>
       <c:if test="${not empty currentUser.courses}">
       <div class="myCourses">
                <table class="courseTable">
                  <thead>
                    <tr>
                      <th id="courseHead" class="num" aria-sort="ascending">
                        <button> Course ID
                        <span aria-hidden="true"></span>
                        </button>
                      </th>
                      <th id="courseHead">
                        <button> Title
                        <span aria-hidden="true"></span>
                        </button>
                      </th>
                      <th id="courseHead">
                        <button> Status
                        <span aria-hidden="true"></span>
                        </button>
                      </th>
                      <th id="courseHead">
                         Quite course
                      </th>
                    </tr>
                  </thead>
                  <tbody>
                    <c:forEach items="${currentUser.courses}" var="course">
                       <tr class="courseBody">
                       <td class="num"> ${course.courseID} </td>
                       <td> ${course.title} </td>
                       <td> ${course.status} </td>
                       <td> <a class="ablack" href="account?course_id=${course.courseID}" onclick="confirmAlert()"> Leave course </a> </td>
                       </tr>
                    </c:forEach>
                  </tbody>
                </table>
       </div>
       </c:if>
       </c:if>
    </div>
    <footer>
        Here should be some footer information
    </footer>
</body>
</html>