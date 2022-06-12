//Example of Updatable Scrollable ResultSet
//self created
import java.sql.*;
class eg5psp
{
public static void display(ResultSet rs)
{
try
{
System.out.printf("Id: %d , Name: %s , Department: %s, Salary: %d\n",rs.getInt("id"),rs.getString("name"),rs.getString("department"),rs.getInt("salary"));
}catch(Exception e)
{
System.out.println(e.getMessage());
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
String query="select * from emp";
ps=c.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
rs=ps.executeQuery();
System.out.println("Updating salary of employees.");
System.out.println("If [10000 to 30000] adding 5000");
System.out.println("If [30001 to 60000] adding 10000");
System.out.println("If [60001 to 100000] adding 20000");
int updSal;
while(rs.next())
{
System.out.println("Before Updating");
display(rs);
int salary=rs.getInt("salary");
if(salary>=10000 && salary<=30000)
{
updSal=salary+5000;
rs.updateInt("salary",updSal);
}else
if(salary>=30001 && salary<=60000)
{
updSal=salary+10000;
rs.updateInt("salary",updSal);
}else
if(salary>=60001 && salary<=100000)
{
updSal=salary+20000;
rs.updateInt("salary",updSal);
}
rs.updateRow();
}
rs.close();
ps.close();
c.close();
}catch(Exception e)
{
System.out.println(e.getMessage());
}
}
}