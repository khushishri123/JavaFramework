class aaa extends Thread
{
aaa()
{
start();
}
public void run()
{
for(int j=5001;j<=5300;j++)
{
System.out.print(j+" ");
try
{
Thread.sleep(100);
}catch(InterruptedException ie)
{
System.out.println(ie);
}
}
}
}
class eg1psp
{
public static void main(String gg[])
{
aaa a=new aaa();
for(int i=201;i<=500;i++)
{
System.out.print(i+" ");
try
{
Thread.sleep(100);
}catch(InterruptedException ie)
{
System.out.println(ie);
}
}
}
}