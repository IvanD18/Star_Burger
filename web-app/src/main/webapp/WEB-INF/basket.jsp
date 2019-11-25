<%@ page import="java.util.List" %>
<%@ page import="ru.rosbank.javaschool.web.constant.Constants" %>
<%@ page import="ru.rosbank.javaschool.web.model.ProductModel" %>
<%@ page import="ru.rosbank.javaschool.web.model.OrderPositionModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!doctype html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Star Burger</title>
    <style>

    </style>
    <%@include file="bootstrap-css.jsp" %>
</head>
<body>

<div class="container">

    <h1>Basket</h1>
<%--        <% List<OrderPositionModel> positions = (List<OrderPositionModel>) request.getAttribute(Constants.ITEMS); %>--%>
<%--        <p><%= positions.size() %>--%>
<%--        </p>--%>
<%--        <% for (OrderPositionModel model : positions) { %>--%>
<%--        <p><%= model %>--%>
<%--        </p>--%>
<%--        <% } %>--%>

    <div class="row">
        <% for (OrderPositionModel item : (List<OrderPositionModel>) request.getAttribute(Constants.ITEMS)) { %>
        <div class="col-3"  >
            <div class="card mt-3">
                <div class="card-body" >
                    <h5 class="card-title"><%= item.getProductName() %>
                    </h5>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item">Price: <%= item.getProductPrice() %>
                        </li>
                        <li class="list-group-item">Quantity: <%= item.getProductQuantity() %>
                        </li>
                        <li class="list-group-item"><a href="<%= request.getContextPath() %>/">Back to menu</a>
                        </li>
                        <a class="btn btn-secondary"><a href="<%= request.getContextPath() %>/basket/remove?id=<%= item.getId()%>">Delete</a>
                        </a>
                    </ul>

                </div>
            </div>
        </div>
        <% } %>
    </div>
</div>

</body>
</html>
