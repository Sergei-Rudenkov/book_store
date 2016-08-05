<%@ page language="java"
         contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Book Store</title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>

    <!-- Latest compiled JavaScript -->
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</head>
<body>
<script type="text/javascript">
    $(document).ready(function () {
        $('#call').click(function () {
            $.ajax({
                type: "post",
                url: "bought_ajax",
                data: $('#buyBookForm').serialize(),
                success: function (data) {
                    for (var i = 0; i < document.getElementsByName("bought_column").length; i++) {
                        document.getElementsByName("bought_column")[i].innerHTML = "Bought by: " + data["books_count"][i] + " user(s)";
                    }
                },
                error: function (jqXHR, exception) {
                    var msg = '';
                    if (jqXHR.status === 0) {
                        msg = 'Not connect.\n Verify Network.';
                    } else if (jqXHR.status == 404) {
                        msg = 'Requested page not found. [404]';
                    } else if (jqXHR.status == 500) {
                        msg = 'Internal Server Error [500].';
                    } else if (exception === 'parsererror') {
                        msg = 'Requested JSON parse failed.';
                    } else if (exception === 'timeout') {
                        msg = 'Time out error.';
                    } else if (exception === 'abort') {
                        msg = 'Ajax request aborted.';
                    } else {
                        msg = 'Uncaught Error.\n' + jqXHR.responseText;
                    }
                    alert(msg);
                }
            });
        });
    });
</script>


<div class="container">
    <h1>Book Store</h1>
    <p>Choice any book you like:</p>

    <form id="buyBookForm" action="books" method="post">
    <table id="table" class="table">
        <thead>
        <tr>
            <th>Book Name</th>
            <th>Author</th>
            <th>Genre</th>
            <th>Price</th>
            <th>Sold</th>
            <th>Bought By</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${books}" var="book">
        <tr>
            <td>
                <input type="checkbox" name="book${book.getName()}"
                       value="${book.getBook_id()}"> <label>${book.getName()}</label>
            </td>
            <td>${book.getAuthor().getName()}</td>
            <td>${book.getGenre()}</td>
            <td>${book.getPrice()}</td>
            <td>${book.isBought()}</td>
            <td name="bought_column"><c:choose>
                <c:when test="${book.getUsers().size() >= 1}">
                    Bought ${book.getUsers().size()} times.
                    <br/>
                </c:when>
                <c:otherwise>
                    Have never been bought.
                    <br/>
                </c:otherwise>
            </c:choose></td>
        </tr>
        <tbody>
        </c:forEach>
    </table>
    <br/><br/>
    <input type="submit" name="submit" value="Purchase">
    </form>
</div>
<br/><br/>
<input type="button" value="Purchase using AJAX" name="Purchase using AJAX" id="call"/>

</body>
</html>