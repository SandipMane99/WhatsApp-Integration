<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Send Message</title>

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css"
	integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N"
	crossorigin="anonymous">

<script>
	function clearcontent(elementID) {
		document.getElementById(elementID).innerHTML = "";
	}
</script>

</head>

<body>

	<h1 class="text-center">WhatsApp Message</h1>

	<form class="text-center mt-5 bg-light shadow"
		action="/excelReadApi/excelRead" method="POST"
		enctype="multipart/form-data"
		style="margin-left: 20%; margin-right: 20%;">

		<!-- <h5 class="pt-3">Download Excel File.</h5> -->

		<!-- <a href="/excelReadApi/download" class="btn btn-primary"> Download
			File <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"
				fill="currentColor" class="bi bi-download" viewBox="0 0 16 16">
  <path
					d="M.5 9.9a.5.5 0 0 1 .5.5v2.5a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1v-2.5a.5.5 0 0 1 1 0v2.5a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2v-2.5a.5.5 0 0 1 .5-.5z" />
  <path
					d="M7.646 11.854a.5.5 0 0 0 .708 0l3-3a.5.5 0 0 0-.708-.708L8.5 10.293V1.5a.5.5 0 0 0-1 0v8.793L5.354 8.146a.5.5 0 1 0-.708.708l3 3z" />
</svg>
		</a> -->
		
		
		<%
		List<String> templates = (List) request.getAttribute("listOfTemplates");
		%>
		
		<div class="form-group text-center mt-5"
			style="margin-left: 35%; margin-right: 35%;">
			<label>Select Template Id:</label> <select
				class="form-control text-center bg-light" name="template_id" id="template_id">

				<%
				for (String item : templates) {
				%>
				<option><%=item%></option>
				<%
				}
				%>

			</select>
		</div>
		
		<h5 class="pt-3">Download Excel File.</h5>
		
		<a href="#" class="btn btn-primary" id="downloadLink"> Download
			File <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"
				fill="currentColor" class="bi bi-download" viewBox="0 0 16 16">
  <path
					d="M.5 9.9a.5.5 0 0 1 .5.5v2.5a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1v-2.5a.5.5 0 0 1 1 0v2.5a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2v-2.5a.5.5 0 0 1 .5-.5z" />
  <path
					d="M7.646 11.854a.5.5 0 0 0 .708 0l3-3a.5.5 0 0 0-.708-.708L8.5 10.293V1.5a.5.5 0 0 0-1 0v8.793L5.354 8.146a.5.5 0 1 0-.708.708l3 3z" />
</svg>
		</a>
		
		<script>
					var downloadLink = document.getElementById("downloadLink");
			
					downloadLink.addEventListener("click", function(event) {
					    event.preventDefault();
					    var template_id = document.getElementById("template_id").value;
					     
					    var url = "/excelReadApi/download?template_id=" + template_id;
					    downloadLink.setAttribute("href", url);
					      window.location.href = url;
					  });
		</script>

		<h5 class="mt-5">Upload Excel File.</h5>
		<input type="file" name="file" required style="padding-left: 6rem;">
		<br>


		<%-- <%
		List<String> templates = (List) request.getAttribute("listOfTemplates");
		%> --%>

		<%-- <div class="form-group text-center mt-5"
			style="margin-left: 35%; margin-right: 35%;">
			<label>Select Template Id:</label> <select
				class="form-control text-center bg-light" name="template_id" id="template_id">

				<%
				for (String item : templates) {
				%>
				<option><%=item%></option>
				<%
				}
				%>

			</select>
		</div> --%>

		<!-- <input class="mt-3 text-center" type="text" name="template_id"
			placeholder="Enter Template Id" required> -->
		<br> <input class="mt-1 btn btn-primary mb-5" type="submit"
			value="Send Message" onclick="clearcontent('clear')">


		<div class="text-center pb-4">
			<span class="pr-5 text-success font-weight-bold h5">
				${sentCount} </span> <span class="pl-5 text-danger font-weight-bold h5">
				${unSentCount} </span>
		</div>

	</form>





	<script
		src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js"
		integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct"
		crossorigin="anonymous"></script>
		

</body>
</html>