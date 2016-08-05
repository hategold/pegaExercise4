<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- 新增判斷，避免直接存取listbrands -->
<html>
<head>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jsLib/bootbox.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Brands Records</title>

</head>
<body>

	<div class="container">
		<div class="row" id="up-padding">
			<div class="col-xs-12 col-md-12 col-lg-12 vcenter">
				<div style="height: 4em;"></div>
			</div>
		</div>

		<div class="row">
			<div class="col-md-8 col-md-offset-2">
				<table class="table table-bordered">
					<caption>ShoesBrands</caption>
					<thead>
						<tr>
							<th>BrandId</th>
							<th>BrandName</th>
							<th>Website</th>
							<th>Country</th>
							<th colspan="3">Action</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${brandList}" var="brand">
							<tr>
								<td><c:out value="${brand.getBrandId()}" /></td>
								<td><c:out value="${brand.getBrandName()}" /></td>
								<td><c:out value="${brand.getWebsite()}" /></td>
								<td><c:out value="${brand.getCountry()}" /></td>
								<td><a
									href="BrandTableController.do?action=edit&brandId=<c:out value="${brand.getBrandId() }"/>"><button
											type="button" class="btn btn-primary">Update</button></a></td>

								<td><a
									href="BrandTableController.do?action=delete&brandId=<c:out value="${brand.getBrandId() }"/>"
									class=" confirm"><button type="button"
											class="btn btn-danger">Delete</button></a></td>
								<td><a
									href="ShoesTableController.do?action=list&brandId=<c:out value="${brand.getBrandId() }"/>"><button
											type="button" class="btn btn-primary">Shoes List</button></a></td>

							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<div class="row">
			<p class="col-md-offset-2">
				<a href="BrandTableController.do?action=insert"><button
						type="button" class="btn btn-success">Add Shoes Brands</button></a>
			</p>
		</div>
	</div>
	<script type="text/javascript" src="jsLib/ConfirmDelete.js"
		charset="utf-8">
		
	</script>

</body>
</html>
