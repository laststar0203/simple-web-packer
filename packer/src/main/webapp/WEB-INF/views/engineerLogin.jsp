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
    <br>
    인터넷이 연결되어 있지 않다면 <a href="/licenseKey">해당 링크</a>로 들어가 오프라인 인증을 진행해 주시길 바랍니다.
</body>
</html>