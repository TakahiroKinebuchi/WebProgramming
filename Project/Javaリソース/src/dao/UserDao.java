package dao;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import model.User;

public class UserDao {
	/**
	 * 新規作成を行う
	 * ログインID、名前、パスワード、パスワード（確認）、生年月日をsqlに挿入する
	 */
	public void InsertNewUserInfo(String loginId, String name, String password2, String birthDate) {
		Connection conn = null;
		try {
			//データベースへ接続
			conn = DBManager.getConnection();
			//SELECT文を準備
			String sql = "INSERT INTO user(login_id, name, birth_date, password, create_date, update_date) VALUES(?,?,?,?, now(), now())";

			//暗号化を実装する
			Algorithm(password2);

			//sqlの?に引数をセットする
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, loginId);
			pStmt.setString(2, name);
			pStmt.setString(3, birthDate);
			pStmt.setString(4, password2);
			//sql実行
			int result2 = pStmt.executeUpdate();
			//追加された行数を出力
			System.out.println(result2);
			pStmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}finally {
            // データベース切断
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
		}
	}
	/**
	 * 更新を行う
	 * 名前、生年月日、パスワードをsqlに挿入する
	 */
	public void UpdateUserInfo(String Id, String name, String password2, String birthDate) {
		Connection conn = null;
		try {
			//データベースへ接続
			conn = DBManager.getConnection();
			//SELECT文を準備
			String sql = "UPDATE user SET name=?, birth_date=?, password=? WHERE id=?";

			//暗号化を実装する
			Algorithm(password2);

			//sqlの?に引数をセットする
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, name);
			pStmt.setString(2, birthDate);
			pStmt.setString(3, password2);
			pStmt.setString(4, Id);
			//sql実行
			int result = pStmt.executeUpdate();
			//追加された行数を出力
			System.out.println(result);
			pStmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}finally {
            // データベース切断
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
		}
	}
	/**
	 * 削除を行う
	 * Idに紐づくユーザ情報をsqlから消去する
	 */
	public void DeleteUserInfo(String Id) {
		Connection conn = null;
		try {
			//データベースへ接続
			conn = DBManager.getConnection();
			//SELECT文を準備
			String sql = "DELETE FROM user WHERE id=?";
			//sqlの?に引数をセットする
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, Id);
			//sql実行
			int result = pStmt.executeUpdate();
			//追加された行数を出力
			System.out.println(result);
			pStmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}finally {
            // データベース切断
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
		}
	}
	/**
	 * 新規登録時に入力されたログインIDとテーブルuserにあるログインIDと照らし合わせて、合うものがあるので戻り値falseを返す
	 */
	public boolean findByRegistration(String loginId) {
		Connection conn = null;
		try {
			//データベースへ接続
			conn = DBManager.getConnection();

			//SELECT文を準備
			String sql = "SELECT login_id FROM user WHERE login_id = ?";

			//SELECTを実行し、結果表を取得
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, loginId);
			ResultSet rs = pStmt.executeQuery();

			 if (!rs.next()) {
	                return true;
	            }

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			// データベース切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}

			}
		}
		return false;
	}

	/**
     * IDに紐づくユーザ情報を返す
     * @param loginId
     * @return
     */
	public User findByUserInfoWithId(String Id) {
		Connection conn = null;
		try {
			// データベースへ接続
			conn = DBManager.getConnection();

			// SELECT文を準備
			String sql = "SELECT * FROM user WHERE id = ?";

			// SELECTを実行し、結果表を取得
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, Id);
			ResultSet rs = pStmt.executeQuery();

			// 主キーに紐づくレコードは1件のみなので、rs.next()は1回だけ行う
			if (!rs.next()) {
				return null;
			}

			String loginId = rs.getString("login_id");
			String name = rs.getString("name");
			String password = rs.getString("password");
			Date birthDate = rs.getDate("birth_date");
			String createDate = rs.getString("create_date");
			String updateDate = rs.getString("update_date");
			return new User(Integer.parseInt(Id), loginId, name, birthDate, password, createDate, updateDate);

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			// データベース切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
	}

	/**
     * ログインIDとパスワードに紐づくユーザ情報を返す
     * @param loginId
     * @param password
     * @return
     */
    public User findByLoginInfo(String loginId, String password) {
        Connection conn = null;
        try {
            // データベースへ接続
            conn = DBManager.getConnection();

            // SELECT文を準備
            String sql = "SELECT * FROM user WHERE login_id = ? and password = ?";

             // SELECTを実行し、結果表を取得
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, loginId);
            pStmt.setString(2, password);
            ResultSet rs = pStmt.executeQuery();

             // 主キーに紐づくレコードは1件のみなので、rs.next()は1回だけ行う
            if (!rs.next()) {
                return null;
            }

            String loginIdData = rs.getString("login_id");
            String nameData = rs.getString("name");
            return new User(loginIdData, nameData);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            // データベース切断
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
       }
        /**
         * 全てのユーザ情報を取得する
         * @return
         */
        public List<User> findAll() {
            Connection conn = null;
            List<User> userList = new ArrayList<User>();

            try {
                // データベースへ接続
                conn = DBManager.getConnection();

                // SELECT文を準備
                // 管理者以外を取得するようSQLを変更する
                String sql = "SELECT * FROM user where login_id not in ('admin')";

                 // SELECTを実行し、結果表を取得
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                // 結果表に格納されたレコードの内容を
                // Userインスタンスに設定し、ArrayListインスタンスに追加
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String loginId = rs.getString("login_id");
                    String name = rs.getString("name");
                    Date birthDate = rs.getDate("birth_date");
                    String password = rs.getString("password");
                    String createDate = rs.getString("create_date");
                    String updateDate = rs.getString("update_date");
                    User user = new User(id, loginId, name, birthDate, password, createDate, updateDate);

                    userList.add(user);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            } finally {
                // データベース切断
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            }
            return userList;
        }

        //ユーザ一覧でログインID、名前、生年月日で検索
        public List<User> findSearch(String loginId, String name, String startDate, String endDate) {
            Connection conn = null;
            List<User> userList = new ArrayList<User>();

            try {
                // データベースへ接続
                conn = DBManager.getConnection();

                // SELECT文を準備
                // 管理者以外を取得するようSQLを変更する
                String sql = "SELECT * FROM user WHERE login_id not in ('admin')";

                if(!loginId.equals("")) {
                	sql += " AND login_id = '" + loginId + "'";
                }

                if(!name.equals("")) {
                	sql += " AND name LIKE '%" + name + "%'";
                }

                if(!startDate.equals("") || !endDate.equals("")) {
                	sql += " AND birth_date >= '" + startDate + "' AND birth_date < '" + endDate + "'";
                }

                 // SELECTを実行し、結果表を取得
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                // 結果表に格納されたレコードの内容を
                // Userインスタンスに設定し、ArrayListインスタンスに追加
                while (rs.next()) {
                    int id = rs.getInt("id");
                    loginId = rs.getString("login_id");
                    name = rs.getString("name");
                    Date birthDate = rs.getDate("birth_date");
                    String password = rs.getString("password");
                    String createDate = rs.getString("create_date");
                    String updateDate = rs.getString("update_date");
                    User user = new User(id, loginId, name, birthDate, password, createDate, updateDate);

                    userList.add(user);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            } finally {
                // データベース切断
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            }
            return userList;
        }

        //暗号化
        public String Algorithm(String str) {
        	//ハッシュを生成したい元の文字列
			String source = str;
			//ハッシュ生成前にバイト配列に置き換える際のCharset
			Charset charset = StandardCharsets.UTF_8;
			//ハッシュアルゴリズム
			String algorithm = "MD5";
			//ハッシュ生成処理
			byte[] bytes;
			try {
				bytes = MessageDigest.getInstance(algorithm).digest(source.getBytes(charset));
				String result = DatatypeConverter.printHexBinary(bytes);
				//標準出力
				System.out.println(result);
				//文字列を返す
				return result;
			} catch (NoSuchAlgorithmException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
				return null;
			}


        }
}
