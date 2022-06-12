package com.thinking.machines.nframework.server;
import java.net.*;
import com.thinking.machines.nframework.common.*;
import java.nio.charset.*;
import java.lang.reflect.*;
import java.io.*;
class RequestProcessor extends Thread
{
private NFrameworkServer server;
private Socket socket;
public RequestProcessor(NFrameworkServer server,Socket socket)
{
this.server=server;
this.socket=socket;
start();
}
public void run()
{
try
{
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

byte request[]=new byte[requestLength];
bytesToRecieve=requestLength;
j=0;
i=0;
while(j<bytesToRecieve)
{
bytesReadCount=is.read(tmp);
if(bytesReadCount==-1)continue;
for(k=0;k<bytesReadCount;k++)
{
request[i]=tmp[k];
i++;
}
j=j+bytesReadCount;
}

String requestJSONString=new String(request,StandardCharsets.UTF_8);
Request requestObject=JSONUtil.fromJSON(requestJSONString,Request.class);
// The request object contains servicePath and arguments
//We want the reference of TCPService that contains the  Class ref and 
//Method ref
String servicePath=requestObject.getServicePath();
TCPService tcpService=this.server.getTCPService(servicePath);
Response responseObject=new Response();
if(tcpService==null)
{
responseObject.setSuccess(false);
responseObject.setResult(null);
responseObject.setException(new RuntimeException("Invalid Path: "+servicePath));
}
else
{
try
{
Class c=tcpService.c;
Method method=tcpService.method;
Object serviceObject=c.newInstance();
Object result=method.invoke(serviceObject,requestObject.getArguments());
responseObject.setSuccess(true);
responseObject.setResult(result);
responseObject.setException(null);
}catch(InstantiationException instantiationException)
{
responseObject.setSuccess(false);
responseObject.setResult(null);
responseObject.setException(new RuntimeException("Unable to create object to service class associated with the path: "+servicePath));
}
catch(IllegalAccessException illegalAccessException)
{
responseObject.setSuccess(false);
responseObject.setResult(null);
responseObject.setException(new RuntimeException(""));
}
catch(InvocationTargetException invocationTargetException)
{
Throwable t=invocationTargetException.getCause();
responseObject.setSuccess(false);
responseObject.setResult(null);
responseObject.setException(t);
}
} 
//Preparing Response JSON String
String responseJSONString=JSONUtil.toJSON(responseObject);

byte objectBytes[];
objectBytes=responseJSONString.getBytes(StandardCharsets.UTF_8);


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
 

}catch(IOException e)
{
System.out.println(e);
}
}
}