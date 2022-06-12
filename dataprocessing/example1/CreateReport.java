import java.util.*;
import java.sql.*;
import java.time.*;
class CreateReport
{
public static void main(String gg[])
{
try
{
Connection c=DAOConnection.getConnection();
Statement statement=c.createStatement();
PreparedStatement preparedStatement;
String name;
int cd,sm,tr,ts,balance;
ResultSet rs1,rs2;
rs1=statement.executeQuery("select * from customer");
Instant start=Instant.now();
System.out.println(LocalDateTime.now());
while(rs1.next())
{
cd=rs1.getInt("code");
name=rs1.getString("name").trim();
preparedStatement=c.prepareStatement("select sum(quantity*rate) as sm from sale where customer_code=?"); 
preparedStatement.setInt(1,cd);
rs2=preparedStatement.executeQuery();
ts=0;
tr=0;
if(rs2.next())
{
ts=rs2.getInt("sm");
}
rs2.close();
preparedStatement.close();
preparedStatement=c.prepareStatement("select sum(amount) as sm from reciept where customer_code=?");
preparedStatement.setInt(1,cd);
rs2=preparedStatement.executeQuery();
if(rs2.next())
{
tr=rs2.getInt("sm");
}
preparedStatement.close();
balance=ts-tr;
System.out.println("Customer Code: "+cd+" Name: "+name+" Balance: "+balance);
}
rs1.close();
Instant end=Instant.now();
Duration timeTaken=Duration.between(start,end);
System.out.println("Time taken: "+timeTaken.toMillis()+" milliseconds");
System.out.print("\n\n\n");

//for supplier

rs1=statement.executeQuery("select * from supplier");
start=Instant.now();
System.out.println(LocalDateTime.now());
while(rs1.next())
{
cd=rs1.getInt("code");
name=rs1.getString("name").trim();
preparedStatement=c.prepareStatement("select sum(quantity*rate) as sm from purchase where supplier_code=?"); 
preparedStatement.setInt(1,cd);
rs2=preparedStatement.executeQuery();
ts=0;
tr=0;
if(rs2.next())
{
ts=rs2.getInt("sm");
}
rs2.close();
preparedStatement.close();
preparedStatement=c.prepareStatement("select sum(amount) as sm from payment where supplier_code=?");
preparedStatement.setInt(1,cd);
rs2=preparedStatement.executeQuery();
if(rs2.next())
{
tr=rs2.getInt("sm");
}
preparedStatement.close();
balance=ts-tr;
System.out.println("Supplier Code: "+cd+" Name: "+name+" Balance: "+balance);
}
rs1.close();
statement.close();
c.close();
end=Instant.now();
timeTaken=Duration.between(start,end);
System.out.println("Time taken: "+timeTaken.toMillis()+" milliseconds");

}catch(Exception e)
{
System.out.println("Error");
System.out.println(e.getMessage());
}
}
}