import java.util.*;
import java.sql.*;
class CreateDummySuppliers
{
public static void main(String gg[])
{
try
{
Connection c=DAOConnection.getConnection();
PreparedStatement preparedStatement;
int x;
String name;
for(x=1;x<=100;x++)
{
name=UUID.randomUUID().toString();
if(name.length()>40)name=name.substring(0,40);
name=name.toUpperCase();
preparedStatement=c.prepareStatement("insert into supplier(name) values(?)");
preparedStatement.setString(1,name);
preparedStatement.executeUpdate();
System.out.printf("Inserted %d).  %s\n",x,name);
preparedStatement.close();
}
c.close();
}catch(Exception e)
{
System.out.println(e.getMessage());
}
}
}