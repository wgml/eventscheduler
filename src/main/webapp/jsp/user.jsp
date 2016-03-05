<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <script src="../js/bootstrap.min.js"></script>
    <title>${user.username}</title>
</head>
<body>
<%@include file="navbar.jsp"%>
<c:if test="${not empty message}">
    <div class="alert alert-success">
            ${message}
    </div>
</c:if>

<form action="/user?id=${user.id}" method="post" id="acceptForm" role="form">
    <input type="hidden" id="invId" name="invId">
    <input type="hidden" id="action" name="action">
</form>

<div class="container">
    <h3>${user.username} profile</h3>

    <h4>Organized events</h4>
    <table class="table table-striped">
        <thead>
        <tr>
            <td>#</td>
            <td>What is it</td>
            <td>Starting</td>
            <td>Ending</td>
            <td></td>
        </tr>
        </thead>
    <c:forEach var="event" items="${events}">
        <tr>
            <td>
                <a href="/event?id=${event.id}">${event.id}</a>
            </td>
            <td>
                <a href="/event?id=${event.id}">${event.name}</a>
            </td>
            <td>
                <fmt:formatDate type="both" value="${event.startDate}"/>
            </td>
            <td>
                <fmt:formatDate type="both" value="${event.endDate}"/>
            </td>
            <td>
                <a href="/event?id=${event.id}">
                    <span class="glyphicon glyphicon-cog"></span>
                </a>
            </td>
        </tr>
    </c:forEach>
    </table>
</div>

<div class="container">
    <h4>Invitations</h4>
    <table class="table table-striped">
        <thead>
        <tr>
            <td>Event</td>
            <td>Starting</td>
            <td>Current status</td>
            <td></td>
        </tr>
        </thead>
        <c:forEach var="inv" items="${invitations}">
        <tr>
            <td>
                <a href="/event?id=${inv.event.id}">${inv.event.name}</a>
            </td>
            <td>
                <fmt:formatDate type="both" value="${inv.event.startDate}"/>
            </td>
            <td>
                <c:choose>
                    <c:when test="${inv.accepted == true}">
                        Going
                    </c:when>
                    <c:otherwise>
                        Ignoring
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
            <td>
            <a href="#" id="accept"
                   onclick="document.getElementById('action').value = 'accept';document.getElementById('invId').value = '${inv.id}';

                           document.getElementById('acceptForm').submit();">
                <span class="glyphicon glyphicon-ok"></span>
            </a>
            <a href="#" id="ignore"
               onclick="document.getElementById('action').value = 'ignore';document.getElementById('invId').value = '${inv.id}';

                       document.getElementById('acceptForm').submit();">
                <span class="glyphicon glyphicon-remove"></span>
            </a>
        </td>
        </tr>
        </c:forEach>
</div>

</body>
</html>
