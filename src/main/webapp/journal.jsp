<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
    <link rel="stylesheet" type="text/css" href="style.css">
    <script type="text/javascript" src="myscripts.js"></script>
    <script type="text/javascript" src="tablescripts.js"></script>
<body>
<c:set var="lastpage" value="journal" scope="session" />
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
         <h2 class="main-h2"><fmt:message key='journal_page.journal.h'/> ${viewedCourse}</h2>
         <c:if test="${infoMessage != null}">
         <div class="info-box"> <span class="info-span"> ${infoMessage} </span> </div>
         <c:remove var="infoMessage"/>
         </c:if>
         <c:if test="${errorMessage != null}">
         <div class="info-box"> <span class="error-span"> ${errorMessage} </span> </div>
         <c:remove var="errorMessage"/>
         </c:if>
         <div> <span class="stealth-span"> hidden </span> </div>
       </div>
       <div class="tools">
         <c:if test="${userRole == 'Teacher' || userRole == 'Admin'}">
         <c:if test="${studentList == null || empty studentList}">
         <b> <fmt:message key='journal_page.no_students'/> </b>
         </c:if>
         <c:if test="${studentList != null && not empty studentList}">
          <table class="courseTable">
            <thead>
              <tr>
              <th id="courseHead" class="num" aria-sort="ascending">
                <button> <fmt:message key='journal_page.student_id'/>
                <span aria-hidden="true"></span>
                </button>
                </th>
              <th id="courseHead">
                <button> <fmt:message key='journal_page.student_name'/>
                <span aria-hidden="true"></span>
                </button>
              </th>
              <th id="courseHead">
                <button> <fmt:message key='journal_page.student_last_name'/> name
                <span aria-hidden="true"></span>
                </button>
              </th>
              <th id="courseHead">
                <fmt:message key='journal_page.current_mark'/>
              </th>
              <th id="courseHead">
                <fmt:message key='journal_page.set_mark'/>
              </th>
              </tr>
            </thead>
            <tbody>
               <c:forEach items="${studentList}" var="student">
                 <tr class="courseBody">
                 <td class="num"> ${student.studentID} </td>
                 <td> ${student.name} </td>
                 <td> ${student.lastName} </td>
                 <td> ${student.markForCurrentCourse} </td>
                 <td>
                    <form action="journal?student_id=${student.studentID}" method="post">
                    <input type="text" name="mark" id="mark" required>
                    <button type="submit" class="markbtn"> <fmt:message key='journal_page.set_mark.button'/>
                    </form>
                 </td>
                 </tr>
               </c:forEach>
            </tbody>
          </table>
       </c:if>
       </c:if>
       </div>
    </div>
    <footer>
        <fmt:message key='footer.content'/>
    </footer>
</body>
</html>