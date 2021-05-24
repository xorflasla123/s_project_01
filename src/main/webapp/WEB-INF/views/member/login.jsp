<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
	<c:import url="../default/header.jsp" />
		<div class="wrap">
			<div align="right">
				<form action="${contextPath }/member/user_check" method="post">
					<table>
						<tr>
							<td><input type="text" name="id" placeholder="input id"></td>
						</tr>
						<tr>
							<td><input type="password" name="pw" placeholder="input password"></td>
						</tr>
						<tr>
							<td colspan="2">
								<input type="submit" value="로그인">&nbsp;
								<a href="${contextPath }/member/register_form">회원가입</a>
								<br><input type="checkbox" name="autoLogin">로그인유지
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	<c:import url="../default/footer.jsp" />
</body>
</html>






