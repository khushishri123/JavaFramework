//Example of Scrollable ResultSet
//self created
import java.sql.*;
class eg4psp
{
public static void display(ResultSet rs)
{
try
{
System.out.printf("Roll Number: %d , Name: %s , Total Fee Received: %d\n",rs.getInt("roll_number"),rs.getString("name"),rs.getInt("total_fee_received"));
}catch(Exception e)
{
}
}
public static void main(String gg[])
{
try
{
Connection c;
Class.forName("com.mysql.cj.jdbc.Driver");
c=DriverManager.getConnection("jdbc:mysql://localhost:3306/dpdb","dp","dp");
PreparedStatement ps;
ResultSet rs;
String query="select * from student";
ps=c.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
rs=ps.executeQuery();
rs.first();
System.out.println("First Record: ");
display(rs);
System.out.println("Last Record: ");
rs.last();
display(rs);
System.out.println("Extracting in reverse order: ");
while(rs.previous())display(rs);
rs.close();
ps.close();
c.close();
}catch(Exception e)
{
System.out.println(e.getMessage());
}
}
}