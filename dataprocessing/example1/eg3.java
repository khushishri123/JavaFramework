import java.util.*;
import java.sql.*;
class eg3psp
{
public static void main(String gg[])
{
Calendar cal=Calendar.getInstance();
cal.set(2018,12,1);
int x,y,z;
Random rand=new Random();
System.out.println();
System.out.println("********** ");
int year=cal.get(Calendar.YEAR);
int month=cal.get(Calendar.MONTH);
int day=cal.get(Calendar.DATE);
System.out.println(year+" "+month+" "+day);
for(x=1;x<=10;x++)
{
z=rand.nextInt(3)+1;
cal.add(Calendar.DATE,z);
java.sql.Date billDate=new java.sql.Date(year-1900,month,day);
System.out.println(billDate);
}
}
}