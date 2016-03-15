<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <script src="../js/bootstrap.min.js"></script>
    <title>Register</title>
</head>
<body>
<%@include file="navbar.jsp"%>
<c:if test="${not empty successMsg}">
    <div class="alert alert-success">
            ${successMsg}
    </div>
</c:if>
<div class="container">
    <div id="loginbox" style="margin-top:25px;" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
        <div class="panel panel-info">
            <div class="panel-heading">
                <div class="panel-title">Sign Up</div>
                <div style="float:right; font-size: 85%; position: relative; top:-10px"><a href="/login">Sign In</a></div>
            </div>
            <div class="panel-body">
                <form id="signupform" class="form-horizontal" role="form" action="/register" method="post">

                    <c:if test="${not empty warnMessage}">
                        <div id="login-alert" class="alert alert-danger col-sm-12">${warnMessage}</div>
                    </c:if>

                    <div style="margin-bottom: 25px" class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                        <input id="register-username" type="text" class="form-control" name="username" value=""
                               placeholder="What should we call you">
                    </div>

                    <div style="margin-bottom: 25px" class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                        <input id="register-password" type="password" class="form-control" name="password"
                               placeholder="At least 6 characters">
                    </div>

                    <div style="margin-bottom: 25px" class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-envelope"></i></span>
                        <input id="register-email" type="text" class="form-control" name="email" value=""
                               placeholder="Your email">
                    </div>

                    <button type="submit" class="btn btn-info">
                        <span class="glyphicon glyphicon-ok"></span> Register
                    </button>
                </form>
            </div>
        </div>
</div>
</body>
</html>
