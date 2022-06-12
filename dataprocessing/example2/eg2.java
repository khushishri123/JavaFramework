//Remember Month in Cal starts from 0 to 11
//but Month in sql starts from 1 to 12
//Dont do +1 in Month sql will automatically do it.
//Just subtract 1900 from year because sql do +1900 in year part.
//0 Month in Calendar represents Jan and 1 Month in sql respresents Jan
import java.util.*;
import java.sql.*;
class eg2psp
{
public static void main(String gg[])
{
Calendar c=Calendar.getInstance();
c.set(2018,0,1);
System.out.println(c.get(Calendar.DATE));
System.out.println(c.get(Calendar.MONTH));
System.out.println(c.get(Calendar.YEAR));
c.add(Calendar.DATE,31);
System.out.println("After adding 31 days");
System.out.println(c.get(Calendar.DATE));
System.out.println(c.get(Calendar.MONTH));
System.out.println(c.get(Calendar.YEAR));
System.out.println("*************");
java.sql.Date sqlDate=new java.sql.Date(c.get(Calendar.YEAR)-1900,c.get(Calendar.MONTH),c.get(Calendar.DATE));
System.out.println(sqlDate);
}
}