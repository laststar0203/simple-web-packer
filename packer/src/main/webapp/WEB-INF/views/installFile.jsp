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

<script>
    $(function () {
        var install_button = $('#install_button');
        var back_button = $('#back_button');

        back_button.attr('disabled', true);
        install_button.click(function () {
            $.ajax({
                url: "/install/"+${installFile.number},
                type: "get",
                dataType: "json",
                succees:  function (data) {
                    alert(data.succees ? '다운로드 성공!' : data.message);
                    back_button.attr('disabled', false);
                },
                error: function (request, status, error) {
                    console.log(request);
                    console.log(status);
                    console.log(error);
                    alert('서버 에러');
                    back_button.attr('disabled', false);
                }
            })
        });

        back_button.click(function () {
            location.href = document.referrer;
        })
    })
</script>


<table>
    <tr>
        <th colspan="2">
            Download the ${installFile.fileName}/${installFile.fileVersion}
        </th>
    </tr>
    <c:forEach items="${installFile.installProperties}" var="property">
        <tr>
            <th>
                <c:out value="${property}"/> :
            </th>
            <th>
                <input type="text" id="input_<c:out value="${property}"/>"/>
            </th>
        </tr>
    </c:forEach>
</table>

<input id="install_button" type="button" value="설치"/>
<input id="back_button" type="button" value="완료">



</body>
</html>