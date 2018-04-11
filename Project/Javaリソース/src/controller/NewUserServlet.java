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

/**
 * Servlet implementation class NewUserServlet
 */
@WebServlet("/NewUserServlet")
public class NewUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//ログインセッションがない場合、ログイン画面にリダイレクトさせる
		HttpSession session = request.getSession();
		Object userInfo = session.getAttribute("userInfo");
		if (userInfo == null) {
			response.sendRedirect("LoginServlet");
			return;
		}
		// フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/newUser.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// リクエストパラメータの入力項目を取得
		String loginId = request.getParameter("loginId");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String password2 = request.getParameter("passwordcheck");
		String birthDate = request.getParameter("birthDate");

		//既に登録されているログインIDが入力された場合
		UserDao user = new UserDao();
		if(!user.findByRegistration(loginId)) {
			// リクエストスコープにエラーメッセージをセット
			request.setAttribute("errMsg", "すでに登録されたログインIDです。");

			// newUser.jspにフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/newUser.jsp");
			dispatcher.forward(request, response);
			return;
		}
		//パスワードとパスワード（確認）の入力内容が異なる場合
		if (!password.equals(password2)) {
			// リクエストスコープにエラーメッセージをセット
			request.setAttribute("errMsg", "パスワードとパスワード（確認）の入力内容が異なります");

			// newUser.jspにフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/newUser.jsp");
			dispatcher.forward(request, response);
			return;

		}
		//入力項目に一つでも未入力のものがある場合
		if (loginId.equals("") || name.equals("") || password.equals("") || password2.equals("") || birthDate.equals("")) {
			// リクエストスコープにエラーメッセージをセット
			request.setAttribute("errMsg", "未入力の項目があります。");

			// newUser.jspにフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/newUser.jsp");
			dispatcher.forward(request, response);
			return;
		}
		UserDao userDao = new UserDao();
		userDao.InsertNewUserInfo(loginId, name, password2, birthDate);

		//ユーザ一覧にリダイレクト
		response.sendRedirect("UserListServlet");
	}

}
