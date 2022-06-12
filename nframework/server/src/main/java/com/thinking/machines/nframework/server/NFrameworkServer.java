package com.thinking.machines.nframework.server;
import com.thinking.machines.nframework.server.annotations.*;
import java.net.*;
import java.lang.reflect.*;
import java.util.*;
public class NFrameworkServer
{
private ServerSocket serverSocket;
private Set<Class> tcpNetworkServiceClasses;
private Map<String,TCPService> services;
public NFrameworkServer()
{
this.tcpNetworkServiceClasses=new HashSet<>();
this.services=new HashMap<>();
}
public void registerClass(Class c)
{
Method methods[];
TCPService tcpService=null;
Path pathOnClass;
Path pathOnMethod;
int i;
int methodWithPathAnnotationCount=0;
String fullPath;
pathOnClass=(Path)c.getAnnotation(Path.class);
if(pathOnClass==null)return;
methods=c.getMethods();
for(i=0;i<methods.length;i++)
{
pathOnMethod=(Path)methods[i].getAnnotation(Path.class);
if(pathOnMethod==null)continue;
methodWithPathAnnotationCount++;
fullPath=pathOnClass.value()+pathOnMethod.value();
tcpService=new TCPService();
tcpService.c=c;
tcpService.method=methods[i];
tcpService.path=fullPath;
services.put(fullPath,tcpService);
}
if(methodWithPathAnnotationCount>0)
{
tcpNetworkServiceClasses.add(c);
}
}
public TCPService getTCPService(String path)
{
if(services.containsKey(path))
{
return services.get(path);
}
else
{
return null;
}
}
public void start()
{
try
{
serverSocket=new ServerSocket(5500);
Socket socket;
while(true)
{
socket=serverSocket.accept();
RequestProcessor requestProcessor=new RequestProcessor(this,socket);
}
}catch(Exception e)
{
}
}
}