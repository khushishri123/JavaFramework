import java.sql.*;
import java.util.*;
class CreateDummyReciept
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
java.sql.Date recieptDate;
int customerCode,amount;
int i=1;
for(x=1;x<=10;x++)
{
z=rand.nextInt(3)+1; //0(inclusive) and 3(exclusive)
cal.add(Calendar.DATE,z);
for(y=1;y<=10;y++)
{
recieptDate=new java.sql.Date(cal.get(Calendar.YEAR)-1900,cal.get(Calendar.MONTH),cal.get(Calendar.DATE));
customerCode=rand.nextInt(100)+1;
amount=rand.nextInt(10)+1;
ps=c.prepareStatement("insert into reciept(reciept_date,customer_code,amount) values(?,?,?)");
ps.setDate(1,recieptDate);
ps.setInt(2,customerCode);
ps.setInt(3,amount);
ps.executeUpdate();
ps.close();
System.out.println(i+" inserted into reciept");
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