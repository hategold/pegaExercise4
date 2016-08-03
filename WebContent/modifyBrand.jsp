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
<title>Insert/Edit the shoes brands record</title>

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
				<h2>Insert/Edit the shoes brands record</h2>
				<form action="BrandTableController.do" method="post">
					<fieldset>
						<div class="form-group">
							<label>BrandId</label><input type="text" name="brandId"
								value="${brand.getBrandId()}" class="form-control" readonly>
						</div>
						<div class="form-group">
							<label>BrandName</label><input type="text" name="brandName"
								value="${brand.getBrandName()}" class="form-control" required>
						</div>
						<div class="form-group">
							<label>Website</label><input type="text" name="website"
								value="${brand.getWebsite()}" class="form-control">
						</div>
						<div class="form-group">
							<label>Country</label><select class="form-control" name="country">
								<c:forEach items="${countryCodeMap}" var="countryCode">
									<option value=${countryCode.key
										}
										${countryCode.key == brand.getCountry()?"selected":""}>${countryCode.value}</option>
								</c:forEach>
							</select>
						</div>
						<button class="btn btn-success" type="submit">Submit</button>

					</fieldset>
				</form>
			</div>
		</div>
	</div>

</body>
</html>