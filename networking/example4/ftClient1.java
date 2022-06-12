import java.io.*;
import java.net.*;
class FTClient
{
public static void main(String gg[])
{
try
{
String fileName=gg[0];
File file=new File(fileName);
if(file.exists()==false)
{
System.out.println("File : "+fileName+" does not exist");
return;
}
if(file.isDirectory())
{
System.out.println("File : "+fileName+" is a directory , not a file");
return;
}
long lengthOfFile=file.length();
String name=file.getName();
long x,p;
int i,r,j;
byte header[]=new byte[1024];
x=lengthOfFile;
i=0;
while(x>0)
{
header[i]=(byte)(x%10);
x=x/10;
i++;
}
header[i]=(byte)',';
i++;
r=0;
x=name.length();
while(r<x)
{
header[i]=(byte)name.charAt(r);
i++;
r++;
}
while(i<=1023)
{
header[i]=(byte)32;
i++;
}
Socket socket=new Socket("localhost",5500);
OutputStream os=socket.getOutputStream();
os.write(header,0,1024); //from which index , how many
os.flush();

InputStream is=socket.getInputStream();
byte ack[]=new byte[1];
int bytesReadCount;
while(true)
{
bytesReadCount=is.read(ack);
if(bytesReadCount==-1)continue;
break;
}

//To read from file we have to create object of FileInputStream
FileInputStream fis=new FileInputStream(file);
int chunkSize=4096;
byte bytes[]=new byte[chunkSize];
j=0;
while(j<lengthOfFile)
{
bytesReadCount=fis.read(bytes);
os.write(bytes,0,4096);
os.flush();
j+=bytesReadCount;
}
fis.close();
while(true)
{
bytesReadCount=is.read(ack);
if(bytesReadCount==-1)continue;
break;
}
System.out.println("File :"+fileName+" uploaded");
socket.close();
}catch(Exception e)
{
System.out.println(e);
}
}
}