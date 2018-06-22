<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>ERROR</title>
<meta name="viewpoint" content="width=device-width,initial-scale=1">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/404.css">
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/easyui-js/jquery.min.js"></script>
</head>
<body>
		<div class="code">
			<p>${message}错误</p>
		</div>
		<div class="road">
			<div class="shadow">
				<div class="shelt">
					<div class="head">
						<div class="eyes">
							<div class="lefteye">
								<div class="eyeball"></div>
								<div class="eyebrow"></div>
							</div>
							<div class="righteye">
								<div class="eyeball"></div>
								<div class="eyebrow"></div>
							</div>
						</div>
					</div>
				</div>
				<div class="hat"></div>
				<div class="bubble">
					<a href="${pageContext.request.contextPath}/index.jsp">返回首页?</a>
				</div>
			</div>
			<p></p>
		</div>
	</body>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/404.js"></script>
</html>