class aaa extends Thread
{
aaa()
{
start();
}
public void run()
{
for(int i=1001;i<=1500;i++)
{
System.out.print(i+" ");
try
{
Thread.sleep(50);
}catch(InterruptedException ie)
{
System.out.print(ie);
}
}
}
}
class eg5psp
{
public static void main(String gg[])
{
aaa a=new aaa();
for(int x=201;x<=250;x++)
{
System.out.print(x+" ");
try
{
Thread.sleep(250);
}catch(InterruptedException ie)
{
System.out.print(ie);
}
}
try
{
a.join();
}catch(InterruptedException ie)
{
System.out.println(ie);
}
for(int e=501;e<=550;e++)
{
System.out.print(e+" ");
try
{
Thread.sleep(250);
}catch(InterruptedException ie)
{
System.out.print(ie);
}
}
}
}