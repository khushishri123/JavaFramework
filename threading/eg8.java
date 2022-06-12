class eg8psp
{
public static void main(String gg[])
{
Thread t;
t=new Thread(()->{
for(int k=501;k<=550;k++)
{
System.out.print(k+" ");
try
{
Thread.sleep(50);
}catch(InterruptedException ie)
{
System.out.println(ie);
}
}
});
t.start();
for(int i=201;i<=250;i++)
{
System.out.print(i+" ");
try
{
Thread.sleep(50);
}catch(InterruptedException ie)
{
System.out.println(ie);
}
}
}
}