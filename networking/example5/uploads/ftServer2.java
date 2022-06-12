//Made by Sir
import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
class RequestProcessor extends Thread
{
private Socket socket;
private String id;
private FTServerFrame fsf;
RequestProcessor(Socket socket,String id,FTServerFrame fsf)
{
this.fsf=fsf;
this.id=id;
this.socket=socket;
start();
}
public void run()
{
try
{
SwingUtilities.invokeLater(new Runnable(){
public void run()
{
fsf.updateLog("Client Connected and id alloted is: "+id);
}
});
InputStream is=socket.getInputStream();
OutputStream os=socket.getOutputStream();
byte header[]=new byte[1024];
byte tmp[]=new byte[1024];
int i,x,k,j;
int bytesReadCount;
x=0;
i=0;
while(x<1024)
{
bytesReadCount=is.read(tmp);
if(bytesReadCount==-1)continue;
for(k=0;k<bytesReadCount;k++)
{
header[i]=tmp[k];
i++;
}
x+=bytesReadCount;
}

long lengthOfFile=0;
i=0;
j=1;
while(header[i]!=',')
{
lengthOfFile=lengthOfFile+(header[i]*j);
j=j*10;
i++;
}
i++;

StringBuffer sb=new StringBuffer();
while(i<=1023)
{
sb.append((char)header[i]);
i++;
}
long lof=lengthOfFile;
String fileName=sb.toString().trim();
SwingUtilities.invokeLater(()->{
fsf.updateLog("Recieving File :"+fileName+" of length : "+lof);
});

File file=new File("uploads"+File.separator+fileName);
if(file.exists()) file.delete();

//After recv. header ,sending ack
byte ack[]=new byte[1];
ack[0]=1;
os.write(ack,0,1);
os.flush();

//For writing in the file ,we create object of FileOutputStream
FileOutputStream fos=new FileOutputStream(file);
int chunkSize=4096;
byte bytes[]=new byte[chunkSize];
i=0;
long m=0;
while(m<lengthOfFile)
{
bytesReadCount=is.read(bytes);
if(bytesReadCount==-1)continue;
fos.write(bytes,0,bytesReadCount);
fos.flush();
m+=bytesReadCount;
}
fos.close();

ack[0]=1;
os.write(ack,0,1);
os.flush();
socket.close();
SwingUtilities.invokeLater(()->{
fsf.updateLog("File Saved to: "+file.getAbsolutePath());
fsf.updateLog("Connection with Client whose id is: "+id+" closed");
});
}catch(Exception e)
{
System.out.println(e);
}
}
}
class FTServer extends Thread
{
private ServerSocket serverSocket;
private FTServerFrame fsf;
FTServer(FTServerFrame fsf)
{
this.fsf=fsf;
}
public void run()
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

public void shutDown()
{
try
{
serverSocket.close();
}catch(Exception exception)
{
System.out.println(exception.getMessage());//remove this after testing 
}
}

private void startListening()
{
try
{
Socket socket;
RequestProcessor requestProcessor;
while(true)
{
System.out.println("Server is ready to accept request on port 5500");
SwingUtilities.invokeLater(new Thread(){
public void run()
{
fsf.updateLog("Server is ready to accept request on port 5500");
}
});
socket=serverSocket.accept();//Server Socket goes in wait mode to accept request because of this line
requestProcessor=new RequestProcessor(socket,UUID.randomUUID().toString(),fsf);
}
}catch(Exception e)
{
System.out.println("Server stopped Listening");
System.out.println(e);
}
}
}
class FTServerFrame extends JFrame implements ActionListener
{
private Container container;
private JButton button;
private JTextArea jta;
private JScrollPane jsp;
private boolean serverState=false;
private FTServer server;
FTServerFrame()
{
container=getContentPane();
container.setLayout(new BorderLayout());
button=new JButton("Start");
jta=new JTextArea();
jsp=new JScrollPane(jta,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
container.add(jsp,BorderLayout.CENTER);
container.add(button,BorderLayout.SOUTH);
setLocation(100,100);
setSize(500,500);
setVisible(true);
button.addActionListener(this);
}
public void actionPerformed(ActionEvent ev)
{
if(serverState==false)
{
server=new FTServer(this);
server.start();
serverState=true;
button.setText("Stop");
}
else
{
server.shutDown();
serverState=false;
button.setText("Start");
jta.append("Server Stopped\n");
}
}
public void updateLog(String s)
{
jta.append(s+"\n");
}
public static void main(String gg[])
{
FTServerFrame fsf=new FTServerFrame();
}
}

private FTServerFrame fsf;
RequestProcessor(Socket socket,String id,FTServerFrame fsf)
{
this.fsf=fsf;
this.id=id;
this.socket=socket;
start();
}
public void run()
{
try
{
SwingUtilities.invokeLater(new Runnable(){
public void run()
{
fsf.updateLog("Client Connected and id alloted is: "+id);
}
});
InputStream is=socket.getInputStream();
OutputStream os=socket.getOutputStream();
byte header[]=new byte[1024];
byte tmp[]=new byte[1024];
int i,x,k,j;
int bytesReadCount;
x=0;
i=0;
while(x<1024)
{
bytesReadCount=is.read(tmp);
if(bytesReadCount==-1)continue;
for(k=0;k<bytesReadCount;k++)
{
header[i]=tmp[k];
i++;
}
x+=bytesReadCount;
}

long lengthOfFile=0;
i=0;
j=1;
while(header[i]!=',')
{
lengthOfFile=lengthOfFile+(header[i]*j);
j=j*10;
i++;
}
i++;

StringBuffer sb=new StringBuffer();
while(i<=1023)
{
sb.append((char)header[i]);
i++;
}
long lof=lengthOfFile;
String fileName=sb.toString().trim();
SwingUtilities.invokeLater(()->{
fsf.updateLog("Recieving File :"+fileName+" of length : "+lof);
});

File file=new File("uploads"+File.separator+fileName);
if(file.exists()) file.delete();

//After recv. header ,sending ack
byte ack[]=new byte[1];
ack[0]=1;
os.write(ack,0,1);
os.flush();

//For writing in the file ,we create object of FileOutputStream
FileOutputStream fos=new FileOutputStream(file);
int chunkSize=4096;
byte bytes[]=new byte[chunkSize];
i=0;
long m=0;
while(m<lengthOfFile)
{
bytesReadCount=is.read(bytes);
if(bytesReadCount==-1)continue;
fos.write(bytes,0,bytesReadCount);
fos.flush();
m+=bytesReadCount;
}
fos.close();

ack[0]=1;
os.write(ack,0,1);
os.flush();
socket.close();
SwingUtilities.invokeLater(()->{
fsf.updateLog("File Saved to: "+file.getAbsolutePath());
fsf.updateLog("Connection with Client whose id is: "+id+" closed");
});
}catch(Exception e)
{
System.out.println(e);
}
}
}
class FTServer extends Thread
{
private ServerSocket serverSocket;
private FTServerFrame fsf;
FTServer(FTServerFrame fsf)
{
this.fsf=fsf;
}
public void run()
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

public void shutDown()
{
try
{
serverSocket.close();
}catch(Exception exception)
{
System.out.println(exception.getMessage());//remove this after testing 
}
}

private void startListening()
{
try
{
Socket socket;
RequestProcessor requestProcessor;
while(true)
{
System.out.println("Server is ready to accept request on port 5500");
SwingUtilities.invokeLater(new Thread(){
public void run()
{
fsf.updateLog("Server is ready to accept request on port 5500");
}
});
socket=serverSocket.accept();//Server Socket goes in wait mode to accept request because of this line
requestProcessor=new RequestProcessor(socket,UUID.randomUUID().toString(),fsf);
}
}catch(Exception e)
{
System.out.println("Server stopped Listening");
System.out.println(e);
}
}
}
class FTServerFrame extends JFrame implements ActionListener
{
private Container container;
private JButton button;
private JTextArea jta;
private JScrollPane jsp;
private boolean serverState=false;
private FTServer server;
FTServerFrame()
{
container=getContentPane();
container.setLayout(new BorderLayout());
button=new JButton("Start");
jta=new JTextArea();
jsp=new JScrollPane(jta,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
container.add(jsp,BorderLayout.CENTER);
container.add(button,BorderLayout.SOUTH);
setLocation(100,100);
setSize(500,500);
setVisible(true);
button.addActionListener(this);
}
public void actionPerformed(ActionEvent ev)
{
if(serverState==false)
{
server=new FTServer(this);
server.start();
serverState=true;
button.setText("Stop");
}
else
{
server.shutDown();
ser