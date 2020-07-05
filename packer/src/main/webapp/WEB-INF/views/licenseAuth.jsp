<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content_Type" charset="UTF-8">
    <title>licenseAuth</title>
</head>
<body>
<form action="/licenseKey/auth" id="licenseKeyAuthForm">
    LICENSE_KEY : <input type="text" id="input_licenseKey" name="key"/>
    <button>인증</button>
</form>
</body>
</html>