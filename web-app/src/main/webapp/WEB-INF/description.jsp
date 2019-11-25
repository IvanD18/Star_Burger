<%@ page import="ru.rosbank.javaschool.web.model.ProductModel" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.rosbank.javaschool.web.constant.Constants" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<%@include file="bootstrap-css.jsp" %>
</head>
<body>

<div class="container">

    <h1>Details</h1>


    <div class="row">
        <% ProductModel item=(ProductModel) request.getAttribute(Constants.ITEM);%>
        <div class="col-3"  >
            <div class="card mt-3">

                <div class="card-body" >
                    <h5 class="card-title"><%= item.getName() %>
                    </h5>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item">Description: <%= item.getDescription() %>
                        </li>
                        <li class="list-group-item"><a href="<%= request.getContextPath() %>/">Go back</a>
                        </li>
                    </ul>

                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>