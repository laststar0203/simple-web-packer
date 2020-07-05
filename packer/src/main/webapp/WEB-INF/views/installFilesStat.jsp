<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content_Type" charset="UTF-8">
    <title>installFileStat</title>
    <script src="/js/lib/jquery-3.5.1.min.js" type="text/javascript"></script>
</head>
<body>
<%--<script>--%>
    <%----%>
    <%--$(function () {--%>
        <%--var installButton;--%>
        <%--<c:forEach var="installFile" items="${installFiles}">--%>
            <%--console.log(<c:out value="${installFile.number}"/>);--%>
            <%--installButton = $("#install_button<c:out value="${installFile.number}"/>");--%>
            <%--installButton.click(function () {--%>
                <%--requestInstallAJAX(<c:out value="${installFile.number}"/>);--%>
            <%--})--%>
        <%--</c:forEach>--%>
    <%--});--%>
    <%----%>
    <%--function requestInstallAJAX(number){--%>
        <%--$.ajax({--%>
            <%--url: "/install/"+number,--%>
            <%--type: "get",--%>
            <%--dataType: "json",--%>
            <%--succees:  function (data) {--%>
                <%--alert(data.succees ? '다운로드 성공!' : data.message);--%>
            <%--},--%>
            <%--error: function (request, status, error) {--%>
                <%--console.log(request);--%>
                <%--console.log(status);--%>
                <%--console.log(error);--%>
                <%--alert('서버 에러');--%>
            <%--}--%>
        <%--});--%>
    <%--}--%>
<%--</script>--%>


<p>
    번호 순서대로 설치해주시길 바랍니다.
</p>

<table id = "installFileList">
    <thead>
        <tr>
            <th>
                번호
            </th>
            <th>
                패키지 이름
            </th>
            <th>
                패키지 버전
            </th>
            <th>
                설치
            </th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="installFile" items="${installFiles}">
            <tr>
                <th>
                    <c:out value="${installFile.number}"/>
                </th>
                <th>
                    <c:out value="${installFile.name}"/>
                </th>
                <th>
                    <c:out value="${installFile.version}"/>
                </th>
                <th>
                    <c:choose>
                        <c:when test="${installFile.installed eq 1}">
                            <span>installed</span>
                        </c:when>
                        <c:otherwise>
                            <a href="/install/file/<c:out value="${installFile.number}"/>"><input type="button" value="설치하러가기"/></a>
                        </c:otherwise>
                    </c:choose>
                </th>
            </tr>
        </c:forEach>
    </tbody>
</table>

</body>
</html>