class mdm
{
private int num;
public void setNum(int num)
{
this.num=num;
System.out.println("Produced: "+this.num);
}
public int getNum()
{
System.out.println("Consumed: "+this.num);
return this.num;
}
}
class Producer extends Thread
{
private mdm medium;
Producer(mdm medium)
{
this.medium=medium;
start();
}
public void run()
{
for(int i=201;i<=250;i++)
{
this.medium.setNum(i);
}
}
}
class Consumer extends Thread
{
private mdm medium;
Consumer(mdm medium)
{
this.medium=medium;
start();
}
public void run()
{
int x,y;
for(x=1;x<=50;x++)
{
y=this.medium.getNum();
}
}
}
class eg3psp
{
public static void main(String gg[])
{
mdm medium=new mdm();
Producer p=new Producer(medium);
Consumer c=new Consumer(medium);
}
}