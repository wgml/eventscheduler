<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <script src="../js/bootstrap.min.js"></script>
    <title>${event.name}</title>
</head>
<body>
<%@include file="navbar.jsp"%>
<c:if test="${not empty message}">
    <div class="alert alert-success">
            ${message}
    </div>
</c:if>

<div class="container">
    <h3>${event.name}</h3>
    <table class="table table-striped">
        <thead>
        <tr>
            <td>What is it</td>
            <td>Starting</td>
            <td>Ending</td>
            <td></td>
        </tr>
        </thead>
        <tr>
            <td>
                ${event.name}
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
    </table>
</div>

<div class="container">
    <h4>Invitations</h4>
    <table class="table table-striped">
        <thead>
        <tr>
            <td>User</td>
            <td>Current status</td>
            <td></td>
        </tr>
        </thead>
        <c:forEach var="inv" items="${invitations}">
        <tr>
            <td>
                <a href="/user?id=${inv.user.id}">${inv.user.username}</a>
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
            <a href="#" id="delete"
               onclick="document.getElementById('action').value = 'delete';document.getElementById('deletedUserId').value = '${inv.user.id}';

                       document.getElementById('invitationForm').submit();">
                <span class="glyphicon glyphicon-remove"></span>
            </a>
        </td>
        </tr>
        </c:forEach>
        </table>

    <h4>Invite someone</h4>
    <form action="/event?id=${event.id}" method="post" id="invitationForm" class="form-inline" role="form">
        <input type="hidden" id="deletedUserId" name="deletedUserId">
        <select class="form-control" id="userId" name="userId">
            <%--@elvariable id="notInvitedUsers" type=""--%>
            <c:forEach var="user" items="${notInvitedUsers}">
                <option value="${user.id}">
                    ${user.username}
                </option>
            </c:forEach>
        </select>
        <%--<input type="hidden" id="userId" name="userId">--%>
        <input type="hidden" id="action" name="action" value="invite">
        <button type="submit" class="btn btn-info" <c:if test="${notInvitedUsers.isEmpty()}">disabled="disabled"</c:if>>
            <span class="glyphicon glyphicon-plus"></span> Invite
        </button>
    </form>
</div>

</body>
</html>
