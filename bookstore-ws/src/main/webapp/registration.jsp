<%@ page import="com.epam.rudenkov.model.UserRole" %>
<!DOCTYPE html>
<html>
<head>
    <style>
        #warning
        {
            visibility: hidden;
        }
    </style>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>

    <!-- Latest compiled JavaScript -->
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script>
        function validateForm() {
            var name = document.forms["registrationForm"]["username"].value;
            if (name == null || name == "") {
                document.getElementById("warning").innerHTML = "<strong>Error!</strong> Name must be filled out!";
                document.getElementById("warning").style.visibility= "visible";
                return false;
            }
            var password = document.forms["registrationForm"]["password"].value;
            var repeatPassword = document.forms["registrationForm"]["passwordAgain"].value;
            if (password.length < 8) {
                document.getElementById("warning").innerHTML = "<strong>Error!</strong> Password should contain 8 symbols or more";
                document.getElementById("warning").style.visibility= "visible";
                return false;
            }
            if (password != repeatPassword) {
                document.getElementById("warning").innerHTML = "<strong>Error!</strong> Filled out passwords should match";
                document.getElementById("warning").style.visibility= "visible";
                return false;
            }
        }
    </script>
</head>
<body>
<h3>SignUp Form</h3>
<div id="warning" class="alert alert-danger">
</div>
<form name="registrationForm" onsubmit="return validateForm()" action="registration" method="post">
    <label>Email:</label><input type="text" name="username"/><br/><br/>
    <label>Password:</label><input type="password" name="password"/><br/><br/>
    <label>Repeat Password:</label><input type="password" name="passwordAgain"/><br/><br/>
    <select name="user_role">
        <%
            UserRole.Role[] roles = UserRole.Role.values();
            for (int i = 0; i < roles.length; i++) {
        %>
        <option value="<%=roles[i]%>"><%=roles[i]%>
        </option>
        <%
            }
        %>
    </select>
    <br/><br/>
    <input type="submit" value="signup"/>
</form>

<%
    if (request.getAttribute("error") != null) {
%>
<h3 style="background-color:yellow"><%=request.getAttribute("error")%>
</h3>
<%
    }
%>
</body>
</html>