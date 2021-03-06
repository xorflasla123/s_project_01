<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <c:set var="contextPath" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:import url="../default/header.jsp" />
	<div class="wrap">
		<h1 style="text-align: center;">회 원 정 보</h1>
		<table class="table">
			<tr>
				<th>아이디</th><th>비밀번호</th><th>주소</th>
			</tr>
			<c:forEach var="dto" items="${memberList}">
			<tr>
				<td>
					<a href="${contextPath }/member/info?id=${dto.id }"> ${dto.id }</a>
				</td> 
				<td>${dto.pw }</td> <td>${dto.addr }</td>
			</tr>
			</c:forEach>
		</table>
	</div>
	<c:import url="../default/footer.jsp" />
</body>
</html>




