import java.sql.*;
import java.util.*;
import java.math.*;
class CreateDummyPayment
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
java.sql.Date paymentDate;
int supplierCode,amount;
int i=1;
for(x=1;x<=10;x++)
{
z=rand.nextInt(3)+1; //0(inclusive) and 3(exclusive)
cal.add(Calendar.DATE,z);
for(y=1;y<=10;y++)
{
paymentDate=new java.sql.Date(cal.get(Calendar.YEAR)-1900,cal.get(Calendar.MONTH),cal.get(Calendar.DATE));
supplierCode=rand.nextInt(100)+1;
amount=rand.nextInt(10)+1;
ps=c.prepareStatement("insert into payment(payment_date,supplier_code,amount) values(?,?,?)");
ps.setDate(1,paymentDate);
ps.setInt(2,supplierCode);
ps.setInt(3,amount);
ps.executeUpdate();
ps.close();
ps=c.prepareStatement("update supplier set total_payments=total_payments+? where code=?");
ps.setBigDecimal(1,new BigDecimal(String.valueOf(amount)));
ps.setInt(2,supplierCode);
ps.executeUpdate();
ps.close();
System.out.println(i+" inserted into payment");
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