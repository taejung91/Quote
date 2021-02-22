package sendDataToAndroid;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Random;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ConnectDB {

	private static ConnectDB instance = new ConnectDB();

	public static ConnectDB getInstance() {
		return instance;
	}

	public ConnectDB() {

	}

	String jdbcUrl = "jdbc:mysql://localhost:3306/quote?characterEncoding=UTF-8&serverTimezone=UTC"; // MySQL 계정
	String dbId = "root"; // MySQL 계정
	String dbPw = "java"; // 비밀번호
	Connection conn = null;
	PreparedStatement pstmt = null;
	PreparedStatement pstmt2 = null;
	PreparedStatement pstmt3 = null;
	ResultSet rs = null;
	String sql = "";
	String sql2 = "";
	String sql3 = "";
	String returns = "";

// 데이터베이스와 통신하기 위한 코드가 들어있는 메서드
	public String one_Quote_Insert_Update(String number, String id, String subject, String quote, String writer,
			String image, int time) {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 디비 연결

			conn = DriverManager.getConnection(jdbcUrl, dbId, dbPw);
			sql = "select * from one_quote where id = ?";// 조회
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			// pstmt.setString(2, quote);
			rs = pstmt.executeQuery();
			if (rs.next()) {

				if (rs.getString("id").equals(id)) {

					sql3 = "update one_quote set number= ?, subject= ?, quote= ?, writer= ?, image= ?, time= ? where id= ?";
					pstmt3 = conn.prepareStatement(sql3);
					pstmt3.setString(1, number);
					pstmt3.setString(2, subject);
					pstmt3.setString(3, quote);
					pstmt3.setString(4, writer);
					pstmt3.setString(5, image);
					pstmt3.setInt(6, time);
					pstmt3.setString(7, id);

					pstmt3.executeUpdate();
					returns = "update";
				}

			} else {

				// 넣고자 하는 정보가 없을 경우 (회원가입이 가능한 경우)
				sql2 = "insert into one_quote values(?,?,?,?,?,?,?,now())"; // 삽입
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, number);
				pstmt2.setString(2, id);
				pstmt2.setString(3, subject);
				pstmt2.setString(4, quote);
				pstmt2.setString(5, writer);
				pstmt2.setString(6, image);
				pstmt2.setInt(7, time);

				pstmt2.executeUpdate();
				returns = "인설트";

			}

		} catch (

		Exception e) {
			e.printStackTrace();
			System.out.print("오류");
		} finally {
			if (pstmt3 != null)
				try {
					pstmt3.close();
				} catch (SQLException ex) {
				}
			if (pstmt2 != null)
				try {
					pstmt2.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}
		return returns;
	}

	public One_Quote one_Quote_Select(String id) {

		One_Quote one = new One_Quote();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 디비 연결

			conn = DriverManager.getConnection(jdbcUrl, dbId, dbPw);
			sql = "select * from one_quote where id = ?";// 조회
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				one.setQuote(rs.getString("quote"));
				one.setWriter(rs.getString("writer"));
				one.setImage(rs.getString("image"));
				one.setTime(rs.getInt("time"));

			}

		} catch (

		Exception e) {
			e.printStackTrace();
		} finally {

			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}
		return one;

	}

	public String one_Quote_Time(String id, int time) {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 디비 연결

			conn = DriverManager.getConnection(jdbcUrl, dbId, dbPw);
			sql = "update one_quote set time =? where id = ?";// 조회
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, time);
			pstmt.setString(2, id);
			pstmt.executeUpdate();

		} catch (

		Exception e) {
			e.printStackTrace();
		} finally {

			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}

		return returns;
	}

	public void c_Quote_Insert(String number, String id, String subject, String quote, String writer, String image) {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 디비 연결

			conn = DriverManager.getConnection(jdbcUrl, dbId, dbPw);

			// 넣고자 하는 정보가 없을 경우 (회원가입이 가능한 경우)
			sql2 = "insert into c_quote(number, id, subject, quote, writer, image) values(?,?,?,?,?,?)"; // 삽입
			pstmt2 = conn.prepareStatement(sql2);
			pstmt2.setString(1, number);
			pstmt2.setString(2, id);
			pstmt2.setString(3, subject);
			pstmt2.setString(4, quote);
			pstmt2.setString(5, writer);
			pstmt2.setString(6, image);

			pstmt2.executeUpdate();

		} catch (

		Exception e) {
			e.printStackTrace();

		} finally {

			if (pstmt2 != null)
				try {
					pstmt2.close();
				} catch (SQLException ex) {
				}

			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}

	}

	public String c_Quote_Select(String id, String number) {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 디비 연결

			conn = DriverManager.getConnection(jdbcUrl, dbId, dbPw);
			sql = "select * from c_quote where id = ? AND number =? ";// 조회
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, number);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				
				if (rs.getString("number").equals(number)) {

					returns = rs.getString("number");
				}
				
			} else {
				returns = "0";

			}

		} catch (

		Exception e) {
			e.printStackTrace();
		} finally {

			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}
		return returns;

	}

	public void c_Quote_Delete(String id, String number) {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 디비 연결

			conn = DriverManager.getConnection(jdbcUrl, dbId, dbPw);
			sql = "delete from c_quote where id =? AND number =? ";// 조회
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, number);

			pstmt.executeUpdate();

		} catch (

		Exception e) {
			e.printStackTrace();
		} finally {

			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}

	}

	public String select(String id) {

		ArrayList<C_Quote> quoteList = new ArrayList<C_Quote>();
		JSONArray jArray = new JSONArray();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 디비 연결

			conn = DriverManager.getConnection(jdbcUrl, dbId, dbPw);
			sql = "select * from c_quote where id= ?";// 조회
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				C_Quote quote = new C_Quote();

				quote.setNumber(rs.getString("number"));
				quote.setId(rs.getString("id"));
				quote.setSubject(rs.getString("subject"));
				quote.setQuote(rs.getString("quote"));
				quote.setWriter(rs.getString("writer"));
				quote.setImage(rs.getString("image"));
				quote.setC_number(String.valueOf(rs.getInt("c_number")));

				quoteList.add(quote);
			}

			for (int i = 0; i < quoteList.size(); i++) {
				JSONObject object = new JSONObject();
				object.put("number", quoteList.get(i).getNumber());
				object.put("id", quoteList.get(i).getId());
				object.put("subject", quoteList.get(i).getSubject());
				object.put("quote", quoteList.get(i).getQuote());
				object.put("writer", quoteList.get(i).getWriter());
				object.put("image", quoteList.get(i).getImage());
				object.put("c_number", quoteList.get(i).getC_number());

				jArray.add(object);

			}

		} catch (

		Exception e) {
			e.printStackTrace();
		} finally {

			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}

		return jArray.toJSONString();

	}

	public String select_Subject(String id, String subject) {

		ArrayList<C_Quote> quoteList = new ArrayList<C_Quote>();
		JSONArray jArray = new JSONArray();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 디비 연결

			conn = DriverManager.getConnection(jdbcUrl, dbId, dbPw);
			sql = "select * from c_quote where id= ? AND subject= ? ";// 조회
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, subject);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				C_Quote quote = new C_Quote();

				quote.setNumber(rs.getString("number"));
				quote.setId(rs.getString("id"));
				quote.setSubject(rs.getString("subject"));
				quote.setQuote(rs.getString("quote"));
				quote.setWriter(rs.getString("writer"));
				quote.setImage(rs.getString("image"));
				quote.setC_number(String.valueOf(rs.getInt("c_number")));

				quoteList.add(quote);
			}

			for (int i = 0; i < quoteList.size(); i++) {
				JSONObject object = new JSONObject();
				object.put("number", quoteList.get(i).getNumber());
				object.put("id", quoteList.get(i).getId());
				object.put("subject", quoteList.get(i).getSubject());
				object.put("quote", quoteList.get(i).getQuote());
				object.put("writer", quoteList.get(i).getWriter());
				object.put("image", quoteList.get(i).getImage());
				object.put("c_number", quoteList.get(i).getC_number());

				jArray.add(object);

			}

		} catch (

		Exception e) {
			e.printStackTrace();
		} finally {

			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}

		return jArray.toJSONString();

	}

	public void f_Quote_Delete(String id, String number) {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 디비 연결

			conn = DriverManager.getConnection(jdbcUrl, dbId, dbPw);
			sql = "delete from c_quote where id =? AND c_number =? ";// 조회
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, number);

			pstmt.executeUpdate();

		} catch (

		Exception e) {
			e.printStackTrace();
		} finally {

			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}

	}

	public void addQuote(String subject, String quote, String writer) {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 디비 연결

			conn = DriverManager.getConnection(jdbcUrl, dbId, dbPw);

			// 넣고자 하는 정보가 없을 경우 (회원가입이 가능한 경우)
			sql2 = "insert into quotes(subject, quote, writer) values(?,?,?)"; // 삽입
			pstmt2 = conn.prepareStatement(sql2);
			pstmt2.setString(1, subject);
			pstmt2.setString(2, quote);
			pstmt2.setString(3, writer);

			pstmt2.executeUpdate();

		} catch (

		Exception e) {
			e.printStackTrace();

		} finally {

			if (pstmt2 != null)
				try {
					pstmt2.close();
				} catch (SQLException ex) {
				}

			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}

	}

	public One_Quote randomQuote() {

		One_Quote quote = new One_Quote();
		Random random = new Random();
		int index = random.nextInt(117);

		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 디비 연결

			conn = DriverManager.getConnection(jdbcUrl, dbId, dbPw);
			sql = "select * from quotes where number = ?";// 조회
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, index);
			rs = pstmt.executeQuery();
			if (rs.next()) {

				quote.setNumber(rs.getString("number"));
				quote.setSubject(rs.getString("subject"));
				quote.setQuote(rs.getString("quote"));
				quote.setWriter(rs.getString("writer"));

			}

		} catch (

		Exception e) {
			e.printStackTrace();
		} finally {

			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}

		return quote;

	}

	public String quoteList() {

		ArrayList<One_Quote> quoteList = new ArrayList<One_Quote>();
		JSONArray jArray = new JSONArray();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 디비 연결

			conn = DriverManager.getConnection(jdbcUrl, dbId, dbPw);
			sql = "select * from quotes";// 조회
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				One_Quote quote = new One_Quote();

				quote.setNumber(rs.getString("number"));
				quote.setSubject(rs.getString("subject"));
				quote.setQuote(rs.getString("quote"));
				quote.setWriter(rs.getString("writer"));

				quoteList.add(quote);
			}

			for (int i = 0; i < quoteList.size(); i++) {
				JSONObject object = new JSONObject();
				object.put("number", quoteList.get(i).getNumber());
				object.put("subject", quoteList.get(i).getSubject());
				object.put("quote", quoteList.get(i).getQuote());
				object.put("writer", quoteList.get(i).getWriter());

				jArray.add(object);

			}

		} catch (

		Exception e) {
			e.printStackTrace();
		} finally {

			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}

		return jArray.toJSONString();

	}

	public String quoteSubjectList(String subject) {

		ArrayList<One_Quote> quoteList = new ArrayList<One_Quote>();
		JSONArray jArray = new JSONArray();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 디비 연결

			conn = DriverManager.getConnection(jdbcUrl, dbId, dbPw);
			sql = "select * from quotes where subject =?";// 조회
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, subject);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				One_Quote quote = new One_Quote();

				quote.setNumber(rs.getString("number"));
				quote.setSubject(rs.getString("subject"));
				quote.setQuote(rs.getString("quote"));
				quote.setWriter(rs.getString("writer"));

				quoteList.add(quote);
			}

			for (int i = 0; i < quoteList.size(); i++) {
				JSONObject object = new JSONObject();
				object.put("number", quoteList.get(i).getNumber());
				object.put("subject", quoteList.get(i).getSubject());
				object.put("quote", quoteList.get(i).getQuote());
				object.put("writer", quoteList.get(i).getWriter());

				jArray.add(object);

			}

		} catch (

		Exception e) {
			e.printStackTrace();
		} finally {

			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}

		return jArray.toJSONString();

	}

	public String curTime(String id) {
		Timestamp time = null;
		ResultSetMetaData rsmd;
		String result = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 디비 연결

			conn = DriverManager.getConnection(jdbcUrl, dbId, dbPw);
			sql = "select * from one_quote where id = ?";// 조회
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			// pstmt.setString(2, quote);
			rs = pstmt.executeQuery();
			if (rs.next()) {

				time = rs.getTimestamp("c_time");
			}

			sql2 = "select TIMESTAMPDIFF(SECOND, ?, now())";
			pstmt3 = conn.prepareStatement(sql2);
			pstmt3.setTimestamp(1, time);
			rs = pstmt3.executeQuery();
			rsmd = rs.getMetaData();

			if (rs.next()) {

				result = rs.getObject(1).toString();

			}

		} catch (

		Exception e) {
			e.printStackTrace();
			System.out.print("오류");
		} finally {
			if (pstmt3 != null)
				try {
					pstmt3.close();
				} catch (SQLException ex) {
				}
			if (pstmt2 != null)
				try {
					pstmt2.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}
		return result;
	}

}
