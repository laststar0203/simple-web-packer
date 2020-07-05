<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content_Type" charset="UTF-8">
    <title>licenseAuth</title>
</head>
<body>
    <script>
        alert(${errorMessage});
        location.href = document.referrer;
    </script>
</body>
</html>