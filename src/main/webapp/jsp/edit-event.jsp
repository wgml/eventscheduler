<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <script src="../js/bootstrap.min.js"></script>
    <title>${event.name}</title>
</head>
<body>
<%@include file="navbar.jsp"%>
<c:if test="${not empty message}">
    <div class="alert alert-warning">
            ${message}
    </div>
</c:if>

<div class="container">
    <form action="/editevent" method="post" id="deleteForm" role="form">
        <input type="hidden" id="deletedEventId" name="deletedEventId">
        <input type="hidden" id="deleteAction" name="deleteAction">
    </form>
    <c:choose>
        <c:when test="${not empty event}">
            <h3>Edit ${event.name}
            <a href="#" id="remove"
                   onclick="document.getElementById('deleteAction').value = 'delete';document.getElementById('deletedEventId').value = '${event.id}';

                           document.getElementById('deleteForm').submit();">
                <span class="glyphicon glyphicon-trash"></span>
            </a></h3>
        </c:when>
        <c:otherwise>
            <h3>Create new event</h3>
        </c:otherwise>
    </c:choose>
    <form action="/editevent" method="post" id="addEditForm" role="form" class="form-inline">
        <div class="row">
            <c:choose>
                <c:when test="${not empty event}">
                    <input type="hidden" id="editEventId" name="editEventId" value="${event.id}"/>
                    <input type="hidden" id="addEditAction" name="action" value="edit"/>
                </c:when>
                <c:otherwise>
                    <input type="hidden" id="addEditAction" name="action" value="add"/>
                </c:otherwise>
            </c:choose>
            <div class="form-group">
                <label for="name">Name</label>
                <input type="text" placeholder="Event name" class="form-control" id="name" name="name" value="${event.name}"/>
            </div>
            <div class="form-group">
                <label for="startDate">From</label>
                <input type="datetime-local" class="form_datetime form-control" id="startDate" name="startDate"
                       <c:if test="${not empty event.startDate}">
                           value="<fmt:formatDate value="${event.startDate}" pattern="yyyy-MM-dd"/>T<fmt:formatDate value="${event.startDate}" pattern="HH:mm"/>"
                       </c:if>
                />
            </div>
            <div class="form-group">
                <label for="endDate">From</label>
                <input type="datetime-local" class="form_datetime form-control" id="endDate" name="endDate"
                        <c:if test="${not empty event.startDate}">
                            value="<fmt:formatDate value="${event.endDate}" pattern="yyyy-MM-dd"/>T<fmt:formatDate value="${event.endDate}" pattern="HH:mm"/>"
                        </c:if>
                />
            </div>
            <div class="form-group">
                <label for="isPublic">Is public</label>
                <input type="checkbox" class="checkbox form-control" id="isPublic" name="isPublic"
                <c:if test="${event.isPublic}"> checked="checked"</c:if>
                />
            </div>

            <button type="submit" class="btn btn-info">
                <span class="glyphicon glyphicon-ok"></span> Send
            </button>
        </div>
    </form>
    <form action="/events" method="post" id="deleteForm" role="form">
        <input type="hidden" id="eventId" name="eventId">
        <input type="hidden" id="action" name="action">
    </form>

</div>

</body>
</html>
