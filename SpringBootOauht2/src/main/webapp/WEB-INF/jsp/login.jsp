<%@ page language="java" contentType="text/html; charset=EUC-KR"    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>로그인</title>
</head>
<body>

<h2>로그인</h2>
	<form name = "form1" method="post" action="/login">
		<table>
			<tr>
				<td>id</td>
				<td><input type ="text" name="id" value="" />
			</tr>
			<tr>
				<td>pass</td>
				<td><input type ="password" name="pass" value="" />
			</tr>
		</table>
		<table>
			<tr>
				<td><input type="submit" value="로그인"></td>
			</tr>
		</table>
	</form>

</body>
</html>