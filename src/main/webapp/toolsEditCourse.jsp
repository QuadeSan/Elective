<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="customLib" uri="http://Elective" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="style.css">
    <script type="text/javascript" src="myscripts.js"></script>
    <script type="text/javascript" src="tablescripts.js"></script>
</head>
<body>
<c:set var="lastpage" value="toolseditcourse" scope="session" />
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
    <h2 class="tools"> <fmt:message key='tools_courses.course_info_lc'/> </h2>
    <table class="editCourseTable" id="editCourseTable">
          <thead>
            <tr>
              <th id="courseHead">
              <fmt:message key='courses_page.course_id'/>
              </th>
              <th id="courseHead">
              <fmt:message key='courses_page.topic'/>
              </th>
              <th id="courseHead">
              <fmt:message key='courses_page.title'/>
              </th>
              <th id="courseHead">
              <fmt:message key='courses_page.status'/>
              </th>
              <th id="courseHead">
              <fmt:message key='courses_page.teacher'/>
              </th>
            </tr>
          </thead>
          <tbody>
               <tr class="courseBody">
               <td> ${editedCourse.courseID} </td>
               <td> ${editedCourse.topic} </td>
               <td> ${editedCourse.title} </td>
               <td> ${editedCourse.status} </td>
               <td> ${editedCourse.assignedTeacher} </td>
               </tr>
          </tbody>
        </table>
        <br>
         <form class="edit-form" action="toolscourses" method="post">
                <div class="info-container">
                <p><fmt:message key='tools_courses.course_info'/></p>
                <div class="col-1-2">
                    <label><b><fmt:message key='tools_courses.new_title'/></b></label>
                    <input type="text" placeholder="<fmt:message key='tools_courses.new_title'/>" name="new-title" class="account-field" autocomplete="off">
                </div>
                <div class="col-1-2">
                    <label><b><fmt:message key='tools_courses.new_topic'/></b></label>
                    <input type="text" placeholder="<fmt:message key='tools_courses.new_topic'/>" name="new-topic" class="account-field" autocomplete="off">
                </div>
                <div class="col-1-2">
                    <label><b><fmt:message key='tools_courses.new_status'/></b></label>
                    <select id="statusSelect" name="new-status">
                    <option value=""> </option>
                    <option value="New"> <fmt:message key='tools_courses.status_new'/> </option>
                    <option value="In progress"> <fmt:message key='tools_courses.status_in_progress'/> </option>
                    <option value="Finished"> <fmt:message key='tools_courses.status_finished'/> </option>
                    </select>
                </div>
                <div class="col-1-2">
                    <label><b><fmt:message key='tools_courses.new_teacher'/></b></label>
                    <select id="teacherSelect" name="teacher-id">
                    <option value="0"> </option>
                    <c:forEach items="${teachers}" var="teacher">
                    <option value="${teacher.teacherID}"> ${teacher.teacherID} ${teacher.name} ${teacher.lastName} </option>
                    </c:forEach>
                    </select>
                </div>
                <input type="hidden" name="adminAction" value="editCourse">
                <button class="applybtn" type="submit"> <fmt:message key='tools_courses.apply'/> </button>
                </div>
         </form>
    </div>
    <footer>
        <fmt:message key='footer.content'/>
    </footer>
</body>
</html>