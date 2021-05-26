<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
		<table border="1">
			<tr>
				<th>글 번호</th>
				<th>${personalData.writeNo }</th>
				<th>작성자</th>
				<th>${personalData.id }</th>
			</tr>
			<tr>
				<th>제 목</th>
				<th>${personalData.title }</th>
				<th>작성일</th>
				<th>${personalData.saveDate }</th>
			</tr>
			<tr>
				<th>내 용</th>
				<th>${personalData.content }</th>
				<th colspan="2">
					<c:if test="${personalData.imageFileName == 'nan' }">
						<b>이미지가 없습니다</b>
					</c:if>
					<c:if test="${personalData.imageFileName != 'nan' }">
						<img width="100px" height="100px"
							src="${contextPath }/board/download?imageFileName=${personalData.imageFileName}">
					</c:if>
				</th>
			</tr>
			<tr>
				<td colspan="4">
					<c:if test="${loginUser == personalData.id }">
						<input type="button" onclick=
						"location.href='${contextPath }/board/modify_form?writeNo=${personalData.writeNo }'" value="수정하기">
						<input type="button" onclick=
						"location.href='${contextPath }/board/delete?writeNo=${personalData.writeNo }&imageFileName=${personalData.imageFileName }'" value="삭제하기">
					</c:if> 
					<c:if test="${loginUser != null }">
						<input type="button" onclick="" value="답글달기">
					</c:if>
					<input type="button" onclick="location.href='${contextPath }/board/boardAllList'" value="돌아가기">
				</td>
			</tr>
		</table>
	</div>
	<c:import url="../default/footer.jsp" />
</body>
</html>
















