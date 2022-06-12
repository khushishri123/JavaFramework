class eg6psp
{
public static void main(String gg[])
{
Thread t;
t=new Thread(new Runnable(){
public void run()
{
for(int j=1;j<=50;j++)
{
System.out.print(j+" ");
try
{
Thread.sleep(50);
}catch(InterruptedException ie)
{
System.out.println(ie);
}
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