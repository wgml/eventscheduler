<%--
  Created by IntelliJ IDEA.
  User: vka
  Date: 05.03.16
  Time: 13:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <script src="../js/bootstrap.min.js"></script>
    <title>Events</title>
</head>
<body>
<%@include file="navbar.jsp"%>
<c:if test="${not empty message}">
    <div class="alert alert-success">
            ${message}
    </div>
</c:if>
<div class="container">
    <h3>Search events</h3>
    <form action="/events" method="get" id="searchByName" role="form" class="form-inline">
        <div class="row">
            <input type="hidden" id="searchBy" name="searchBy" value="date"/>
            <div class="form-group">
                <label for="date">On exact day</label>
                <input type="date" class="form_datetime form-control" id="date" name="date"/>
            </div>
            <div class="form-group">
                <label for="after">After</label>
                <input type="date" name="after" id="after" class="form-control"/>
            </div>
            <button type="submit" class="btn btn-info">
                <span class="glyphicon glyphicon-search"></span> Search
            </button>
        </div>
    </form>
</div>
<div class="container">
    <h3>List of events</h3>
    <table class="table table-striped">
        <thead>
        <tr>
            <td>#</td>
            <td>What is it</td>
            <td>Created by</td>
            <td>Starting</td>
            <td>Ending</td>
        </tr>
        </thead>

        <c:forEach var="event" items="${eventList}">
            <tr>
                <td>
                    <a href="/events?id=${event.id}&searchBy=id">${event.id}</a>
                </td>
                <td>
                    <a href="/event?id=${event.id}">${event.name}</a></td>
                <td>
                    <a href="/user?id=${event.creator.id}">${event.creator.username}</a>
                </td>
                <td>
                    <fmt:formatDate type="both" value="${event.startDate}"/>
                </td>
                <td>
                    <fmt:formatDate type="both" value="${event.endDate}"/>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
