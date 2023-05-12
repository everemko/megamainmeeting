<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<html>
<head>
    <title>Mega main meeting admin panel</title>
</head>
<body>
<p>Количество раскрытий до блокировки пользователя</p>
<input type="number" id="inputOpensBeforeUserBlocked" value=${opensbeforeuserblocked}>
<button type="button" onclick="send()">Применить</button>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script>
function send() {
  let value = document.getElementById('inputOpensBeforeUserBlocked').value
  let success = function(data){
       alert("Изменения приняты")
  }
  let error = function(){
    alert("Изменения не приняты, свяжитесь с администратором")
  }
        let requestData = {
            "dataType":    "json",
            "type"    :    "PUT",
            "url"     :    "/admin/opensbeforeuserblocked?value=" + value,
            "success" :     success,
            "error"   :     error
        };
    $.ajax(requestData)
}
</script>
</body>
</html>