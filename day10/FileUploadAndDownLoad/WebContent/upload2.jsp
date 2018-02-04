<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/FileUploadServlet" enctype="multipart/form-data" method="POST">
上传用户<input type="text" name = "username">
<br>
上传文件1<input type="file" name ="file"><br>

上传文件2<input type="file" name = "file"><br>

<button type="submit">提交</button>
</form>
</body>
</html>