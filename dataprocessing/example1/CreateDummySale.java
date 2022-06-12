import java.sql.*;
import java.util.*;
class CreateDummySale
{
public static void main(String gg[])
{
try
{
Connection c=DAOConnection.getConnection();
PreparedStatement ps;
Random rand=new Random();
int x,y,z;
Calendar cal=Calendar.getInstance();
cal.set(2018,0,1);
java.sql.Date billDate;
int customerCode,itemCode,quantity,rate;
int i=1;
for(x=1;x<=10;x++)
{
z=rand.nextInt(3)+1; //0(inclusive) and 3(exclusive)
cal.add(Calendar.DATE,z);
for(y=1;y<=100;y++)
{
billDate=new java.sql.Date(cal.get(Calendar.YEAR)-1900,cal.get(Calendar.MONTH),cal.get(Calendar.DATE));
customerCode=rand.nextInt(100)+1;
itemCode=rand.nextInt(400)+1; //since in case 0 it bill become 1 and 400 is not included
quantity=rand.nextInt(30)+1;
rate=rand.nextInt(10)+1;
ps=c.prepareStatement("insert into sale(bill_date,customer_code,item_code,quantity,rate) values(?,?,?,?,?)");
ps.setDate(1,billDate);
ps.setInt(2,customerCode);
ps.setInt(3,itemCode);
ps.setInt(4,quantity);
ps.setInt(5,rate);
ps.executeUpdate();
ps.close();
System.out.println(i+" inserted into sale");
i++;
}
}
c.close();
}catch(Exception e)
{
System.out.println(e.getMessage());
}
}
}