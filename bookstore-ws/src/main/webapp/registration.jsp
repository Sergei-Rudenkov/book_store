<%@ page import="com.epam.rudenkov.model.UserRole" %>
<!DOCTYPE html>
<html>
<head>
    <script>
        function validateForm() {
            var name = document.forms["registrationForm"]["username"].value;
            if (name == null || name == "") {
                alert("Name must be filled out");
                return false;
            }
            var password = document.forms["registrationForm"]["password"].value;
            var repeatPassword = document.forms["registrationForm"]["passwordAgain"].value;
            if (password.length < 8) {
                alert("Password should contain 8 symbols or more");
                return false;
            }
            if (password != repeatPassword) {
                alert("Filled out passwords should match");
                return false;
            }
        }
    </script>
</head>
<body>
<h3>SignUp Form</h3>
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