<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Events</title>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.12.1/jquery.min.js"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
<link rel="stylesheet" href="/ElasticDemo/css/Style.css" type="text/css">
<link rel="stylesheet" type="text/css" media="screen" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/base/jquery-ui.css">
<link href="//netdna.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
<script src="/ElasticDemo/js/Util.js"></script>
<script src="/ElasticDemo/js/jquery.twbsPagination.js"></script>
</head>
<body>
	<div class="filterBox">
		<label>Period: </label>
		<select id="datePeriod" class="cellSpacing">
			<option value="week">Week</option>
			<option value="month">Month</option>
			<option value="quarter">Quarter</option>
		</select>
		<div style="padding-top: 10px">
			<label>Start date: </label>
			<input type="text" id="datepicker" class="cellSpacing">
			<input type="button" value ="Fetch" onclick = "getEvents()" />
		</div>
	</div>

	<div style="padding-left: 20px;">
		<div class="tableWrapper">
			<div class="scrollBox">
				<div id="templateTable" class="table">
				<div class="row header" style="display:none;">
			  		<div class="cell"><h5>Event Date</h5></div>
			  		<div class="cell"><h5>Event Type</h5></div>
			  		<div class="cell"><h5>Event Summary</h5></div>
			  		<div class="cell"><h5>Event Size</h5></div>
				</div>
		  		</div>
		  	</div>
		  	<div id="paginationBox">
				<ul id="pagination" class="pagination-sm paginationControl"></ul>
		  	</div>
		</div>
		<div class="detailsWrapper">
			<div id="detailsTable" style="display:none;">
				<div>
					<label>Event date: </label>
					<span id="txtDate" class="cellSpacing"></span>
				</div>
				<div>
					<label>Event type: </label>
					<span id="txtType" class="cellSpacing"></span>
				</div>
				<div>
					<label>Event size: </label>
					<span id="txtSize" class="cellSpacing"></span>
				</div>
				<div>
					<label>Event summary: </label>
					<span id="txtSummary" class="cellSpacing"></span>
				</div>
				<label>Event details </label>
				<textarea id="txtDetails" class="detailsTxt"></textarea>
			</div>
		</div>
</body>
</html>