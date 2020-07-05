<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content_Type" charset="UTF-8">
    <title>engineerAuth</title>
</head>
<body>
    <form action="/engineer/login/auth" id="engineerLoginForm">
        ID : <input type="text" id="input_id" name="id"/>
        PWD : <input type="text" id="input_password" name="password"/>
        <button>로그인</button>
    </form>
</body>
</html>