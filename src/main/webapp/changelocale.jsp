<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${param.locale}" scope="session"/>

<fmt:setBundle basename="resources"/>

<c:set var="currentLocale" value="${param.locale}" scope="session"/>

<c:if test="${lastpage == null}">
<c:redirect url="home"/>
</c:if>
<c:if test="${lastpage != null}">
<c:redirect url="${lastpage}"/>
</c:if>
