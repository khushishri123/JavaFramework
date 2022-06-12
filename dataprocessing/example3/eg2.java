//how to implement call a procedure(call by reference)
import java.sql.*;
class eg2psp
{
public static void main(String gg[])
{
String name=gg[0];
try
{
Connection c;
Class.forName("com.mysql.cj.jdbc.Driver");
c=DriverManager.getConnection("jdbc:mysql://localhost:3306/dpdb","dp","dp");
CallableStatement cs;
cs=c.prepareCall("{call add_stud(?,?)}");
cs.setString(1,name);
cs.registerOutParameter(2,Types.INTEGER);
cs.execute();
int generatedRollNumber=cs.getInt(2);
c.close();
if(generatedRollNumber==0)
{
System.out.println("Unable to insert record");
}
else
{
System.out.println(name+" inserted and Roll Number generated is: "+generatedRollNumber);
}
}catch(Exception e)
{
System.out.println(e.getMessage());
}
}
}