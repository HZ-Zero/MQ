import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class JDBC {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/mq?useUnicode=true&characterEncoding=utf8&useSSL=false";
    static final String USER = "root";
    static final String PASS = "123456";
    private static Connection conn = null;
    static ArrayList<String> keys =null;

    static  {
        try{
        Class.forName("com.mysql.jdbc.Driver");
        //2. ������ݿ�����
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    }
    static{
        try{
            keys=JDBC.findall();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection(){
        return conn;
    }
    public static ArrayList<String> findall() throws SQLException{
        ArrayList<String> list=new ArrayList<String>();
        Connection conn = JDBC.getConnection();
        Statement stmt = conn.createStatement();
        String sql = "select messagekey from mq";
        ResultSet rs = stmt.executeQuery(sql);
        while(rs.next()){
            String key=rs.getString("messagekey");
            list.add(key);
        }
        return list;
    }
    private static void Addrecorde(String key,String content) throws SQLException{
        Connection conn = JDBC.getConnection();
        String sql ="INSERT INTO mq(messagekey,content,uuid) values(?,?,?)";
        PreparedStatement ptmt = conn.prepareStatement(sql);
        ptmt.setString(1, key);
        ptmt.setString(2, content);
        ptmt.setString(3, UUID.randomUUID().toString().replaceAll("-", ""));
        ptmt.execute();
//        return list;
    }
    private static void Addrecorde(String key) throws SQLException{
        Connection conn = JDBC.getConnection();
        String sql ="INSERT INTO mq(messagekey,content,uuid) values(?,?,?)";
        PreparedStatement ptmt = conn.prepareStatement(sql);
        ptmt.setString(1, key);
        ptmt.setString(2, "");
        ptmt.setString(3, UUID.randomUUID().toString().replaceAll("-", ""));
        ptmt.execute();
//        return list;
    }
    public static int AddSer(String key) throws SQLException
    {
        if (keys.contains(key)){
//          ����key ����ʧ��
            return 0;
        } else
        { JDBC.Addrecorde(key);
            return 1;
        }
    }
    public static int DeleteSer(String key) throws SQLException
    {
        if (!keys.contains(key)){
//          ������key ɾ��ʧ��
            return 0;
        } else
        {
            Connection conn = JDBC.getConnection();
            String sql ="DELETE FROM mq WHERE messagekey= ?";
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, key);
            ptmt.execute();
            return 1;
        }
    }
    @Test
    public void testf() throws SQLException
    {
//        System.out.println(keys);
//        AddSer("asdasdf");
//        DeleteSer();
    }
}