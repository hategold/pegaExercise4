<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert/Edit the shoes records</title>

</head>
<body>
	<div class="container">
		<div class="row" id="up-padding">
			<div class="col-xs-12 col-md-12 col-lg-12 vcenter">
				<div style="height: 4em;"></div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-offset-2 col-md-8 jumbotron">
				<h2>Insert/Edit the shoes to ${brand.getBrandName()}</h2>
				<form action="ShoesTableController.do" method="post">
					<fieldset>
						<div class="form-group">
							<label>ShoesId</label><input type="text" name="shoesId"
								value="${shoes.getShoesId()}" class="form-control" readonly>
						</div>
						<div class="form-group">
							<label>ShoesName</label><input type="text" name="shoesName"
								value="${shoes.getShoesName()}" class="form-control" required>
						</div>
						<div class="form-group">
							<label>Series</label><input type="text" name="series"
								value="${shoes.getSeries()}" class="form-control">
						</div>
						<div class="form-group">
							<label>Category</label><input type="text" name="category"
								value="${shoes.getCategory()}" class="form-control">
						</div>
						<div class="form-group">
							<label>Price</label><input type="number" name="price"
								value="${shoes.getPrice()}" class="form-control">
						</div>
						<input type="hidden" name="brandId" value="${brand.getBrandId()}">
						<button class="btn btn-success" type="submit">Submit</button>

					</fieldset>
				</form>
			</div>
		</div>
	</div>

</body>
</html>