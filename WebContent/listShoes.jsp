<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jsLib/bootbox.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Shoes Records</title>
</head>
<body>

	<div class="container">
		<div class="row" id="up-padding">
			<div class="col-xs-12 col-md-12 col-lg-12 vcenter">
				<div style="height: 4em;"></div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-3 col-md-offset-2">
				<h3>${brand.getBrandId()}:${brand.getBrandName()}</h3>
			</div>
		</div>
		<div class="row">
			<div class="col-md-8 col-md-offset-2">
				<table class="table table-bordered">
					<thead>
						<tr>
							<th>ShoesId</th>
							<th>ShoesName</th>
							<th>Series</th>
							<th>Category</th>
							<th>Price</th>
							<th colspan="2">Action</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${shoesList}" var="shoes">
							<tr>
								<td><c:out value="${shoes.getShoesId()}" /></td>
								<td><c:out value="${shoes.getShoesName()}" /></td>
								<td><c:out value="${shoes.getSeries()}" /></td>
								<td><c:out value="${shoes.getCategory()}" /></td>
								<td><c:out value="${shoes.getPrice()}" /></td>
								<td><a
									href="ShoesTableController.do?action=edit&shoesId=<c:out value="${shoes.getShoesId() }"/>&brandId=<c:out value="${brand.getBrandId() }"/>"><button
											type="button" class="btn btn-primary">Update</button></a></td>

								<td><a
									href="ShoesTableController.do?action=delete&shoesId=<c:out value="${shoes.getShoesId() }"/>&brandId=<c:out value="${brand.getBrandId() }"/>"
									class="confirm"><button type="button"
											class="btn btn-danger">Delete</button></a></td>

							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<div class="row">
			<p class="col-md-offset-2 col-md-2">
				<a
					href="ShoesTableController.do?action=insert&brandId=<c:out value="${brand.getBrandId() }"/>"><button
						type="button" class="btn btn-success">Add Shoes to Brand</button></a>
			</p>
			<p class="col-md-2">
				<a href="BrandTableController.do?action=list"><button
						type="button" class="btn btn-primary">Back to Brands List</button></a>
			</p>
		</div>
	</div>
	<script type="text/javascript" src="jsLib/ConfirmDelete.js"
		charset="utf-8">
		
	</script>
</body>
</html>