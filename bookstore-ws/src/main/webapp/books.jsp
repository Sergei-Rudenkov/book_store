<%@ page language="java"
         contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Book Store</title>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js"></script>

</head>
<body>
<h1>Book Store</h1>

<script type="text/javascript">
    $(document).ready(function() {
        $('#call').click(function ()
        {
            $.ajax({
                type: "post",
                url: "bought_ajax",
                data: $('#buyBookForm').serialize(),
                success: function(data) {
                    for(var i = 0; i < document.getElementsByName("bought_column").length; i++){
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

<form id="buyBookForm" action="books" method="post">
<table id="table" width="70%" border="1">
    <tr>
        <th>Book Name</th>
        <th>Author</th>
        <th>Genre</th>
        <th>Price</th>
        <th>Sold</th>
        <th>Bought By</th>
    </tr>
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
            <td name = "bought_column"><c:choose>
                <c:when test="${book.getUsers().size() >= 1}">
                    Bought ${book.getUsers().size()} times.
                    <br />
                </c:when>
                <c:otherwise>
                    Have never been bought.
                    <br />
                </c:otherwise>
            </c:choose></td>
        </tr>
    </c:forEach>
</table>
    <br/><br/>
    <input type="submit" name="submit" value="Purchase">
</form>
<br/><br/>
<input type="button" value="Purchase using AJAX" name="Purchase using AJAX" id="call"/>

</body>
</html>