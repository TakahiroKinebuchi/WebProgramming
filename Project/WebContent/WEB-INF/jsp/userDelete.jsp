<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="css/origin/userdelete.css">

<meta charset="UTF-8">
<title>ユーザ削除確認</title>
</head>
<body>


	<div class="container">
			<ul class="nav justify-content-end">
			  <li class="nav-item">
			    <a class="nav-link username" href="">${userInfo.name} さん</a>
			  </li>
			  <li class="nav-item">
			    <a class="nav-link logout" href="LogoutServlet">ログアウト</a>
			  </li>
			</ul>

		<div id="form">
			<div class="heading">
				<h1>ユーザ削除確認</h1>
			</div>



			<form action="UserDeleteServlet" method="post">

			<div class="search-area">
			<div>
			<p><input type="hidden" value="${user.id}" name="id"></p>
			</div>
			<div class="form-group row">
						<label class="col-sm-2 col-form-label">ログインID</label>
						<label class="col-sm-2 col-form-label">${user.loginId }</label>
					</div>
			<div class="form-group row">
						<label>を本当に削除してよろしいでしょうか。</label>
			</div>
		</div>
				<div class="col-md-10 offset-md-5"style="display:inline-flex">
					<button type="submit" class="btn btn-danger" name="button">はい</button>
					<a class="btn btn-secondary" href="UserListServlet" role="button">いいえ</a>
				</div>

			</form>

			<div class="return">
				<p><a href="UserListServlet">戻る</a></p>
			</div>

		</div>



	</div>
</body>
</html>