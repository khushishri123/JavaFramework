class eg7psp
{
public static void main(String gg[])
{
Runnable r;
r=()->{
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
};
Thread t=new Thread(r);
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