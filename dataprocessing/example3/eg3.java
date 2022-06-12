//How to implement Rollback,Commit and SAVEPOINT in procedure through JDBC
//Also how to do Rollback in case of exception
//self created
import java.sql.*;
class eg3psp
{
public static void main(String gg[])
{
try
{
String cardNumber=gg[0];
String accountNumber=gg[1];
int amount=Integer.parseInt(gg[2]);
Connection c;
Class.forName("com.mysql.cj.jdbc.Driver");
c=DriverManager.getConnection("jdbc:mysql://localhost:3306/dpdb","dp","dp");
c.setAutoCommit(false);
PreparedStatement preparedStatement;
CallableStatement cs;
Savepoint savepoint=null;

preparedStatement=c.prepareStatement("insert into atm_log(card_number,tran_amount) values(?,?)");
preparedStatement.setString(1,cardNumber);
preparedStatement.setInt(2,amount);
preparedStatement.executeUpdate();
preparedStatement.close();
savepoint=c.setSavepoint("logged");

preparedStatement=c.prepareStatement("insert into bank_tran(account_number,tran_amount,tran_type) values(?,?,?)");
preparedStatement.setString(1,accountNumber);
preparedStatement.setInt(2,amount);
preparedStatement.setString(3,"W");
preparedStatement.executeUpdate();
preparedStatement.close();

cs=c.prepareCall("{call dispenseCash(?,?)}");
cs.setInt(1,amount);
cs.registerOutParameter(2,Types.INTEGER);
cs.execute();
int dispensed=cs.getInt(2);
if(dispensed==0) c.rollback(savepoint);
c.commit();
c.close();
}catch(Exception e)
{
System.out.println(e.getMessage());
}
}
}