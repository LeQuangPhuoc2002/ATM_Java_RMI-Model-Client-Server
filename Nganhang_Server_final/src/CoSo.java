
import java.sql.*;
import java.net.*;

public class CoSo {

    public static Connection cn;
    public static String TenDn="";

    public static void MoKetNoi() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("Đang kết nối vào CSDL: ");
            cn = DriverManager.getConnection("jdbc:sqlserver://PHUOCTACOO\\SQLEXPRESS:1433; databaseName=PhanTanKT2;user=sa;password=123");
            System.out.println("Da ket noi CSDL ");
        } catch (Exception t1) {
            t1.printStackTrace();
        }
    }
}
