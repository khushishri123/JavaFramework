package com.thinking.machines.nframework.client;
import com.thinking.machines.nframework.common.*;
import com.thinking.machines.nframework.common.exceptions.*;
import java.net.*;
import java.io.*;
import java.nio.charset.*;
public class NFrameworkClient
{
public Object execute(String servicePath,Object ...arguments) throws Throwable
{
try
{
Request request=new Request();
request.setServicePath(servicePath);
request.setArguments(arguments);
String requestJSONString=JSONUtil.toJSON(request);

byte objectBytes[];
objectBytes=requestJSONString.getBytes(StandardCharsets.UTF_8);
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
String responseJSONString=new String(response,StandardCharsets.UTF_8);
Response responseObject=JSONUtil.fromJSON(responseJSONString,Response.class);
if(responseObject.getSuccess())return responseObject.getResult();
else
{
throw responseObject.getException();
}
}catch(Exception e)
{
System.out.println(e);
}
return null;
}
}
 