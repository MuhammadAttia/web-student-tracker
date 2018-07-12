<%@ page language="java" contentType="text/html; charset=windows-1256"
	pageEncoding="windows-1256"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.*,com.muhammad.crud.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1256">
<title>List Students</title>

<style type="text/css">
#customers {
	font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
	border-collapse: collapse;
	width: 75%;
	margin: 20px;
}

#customers td, #customers th {
	border: 1px solid #ddd;
	padding: 8px;
}

#customers tr:nth-child(even) {
	background-color: #f2f2f2;
}

#customers tr:hover {
	background-color: #ddd;
}

#customers th {
	padding-top: 12px;
	padding-bottom: 12px;
	text-align: left;
	background-color: #4CAF50;
	color: white;
}
</style>
</head>
<body>

	<input type="button" value="Add Student"
		onclick="window.location.href='add-student-form.jsp'; return false;"
		class="add-student-button" />

	<%
		List<Student> theStudents = (List<Student>) request.getAttribute("listStudents");
	%>

	<table id="customers">
		<tr>
			<th>First Name</th>
			<th>Last Name</th>
			<th>Email</th>
			<th>Actions</th>
		</tr>

		<c:forEach var="data" items="${listStudents}">

			<c:url var="tempLink" value="StudentControllerServlet">

				<!--  setup load link  -->
				<c:param name="command" value="LOAD" />
				<c:param name="studentId" value="${data.id}" />
			</c:url>

			<!-- setup delete link  -->
			<c:url var="deleteLink" value="StudentControllerServlet">
				<c:param name="command" value="DELETE" />
				<c:param name="studentId" value="${data.id}" />
			</c:url>


			<tr>
				<td>${data.firstName }</td>
				<td>${data.lastName }</td>
				<td>${data.email }</td>
				<td><a href="${tempLink }">Update</a> |<a href="${deleteLink}"
					onclick="if (!(confirm('Are you sure you want to delete this student?'))) return false">
						Delete</a></td>
			</tr>
		</c:forEach>

	</table>
</body>
</html>