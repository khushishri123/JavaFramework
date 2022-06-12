import java.io.*;
import java.net.*;
class City implements Serializable
{
public int code;
public String name;
}
class Student implements Serializable
{
public int rollNumber;
public String name;
public char gender;
public City city;
}
class Client3
{
public static void main(String gg[])
{
try
{
int rollNumber=Integer.parseInt(gg[0]);
String name=gg[1];
char gender=gg[2].charAt(0);
int cityCode=Integer.parseInt(gg[3]);
String cityName=gg[4];
City c=new City();
c.code=cityCode;
c.name=cityName;
Student s=new Student();
s.rollNumber=rollNumber;
s.name=name;
s.gender=gender;
s.city=c;
//Now for Serialising Object
ByteArrayOutputStream baos=new ByteArrayOutputStream();
ObjectOutputStream oos=new ObjectOutputStream(baos);
oos.writeObject(s);
oos.flush();
byte objectBytes[];
objectBytes=baos.toByteArray();
int requestLength=objectBytes.length;
byte header[]=new byte[1024];
int x,i,j;
x=requestLength;
i=1023;
while(x>0)
{
j=x%10;
header[i]=(byte)j;
x=x/10;
i--;
}
Socket socket=new Socket("localhost",5500);//Connection Established
OutputStream os=socket.getOutputStream();
os.write(header,0,1024);// from which index and how many
os.flush();
//Now before sending the data ,we will recieve acknowledgement from
//Server side,and that ack will be of one byte only
InputStream is=socket.getInputStream();
byte ack[]=new byte[1];
int bytesReadCount;

while(true)
{
bytesReadCount=is.read(ack);
if(bytesReadCount==-1) continue;
break;
}

int bytesToSend=requestLength;
int chunkSize=1024;
j=0;
while(j<bytesToSend)
{
if((bytesToSend-j)<chunkSize)chunkSize=bytesToSend-j;
os.write(objectBytes,j,chunkSize);
os.flush();
j=j+chunkSize;
}

int bytesToRecieve=1024; //since Header will be of 1024
byte tmp[]=new byte[1024];
int k=0;
i=0;
j=0;
while(j<bytesToRecieve)
{
bytesReadCount=is.read(tmp); //read har bar 0 index se rekhega data ko
if(bytesReadCount==-1)continue;
for(k=0;k<bytesReadCount;k++)
{
header[i]=tmp[k];
i++;
}
j=j+bytesReadCount;
}
int responseLength=0;
i=1;
j=1023;
while(j>=0)
{
responseLength=responseLength+(header[j]*i);
i=i*10;
j--;
}


//Now after reading header ,sending ack.
ack[0]=1;
os.write(ack,0,1);
os.flush(); //ack. sent

byte response[]=new byte[responseLength];
i=0;
j=0;
bytesToRecieve=responseLength;
while(j<bytesToRecieve)
{
bytesReadCount=is.read(tmp);
if(bytesReadCount==-1)continue;
for(k=0;k<bytesReadCount;k++)
{
response[i]=tmp[k];
i++;
}
j=j+bytesReadCount;
}
ack[0]=1;
os.write(ack,0,1);
os.flush();

socket.close();
ByteArrayInputStream bais=new ByteArrayInputStream(response);
ObjectInputStream ois=new ObjectInputStream(bais);
String responseString=(String)ois.readObject(); //Object Deserialized
System.out.println("Response is: "+responseString);

}catch(Exception e)
{
System.out.println(e);
}
}
}