import java.util.*;
import java.sql.*;
import java.time.*;
import java.math.*;
class CustomerBalance
{
public int customerCode;
public String name;
public BigDecimal totalSales;
public BigDecimal totalReceipts;
}
class eg4psp
{
public static void main(String gg[])
{
try
{
List<CustomerBalance> customers=new ArrayList<>();
Connection c=DAOConnection.getConnection();
Statement statement=c.createStatement();
ResultSet rs1;
rs1=statement.executeQuery("select * from customer order by name");
Instant start=Instant.now();
CustomerBalance customerBalance;
while(rs1.next())
{
customerBalance=new CustomerBalance();
customerBalance.customerCode=rs1.getInt("code");
customerBalance.name=rs1.getString("name").trim();
customerBalance.totalSales=rs1.getBigDecimal("total_sales");
customerBalance.totalReceipts=rs1.getBigDecimal("total_receipts");
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
System.out.printf("%3d) %10d %20s %10s\n",x,customer.customerCode,customer.name,customer.totalSales.subtract(customer.totalReceipts).toPlainString());
}
System.out.println("Time taken: "+timeTaken.toMillis()+" milliseconds");
}catch(Exception e)
{
System.out.println("Error");
System.out.println(e.getMessage());
}
}
}