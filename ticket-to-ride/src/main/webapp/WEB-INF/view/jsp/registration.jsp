<%--
  Created by IntelliJ IDEA.
  User: trofim
  Date: 03.10.2020
  Time: 16:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="sbb train page">
    <meta name="author" content="Trofim Kremen">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css"/>">
    <title>Registration</title>
</head>
<body>
<header>
    <nav class="navbar navbar-light bg-light">
        <a class="navbar-brand" href="<c:url value="/"/>">
            <img src="<c:url value="/resources/images/SBB-logo.png"/>" width="40" height="40" class="d-inline-block align-top" alt="">
            SBB App
        </a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav">
                <a class="nav-item nav-link" href="<c:url value="/"/>">Home</a>
                <a class="nav-item nav-link" href="<c:url value="/trains"/>">Trains</a>
                <a class="nav-item nav-link active" href="<c:url value="/stations"/>">Stations<span class="sr-only">(current)</span></a>
            </div>
        </div>
    </nav>
</header>
<main>
    <div class="container container-fluid">
        <div class="row">
            <div class="col-md-6 col-md-offset-3">
                <c:if test="${param.error}">
                    <div class="alert alert-info">
                        You've successfully registered to Grid Student Practice Chat!
                    </div>
                </c:if>
                <h1>Registration</h1>
                <form:form method="post" modelAttribute="user" action="${pageContext.request.contextPath}/registration">
                    <spring:bind path="login">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <label for="login" class="control-label">Login
                                <form:input id="login" path="login" />
                                <br>
                                <form:errors path="login" cssClass="error"/>
                            </label>
                        </div>
                    </spring:bind>
                    <spring:bind path="email">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <label for="email" class="control-label">Email
                                <form:input id="email" path="email" />
                                <br>
                                <form:errors path="email" cssClass="error"/>
                            </label>
                        </div>
                    </spring:bind>
                    <spring:bind path="confirmEmail">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <label for="confEmail" class="control-label">Confirm Email
                                <form:input id="confEmail" path="confirmEmail" />
                                <br>
                                <form:errors path="confirmEmail" cssClass="error"/>
                            </label>
                        </div>
                    </spring:bind>
                    <spring:bind path="password">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <label for="password" class="control-label">Password
                                <form:input id="pasword" path="password" />
                                <br>
                                <form:errors path="password" cssClass="error"/>
                            </label>
                        </div>
                    </spring:bind>
                    <spring:bind path="confirmPassword">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <label for="confPassword" class="control-label">Confirm password
                                <form:input id="confPassword" path="confirmPassword" />
                                <br>
                                <form:errors path="confirmPassword" cssClass="error"/>
                            </label>
                        </div>
                    </spring:bind>
                    <input type="submit" class="form-control btn btn-info" value="Register!"/>
                </form:form>
            </div>
        </div>
    </div>
</main>
<footer class="fixed-bottom page-footer bg-light">
    <p class="text-center footer-text">&copy; 2020 Java School &middot; Trofim Kremen </p>
</footer>
<script src="<c:url value="/resources/js/jquery/jquery-3.5.1.min.js"/>"></script>
<script src="<c:url value="/resources/js/popper/popper.min.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap/bootstrap.min.js"/>"></script>
</body>
</html>
