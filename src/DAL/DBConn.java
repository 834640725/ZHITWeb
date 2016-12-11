package DAL;
import java.sql.*;

public class DBConn {
	private static final String url = "jdbc:sqlserver://localhost:1433;DatabaseName=ZHITWEB";
	private static final String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	/**
	 * ��ȡ���ӵķ���
	 * @return
	 */
	public static Connection getCon(){
		Connection conn=null;
		try {
			Class.forName(driver);
			conn=DriverManager.getConnection(url,"sa","peipei");
			System.out.println("���ݿ����ӳɹ�");
		} catch (Exception e) {
			System.out.println("���ݿ�����ʧ��");
			e.printStackTrace();
		}
		return conn;
	} 
	
	/**
	 * �ر�����
	 */
	public static void close(ResultSet rs,Statement stmt,Connection conn){
		try {
			if(rs!=null)
				rs.close();
			if(stmt !=null)
				stmt.close();
			if(conn!=null)
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//�ر����ݿ⣬�ͷ���Դ
	public static void closeDB(Connection conn,PreparedStatement pstm,ResultSet rs){
		try {
			if(rs!=null)
				rs.close();
			if(pstm!=null)  
				pstm.close();
			if(conn!=null)
				conn.close();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public static void main(String[] args){
		getCon();
	}
}