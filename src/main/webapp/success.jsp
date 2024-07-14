<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Success</title>
</head>
<body>
<h3> Login Successful</h3>
<br>
Hello <%= request.getAttribute("username")%> !

</body>
</html>