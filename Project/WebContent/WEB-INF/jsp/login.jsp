<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<!-- Bootstrapのcssの読み込み -->
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
<!-- originパッケージのcommon.cssの読み込み -->
<link rel="stylesheet" type="text/css" href="css/origin/common.css">

<meta charset="UTF-8">
<title>ログイン画面</title>
</head>
<body>
	<div class="container">

	<c:if test="${errMsg != null}" >
	    <div class="alert alert-danger" role="alert">
		  ${errMsg}
		</div>
	</c:if>

		<div id="form">
			<div class="heading">
				<h1>ログイン画面</h1>
			</div>
			<form class="signin" action="LoginServlet" method="post">



				<div class="login-area">


					<div class="form-group row">
						<label class="col-sm-2 col-form-label">ログインID</label>
						<div class="col-sm-8">
							<input type="text" class="form-control"name="loginId">
						</div>
					</div>

					<div class="form-group row">
						<label class="col-sm-2 col-form-label">パスワード</label>
						<div class="col-sm-8">
							<input type="password" class="form-control"name="password">
						</div>
					</div>

				</div>

				<div class="button_wrapper">
				<button type="submit" class="btn btn-primary"  name="button" value="1">ログイン</button>
				</div>

			</form>
		</div>
	</div>
</body>
</html>