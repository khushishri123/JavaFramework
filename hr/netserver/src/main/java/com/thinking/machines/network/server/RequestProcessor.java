package com.thinking.machines.network.server;
import com.thinking.machines.network.common.*;
import com.thinking.machines.network.common.exceptions.*;
import java.net.*;
import java.io.*;
class RequestProcessor extends Thread
{
private RequestHandlerInterface requestHandler;
private Socket socket;
public RequestProcessor(Socket socket,RequestHandlerInterface requestHandler)throws NetworkException
{
this.socket=socket;
this.requestHandler=requestHandler;
start();
}
public void run()
{
try
{
//Recieving Header
InputStream is=socket.getInputStream();
byte tmp[]=new byte[1024];
byte header[]=new byte[1024];
int i,k,j;
int bytesToRecieve=1024;
int bytesReadCount;
i=0;
k=0;
j=0;
while(j<bytesToRecieve)
{
bytesReadCount=is.read(tmp);
if(bytesReadCount==-1)continue;
for(k=0;k<bytesReadCount;k++)
{
header[i]=tmp[k];
i++;
}
j=j+bytesReadCount;
}

//Finding Request Length
int requestLength=0;
i=1;
j=1023;
while(j>=0)
{
requestLength=requestLength+(header[j]*i);
i=i*10;
j--;
}

//Sending ack
byte ack[]=new byte[1];
OutputStream os=socket.getOutputStream();
os.write(ack,0,1);
os.flush();

byte requestBytes[]=new byte[requestLength];
bytesToRecieve=requestLength;
j=0;
i=0;
while(j<bytesToRecieve)
{
bytesReadCount=is.read(tmp);
if(bytesReadCount==-1)continue;
for(k=0;k<bytesReadCount;k++)
{
requestBytes[i]=tmp[k];
i++;
}
j=j+bytesReadCount;
}


//Deserializing Object
ByteArrayInputStream bais=new ByteArrayInputStream(requestBytes);
ObjectInputStream ois=new ObjectInputStream(bais);
Request request=(Request)ois.readObject();

//calling process method of RequestHandler
Response response=requestHandler.process(request);

//Serializing the responseString
ByteArrayOutputStream baos=new ByteArrayOutputStream();
ObjectOutputStream oos=new ObjectOutputStream(baos);
oos.writeObject(response);
oos.flush();
byte objectBytes[];
objectBytes=baos.toByteArray();

//Preparing Header
int responseLength=objectBytes.length;
int x=responseLength;
i=1023;
header=new byte[1024]; //Purana Header use karenge to problem hogi
while(x>0)
{
j=x%10;
header[i]=(byte)j;
x=x/10;
i--;
}

//Sending header
os.write(header,0,1024);
os.flush();

//Recieving ack
while(true)
{
bytesReadCount=is.read(ack);
if(bytesReadCount==-1)continue;
break;
}

//Sending response;
int bytesToSend=responseLength;
int chunkSize=1024;
j=0;
while(j<bytesToSend)
{
if((bytesToSend-j)<chunkSize)chunkSize=bytesToSend-j;
os.write(objectBytes,j,chunkSize);
os.flush();
j=j+chunkSize;
}

//Again Recieving ack
while(true)
{
bytesReadCount=is.read(ack);
if(bytesReadCount==-1)continue;
break;
}

socket.close();
}catch(Exception e)
{
System.out.println(e);
}
}
}