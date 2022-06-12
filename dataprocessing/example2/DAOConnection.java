import java.sql.*;
class DAOConnection
{
public static Connection getConnection() throws Exception
{
Class.forName("com.mysql.cj.jdbc.Driver");
return DriverManager.getConnection("jdbc:mysql://localhost:3306/dpdb","dp","dp");
}
public static void main(String gg[])
{
try
{
Connection c=getConnection();
c.close();
System.out.println("Done");
}catch(Exception e)
{
System.out.println(e.getMessage());
}
}
}