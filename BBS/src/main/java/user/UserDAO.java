package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	//생성자 만들기
	public UserDAO() { //실제로 mysql에 접속하게 해주는 부분
		try {
			String dbURL = "jdbc:mysql://localhost:3306/bbs?useUnicode=true&characterEncoding=UTF8";
			String dbID = "root";
			String dbPassword = "wkdgnsok8229!";
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword); 
		}catch(Exception e){
			e.printStackTrace(); //오류가 뭔지 출력해주세요
		}
	}
	//로그인기능구현
	public int login(String userID, String userPassword) {
		String SQL = "SELECT userPassword FROM USER WHERE userID = ?";
		try {
			pstmt = conn.prepareStatement(SQL); // 해킹을 방지하기 위해 처음엔 ?로 해놨다가 나중에 userID를 넣어주는것
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery(); //resultset에 실행한 결과 넣어주기
			if(rs.next()) { //아이디가 있으면 실행됌
				if(rs.getString(1).equals(userPassword))
					return 1; //로그인 성공
				else
					return 0; //비밀번호 불일치
			}
			return -1; //아이디가 없다
		}catch(Exception e){
			e.printStackTrace();
		}
		return -2; //데이터베이스 오류를 의미함
	}
	//회원가입 기능 구현
	public int join(User user) {
		String SQL = "INSERT INTO USER VALUES(?, ?, ?, ?, ?)";
			try {
				pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, user.getUserID());
				pstmt.setString(2, user.getUserPassword());
				pstmt.setString(3, user.getUserName());
				pstmt.setString(4, user.getUserGender());
				pstmt.setString(5, user.getUserEmail());
				return pstmt.executeUpdate();
			}catch(Exception e){
				e.printStackTrace();
			}
			return -1; //데이터베이스오류
	}
	
}
