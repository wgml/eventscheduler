<%@ page import="pl.wgml.eventscheduler.dao.pojo.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    User loggedUser = (User) session.getAttribute("loggedUser");
%>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/">Event scheduler</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li><a href="/users">Users</a></li>
                <li><a href="/events">Events</a></li>
                <li><a href="/editevent">Add event</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <c:choose>
                    <c:when test="${empty loggedUser}">
                        <li><a href="/login">Login</a></li>
                        <li><a href="/register">Register</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="/user?id=${loggedUser.getId()}">Logged as ${loggedUser.getUsername()}</a></li>
                        <li><a href="/logout">Log out</a></li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
