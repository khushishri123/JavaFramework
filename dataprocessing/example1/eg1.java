import java.util.*;
import java.sql.*;
import java.time.*;
class CustomerBalance
{
public int customerCode;
public String name;
public int totalSales;
public int totalReceipts;
}
class eg1psp
{
public static void main(String gg[])
{
try
{
List<CustomerBalance> customers=new ArrayList<>();
Connection c=DAOConnection.getConnection();
Statement statement=c.createStatement();
PreparedStatement preparedStatement;
String name;
int cd,sm,tr,ts,balance;
ResultSet rs1,rs2;
rs1=statement.executeQuery("select * from customer order by name");
Instant start=Instant.now();
CustomerBalance customerBalance;
while(rs1.next())
{
customerBalance=new CustomerBalance();
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
customerBalance.customerCode=cd;
customerBalance.name=name;
customerBalance.totalSales=ts;
customerBalance.totalReceipts=tr;
customers.add(customerBalance);
}
rs1.close();
statement.close();
c.close();
Instant end=Instant.now();
Duration timeTaken=Duration.between(start,end);
int x=0;
for(CustomerBalance customer:customers)
{
x++;
System.out.printf("%3d) %20s %10d\n",x,customer.name,(customer.totalSales-customer.totalReceipts));
}
System.out.println("Time taken: "+timeTaken.toMillis()+" milliseconds");
}catch(Exception e)
{
System.out.println("Error");
System.out.println(e.getMessage());
}
}
}