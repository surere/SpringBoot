<%@ PAGE LANGUAGE="JAVA" CONTENTTYPE="TEXT/HTML; CHARSET=UTF-8"
    PAGEENCODING="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	out.println("JdbcTemplate : Hello World");
%>
<br>

<!-- 
	컨트롤러에서 Model에 저장한 List의 원소갯수만큼 반복하여 
	회원목록을 출력한다.
 -->
<c:forEach var="dto" items="${users }">
	${dto.id } / ${dto.name}<br>
	</c:forEach>
	
</body>
</html>