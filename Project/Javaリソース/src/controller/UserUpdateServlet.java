package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDao;
import model.User;

/**
 * Servlet implementation class UserUpdateServlet
 */
@WebServlet("/UserUpdateServlet")
public class UserUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//ログインセッションがない場合、ログイン画面にリダイレクトさせる
		HttpSession session = request.getSession();
		Object userInfo = session.getAttribute("userInfo");
		if(userInfo == null) {
			response.sendRedirect("LoginServlet");
			return;
		}

		//URLからGETパラメータとしてIDを受け取る
		String Id = request.getParameter("id");

		//確認用：idをコンソールに出力
		System.out.println(Id);

		//未実装：idを引数にして、idに紐づくユーザ情報を出力する
		UserDao userDao = new UserDao();
		User user = userDao.findByUserInfoWithId(Id);

		//未実装：ユーザ情報をリクエストスコープにセットしてjspにフォワード
		request.setAttribute("user", user);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userUpdate.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// リクエストパラメータの入力項目を取得
		String Id = request.getParameter("id");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String password2 = request.getParameter("passwordcheck");
		String birthDate = request.getParameter("birthDate");


		//パスワードとパスワード（確認）の入力内容が異なる場合
		if (!password.equals(password2)) {
			// リクエストスコープにエラーメッセージをセット
			request.setAttribute("errMsg", "パスワードとパスワード（確認）の入力内容が異なります");

			// userUpdate.jspにフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userUpdate.jsp");
			dispatcher.forward(request, response);
			return;

		}
		//入力項目に一つでも未入力のものがある場合
		if (name.equals("") || password.equals("") || password2.equals("") || birthDate.equals("")) {
			// リクエストスコープにエラーメッセージをセット
			request.setAttribute("errMsg", "未入力の項目があります。");


			// userUpdate.jspにフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/userUpdate.jsp");
			dispatcher.forward(request, response);
			return;
		}
		UserDao userDao = new UserDao();
		userDao.UpdateUserInfo(Id, name, password2, birthDate);

		//ユーザ一覧にリダイレクト
		response.sendRedirect("UserListServlet");
	}

}
