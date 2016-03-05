<%--
  Created by IntelliJ IDEA.
  User: vka
  Date: 05.03.16
  Time: 13:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <script src="../js/bootstrap.min.js"></script>
    <title>Users</title>
</head>
<body>
<%@include file="navbar.jsp"%>
<c:if test="${not empty message}">
    <div class="alert alert-success">
            ${message}
    </div>
</c:if>

<div class="container">
    <h3>Search users</h3>
    <form action="/users" method="get" id="searchByName" role="form">
        <input type="hidden" id="searchBy" name="searchBy" value="name"/>
        <div class="form-group col-xs-5">
            <input type="text" name="name" id="name" class="form-control" required="required"
                   placeholder="Type desired username or email"/>
        </div>
        <button type="submit" class="btn btn-info">
            <span class="glyphicon glyphicon-search"></span> Search
        </button>
    </form>
    <form action="/users" method="post" id="deleteForm" role="form" >
        <input type="hidden" id="userId" name="userId">
        <input type="hidden" id="action" name="action">
    </form>
</div>

<div class="container">
    <h3>List of users</h3>
    <table class="table table-striped">
        <thead>
        <tr>
            <td>#</td>
            <td>Username</td>
            <td>E-mail</td>
            <td>Role</td>
            <td></td>
        </tr>
        </thead>

        <c:forEach var="user" items="${userList}">
            <tr class="${classSuccess}">
                <td>
                    <a href="/users?id=${user.id}&searchBy=id">${user.id}</a>
                </td>
                <td><a href="/user?id=${user.id}">${user.username}</a></td>
                <td>${user.email}</td>
                <td>
                    <c:choose>
                        <c:when test="${user.userType eq 'ADMINISTRATOR'}">
                            <span class="glyphicon glyphicon-heart">Administrator</span>
                        </c:when>
                        <c:otherwise>
                            <span class="glyphicon glyphicon-heart-empty">Regular user</span>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td><a href="#" id="remove"
                       onclick="document.getElementById('action').value = 'delete';document.getElementById('userId').value = '${user.id}';

                               document.getElementById('deleteForm').submit();">
                    <span class="glyphicon glyphicon-trash"></span>
                </a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>
