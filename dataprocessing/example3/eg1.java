import java.sql.*;
class eg1psp
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
cs=c.prepareCall("{?=call add_student(?)}");
cs.registerOutParameter(1,Types.INTEGER);
cs.setString(2,name);
cs.execute();
int generatedRollNumber=cs.getInt(1);
c.close();
if(generatedRollNumber==-1)System.out.println("Unable to insert record");
else System.out.println(name+" inserted and Roll Number generated is: "+generatedRollNumber);
}catch(Exception e)
{
System.out.println(e.getMessage());
}
}
}