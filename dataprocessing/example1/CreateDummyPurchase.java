import java.sql.*;
import java.util.*;
class CreateDummyPurchase
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
String billNumber;
int supplierCode,itemCode,quantity,rate;
int i=1;
for(x=1;x<=10;x++)
{
z=rand.nextInt(3)+1; //0(inclusive) and 3(exclusive)
cal.add(Calendar.DATE,z);
for(y=1;y<=100;y++)
{
billNumber=UUID.randomUUID().toString();
if(billNumber.length()>20)billNumber=billNumber.substring(0,20);
billDate=new java.sql.Date(cal.get(Calendar.YEAR)-1900,cal.get(Calendar.MONTH),cal.get(Calendar.DATE));
supplierCode=rand.nextInt(100)+1;
itemCode=rand.nextInt(400)+1; //since in case 0 it bill become 1 and 400 is not included
quantity=rand.nextInt(30)+1;
rate=rand.nextInt(10)+1;
ps=c.prepareStatement("insert into purchase(bill_number,bill_date,supplier_code,item_code,quantity,rate) values(?,?,?,?,?,?)");
ps.setString(1,billNumber);
ps.setDate(2,billDate);
ps.setInt(3,supplierCode);
ps.setInt(4,itemCode);
ps.setInt(5,quantity);
ps.setInt(6,rate);
ps.executeUpdate();
ps.close();
System.out.println(i+" inserted into purchase");
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