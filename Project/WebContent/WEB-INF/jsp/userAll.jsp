<%@page import="model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<!-- Bootstrapのcssの読み込み -->
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
<!-- originパッケージのuser.cssの読み込み -->
<link rel="stylesheet" type="text/css" href="css/origin/user.css">

<meta charset="UTF-8">
<title>ユーザ一覧</title>
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
				<h1>ユーザ一覧</h1>
			</div>

			<div class="new">
				<p><a href="NewUserServlet">新規登録</a></p>
			</div>

			<form action="UserListServlet" method="post">

			<div class="search-area">

			<div class="form-group row">
						<label class="col-sm-2 col-form-label">ログインID</label>
						<div class="col-sm-9">
							<input type="text" name="login-id" id="login-id" class="form-control">
						</div>
					</div>
			<div class="form-group row">
						<label class="col-sm-2 col-form-label">ユーザ名</label>
						<div class="col-sm-9">
							<input type="text" name="user-name" id="user-name" class="form-control">
						</div>
					</div>
			<div class="form-group row">
						<label class="col-sm-2 col-form-label">生年月日</label>
						<div class="col-sm-9">
							<input type="date" name="date-start" id="date-start" class="form-control">
							～
							<input type="date" name="date-end" id="date-end" class="form-control">
						</div>
					</div>

			</div>

			<div class="button_wrapper">
				<button type="submit" class="btn btn-primary"  name="button" value="1">検索</button>
				</div>
			</form>
			<hr/>
		</div>


 <div class="table-responsive">
             <table class="table table-striped">
               <thead>
                 <tr>
                   <th>ログインID</th>
                   <th>ユーザ名</th>
                   <th>生年月日</th>
                   <th>アカウント編集リンク集</th>
                 </tr>
               </thead>
               <tbody>
                 <c:forEach var="user" items="${userList}" >
                   <tr>
                     <td>${user.loginId}</td>
                     <td>${user.name}</td>
                     <td>${user.birthDate}</td>
                     <!-- TODO 未実装；ログインボタンの表示制御を行う -->




						<td><c:choose>
								<c:when test="${userInfo.loginId == 'admin'}">
									<a class="btn btn-primary"
										href="UserDetailServlet?id=${user.id}">詳細</a>
									<a class="btn btn-success"
										href="UserUpdateServlet?id=${user.id}">更新</a>
									<a class="btn btn-danger"
										href="UserDeleteServlet?id=${user.id}">削除</a>
								</c:when>
								<c:otherwise>
									<a class="btn btn-primary"
										href="UserDetailServlet?id=${user.id}">詳細</a>
										<c:if test="${userInfo.loginId == user.loginId }">
											<a class="btn btn-success"
												href="UserUpdateServlet?id=${user.id}">更新</a>
										</c:if>
									</c:otherwise>
							</c:choose></td>
							</tr>
							</c:forEach>



				</tbody>
             </table>
           </div>
	</div>
</body>
</html>