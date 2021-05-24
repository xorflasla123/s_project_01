<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript">
	function realURL(input){
		var file = input.files[0]
		console.log(file)
		if(file!=''){
			var reader = new FileReader();
			reader.readAsDataURL(file)
			reader.onload = function(e){
				console.log(e.target.result)
				$("#preview").attr("src",e.target.result)
			}
		}
	}
</script>
</head>
<body>
	<c:import url="../default/header.jsp" />
	<div class="wrap">
		<div style="width: 400px; margin: 0 auto;">
			<form action="${contextPath }/board/writeSave" method="post" enctype="multipart/form-data">
				<b>작성자</b><br>
				<input type="text" name="id" size="50" value="${loginUser }" readonly="readonly">
				<hr>
				<b>제목</b><br>
				<input type="text" size="50" name="title">
				<b>내용</b><br>
				<textarea rows="10" cols="50" name="content"></textarea>
				<hr>
				<b>이미지파일 첨부</b><br>
				<input type="file" name="image_file_name" onchange="realURL(this)"> 
				<img alt="선택 이미지 없음" src="#" id="preview" width="100" height="100">
				<hr>
				<input type="submit" value="글쓰기">
				<input type="button" value="목록이동" onclick="location.href='${contextPath}/boardAllList'">
			</form>
		</div>
	</div>
	<c:import url="../default/footer.jsp" />
</body>
</html>














