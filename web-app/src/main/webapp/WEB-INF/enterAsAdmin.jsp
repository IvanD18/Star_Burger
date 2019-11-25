<%@ page import="ru.rosbank.javaschool.web.servlet.FrontServlet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Enter</title>

    <%@include file="bootstrap-css.jsp" %>
</head>
<body>
<div>
<form method="post" action="<%= request.getContextPath() %>" class="form-inline"  >

        <label  for="exampleInputEmail3">Login</label>
        <input type="text" name = "login" class="form-control" id="exampleInputEmail3" >


        <label  for="exampleInputPassword3">Password</label>
        <input type="text" name = "password" class="form-control" id="exampleInputPassword3" >
   <button class="btn btn-secondary">Sign In</button>
    <a href="<%= request.getContextPath() %>/admin" class="btn btn-primary">Next</a>
</form>
</div>
</body>
</html>
