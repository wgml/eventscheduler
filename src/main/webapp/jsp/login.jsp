<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <script src="../js/bootstrap.min.js"></script>
    <title>Login</title>
</head>
<body>
<%@include file="navbar.jsp"%>
<div class="container">
    <div id="loginbox" style="margin-top:25px;" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
        <div class="panel panel-info">
            <div class="panel-heading">
                <div class="panel-title">Sign In</div>
                <div style="float:right; font-size: 85%; position: relative; top:-10px"><a href="/register">Sign Up</a></div>
            </div>
            <div style="padding-top:30px" class="panel-body">
                <c:if test="${not empty warnMessage}">
                    <div id="login-alert" class="alert alert-danger col-sm-12">${warnMessage}</div>
                </c:if>
                <form id="loginform" class="form-horizontal" role="form" action="/login" method="post">

                    <div style="margin-bottom: 25px" class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                        <input id="login-username" type="text" class="form-control" name="username" value=""
                               placeholder="username">
                    </div>

                    <div style="margin-bottom: 25px" class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                        <input id="login-password" type="password" class="form-control" name="password"
                               placeholder="password">
                    </div>

                    <button type="submit" class="btn btn-info">
                        <span class="glyphicon glyphicon-ok"></span> Login
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
