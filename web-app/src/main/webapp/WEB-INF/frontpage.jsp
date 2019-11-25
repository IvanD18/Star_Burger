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

    <h1>Burger Shop</h1>
    <p><a href="<%= request.getContextPath() %>/basket" class="btn btn-secondary">Go to basket</a></p>
    <p><a href="<%= request.getContextPath() %>/enter" class="btn btn-secondary">I'm admin</a></p>

    <div class="row">
        <% for (ProductModel item : (List<ProductModel>) request.getAttribute(Constants.ITEMS)) { if(item.getQuantity()>0){%>
        <div class="col-3">
            <div class="card mt-3">
                <div style="height: auto"><img src="<%= item.getImageUrl() %>" alt="<%= item.getImageUrl() %>"
                                               class="img-rounded"></div>
                <div class="card-body">
                    <h5 class="card-title"><%= item.getName() %>
                    </h5>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item">Price: <%= item.getPrice() %>
                        </li>
                        <li class="list-group-item"><a
                                href="<%= request.getContextPath() %>/details?id=<%= item.getId()%>">Details</a>
                        </li>
                    </ul>
                    <form action="<%= request.getContextPath() %>" method="post">
                        <input name="id" type="hidden" value="<%= item.getId() %>">
                        <div class="form group">
                            <label for="quantity">Product Quantity</label>
                            <input type="number" min="0" id="quantity" name="quantity" value="1">
                        </div>
                        <button class="btn btn-primary">Add to card</button>

                    </form>
                </div>
            </div>
        </div>
        <%} } %>
    </div>
</div>

</body>
</html>
