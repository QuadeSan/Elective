<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="style.css">
    <script type="text/javascript" src="myscripts.js"></script>
    <script type="text/javascript" src="tablescripts.js"></script>
</head>
<body>
<c:set var="lastpage" value="toolsstudents" scope="session" />
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
        <h1 class="tools"> <fmt:message key='tools_students.student_list'/> </h1>
    <div class="tools">
        <c:if test="${allStudentsList == null || empty allStudentsList}">
        <b> <fmt:message key='tools_students.no_students'/> </b>
        </c:if>
        <c:if test="${allStudentsList != null && not empty allStudentsList}">
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
                <button> <fmt:message key='journal_page.student_last_name'/>
                <span aria-hidden="true"></span>
                </button>
              </th>
              <th id="courseHead">
                <button> <fmt:message key='tools_students.student_status'/>
                <span aria-hidden="true"></span>
                </button>
              </th>
              <th id="courseHead">
                <fmt:message key='tools_students.lock'/>
              </th>
              </tr>
            </thead>
            <tbody>
               <c:forEach items="${allStudentsList}" var="student">
                 <tr class="courseBody">
                 <td class="num"> ${student.studentID} </td>
                 <td> ${student.name} </td>
                 <td> ${student.lastName} </td>
                 <td> ${student.status} </td>
                 <c:if test="${student.status == 'locked'}">
                 <td>
                    <form action="toolsstudents" method="post">
                    <input type="hidden" name="student_id" value="${student.studentID}">
                    <input type="hidden" name="status" value="unlocked">
                    <button type="submit" class="lockbtn"> <fmt:message key='tools_students.lock.unlock'/>
                    </form>
                 </td>
                 </c:if>
                 <c:if test="${student.status == 'unlocked'}">
                 <td>
                    <form action="toolsstudents" method="post">
                    <input type="hidden" name="student_id" value="${student.studentID}">
                    <input type="hidden" name="status" value="locked">
                    <button type="submit" class="lockbtn"> <fmt:message key='tools_students.lock.lock'/>
                    </form>
                 </td>
                 </c:if>
                 </tr>
               </c:forEach>
            </tbody>
          </table>
          <button id="fakebtn" disabled> <fmt:message key='tools_students.current_page.button'/> ${currentPage} </button>
          <c:if test="${currentPage == 1}">
          <button id="fakescrollbtn"> <fmt:message key='tools_students.previous.button'/> </button>
          </c:if>
          <c:if test="${currentPage != 1}">
          <a id="abutton" href="toolsstudents?current-page=${currentPage -1}"> <button id="prevbtn"> <fmt:message key='tools_students.previous.button'/> </button> </a>
          </c:if>
          <c:if test="${currentPage lt pageCount}">
          <a id="abutton" href="toolsstudents?current-page=${currentPage +1}"> <button id="nextbtn"> <fmt:message key='tools_students.next.button'/> </button> </a>
          </c:if>
          <c:if test="${currentPage == pageCount}">
          <button id="fakescrollbtn"> <fmt:message key='tools_students.next.button'/> </button>
          </c:if>
          <button id="fakebtn" disabled> <fmt:message key='tools_students.total_page.button'/> ${pageCount} </button>
       </c:if>
    </div>
    </div>
    <footer>
        <fmt:message key='footer.content'/>
    </footer>
</body>
<html>