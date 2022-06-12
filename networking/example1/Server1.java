import java.io.*;
import java.net.*;
class Server1
{
private ServerSocket serverSocket;
Server1()
{
try
{
serverSocket=new ServerSocket(5500);
startListening();

}catch(Exception e)
{
System.out.println(e);
}
}
private void startListening()
{
try
{
InputStream is;
InputStreamReader isr;
OutputStream os;
OutputStreamWriter osw;
StringBuffer sb;
int x;
int c1,c2;
String pc1,pc2,pc3;//pc1 means piece 1 and so on..
int rollNumber;
String name;
String gender;
String request,response;
Socket socket;
while(true)
{
System.out.println("Server is ready to accept request on port 5500");
socket=serverSocket.accept();//Server Socket goes in wait mode to accept request because of this line
is=socket.getInputStream();
isr=new InputStreamReader(is);
sb=new StringBuffer();
while(true)
{
x=isr.read();
if(x==-1)break;
if(x=='#')break;
sb.append((char)x);
}
request=sb.toString();
System.out.println("Request Arrived: "+request);
c1=request.indexOf(",");
c2=request.indexOf(",",c1+1);
pc1=request.substring(0,c1);
pc2=request.substring(c1+1,c2);
pc3=request.substring(c2+1);
rollNumber=Integer.parseInt(pc1);
name=pc2;
gender=pc3;
System.out.printf("Roll Number: %d, Name: %s, Gender: %s\n",rollNumber,name,gender);
response="Data Saved#";
os=socket.getOutputStream();
osw=new OutputStreamWriter(os);
osw.write(response);
osw.flush();//Very Very Very Important
socket.close();
}
}catch(Exception e)
{
System.out.println(e);
}
}
public static void main(String gg[])
{
Server1 server=new Server1();
}
}