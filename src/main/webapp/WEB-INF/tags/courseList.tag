<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="role" required="true" %>
<%@ attribute name="courses" required="true" type="java.lang.Iterable" %>

<c:if test="${role != 'Admin'}">
         <h2> <fmt:message key='account_page.my_courses'/> </h2> <br>
         <c:if test="${empty currentUser.courses}">
         <div class="myCourses">
         <fmt:message key='account_page.no_courses'/>
         </div>
         </c:if>
         <c:if test="${not empty currentUser.courses}">
          <table class="courseTable">
            <thead>
              <tr>
              <th id="courseHead" class="num" aria-sort="ascending">
                <button> <fmt:message key='courses_page.course_id'/>
                <span aria-hidden="true"></span>
                </button>
                </th>
              <th id="courseHead">
                <button> <fmt:message key='courses_page.topic'/>
                <span aria-hidden="true"></span>
                </button>
              </th>
              <th id="courseHead">
                <button> <fmt:message key='courses_page.title'/>
                <span aria-hidden="true"></span>
                </button>
              </th>
              <th id="courseHead">
                 <button> <fmt:message key='courses_page.status'/>
                 <span aria-hidden="true"></span>
                 </button>
              </th>
              <c:if test="${role == 'Student'}">
              <th id="courseHead">
                 <fmt:message key='account_page.course_mark'/>
              </th>
              <th id="courseHead">
                  <fmt:message key='account_page.course_quit'/>
              </th>
              </c:if>
              <c:if test="${role == 'Teacher'}">
              <th id="courseHead">
                  <fmt:message key='account_page.course_journal'/>
              </th>
              </c:if>
              </tr>
            </thead>
            <tbody>
               <c:forEach items="${courses}" var="course">
                 <tr class="courseBody">
                 <td class="num"> ${course.courseID} </td>
                 <td> ${course.topic} </td>
                 <td> ${course.title} </td>
                 <td> ${course.status} </td>
               <c:if test="${role == 'Student'}">
                 <td> ${course.mark} </td>
                 <td> <a class="ablack" href="account?course_id=${course.courseID}" onclick="return confirmAlert()"> <fmt:message key='account_page.course_quit.link'/> </a> </td>
               </c:if>
               <c:if test="${role == 'Teacher'}">
                 <td>
                    <a class="ablack" href="journal?viewedCourse=${course.courseID}"> <fmt:message key='account_page.course_journal.link'/> </a>
                 </td>
               </c:if>
                 </tr>
               </c:forEach>
            </tbody>
          </table>
         </c:if>
</c:if>