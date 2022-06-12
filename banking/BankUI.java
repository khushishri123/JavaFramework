import com.thinking.machines.nframework.client.*;
class BankUI
{
public static void main(String gg[])
{
NFrameworkClient client=new NFrameworkClient();
try
{
String branchName=(String)client.execute("/banking/branchName",gg[0]);
System.out.println(branchName);
}catch(Throwable t)
{
BankingException bankingException=new BankingException(t.getMessage());
System.out.println(bankingException.getMessage());
}
}
}
