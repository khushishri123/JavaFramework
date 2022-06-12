import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
class FileUploadEvent
{
private String uploaderId;
private File file;
private long numberOfBytesUploaded;
public void setUploaderId(String uploaderId)
{
this.uploaderId=uploaderId;
}
public String getUploaderId()
{
return this.uploaderId;
}
public void setFile(File file)
{
this.file=file;
}
public File getFile()
{
return this.file;
}
public void setNumberOfBytesUploaded(long numberOfBytesUploaded)
{
this.numberOfBytesUploaded=numberOfBytesUploaded;
}
public long getNumberOfBytesUploaded()
{
return this.numberOfBytesUploaded;
}
}
interface FileUploadListener
{
public void fileUploadStatusChanged(FileUploadEvent fileUploadEvent);
}
class FileModel extends AbstractTableModel
{
private ArrayList<File> files;
FileModel()
{
this.files=new ArrayList<>();
}
public int getRowCount()
{
return this.files.size();
}
public int getColumnCount()
{
return 2;
}
public String getColumnName(int c)
{
if(c==0)return "S.No.";
return "Files";
}
public Object getValueAt(int r,int c)
{
if(c==0)return (r+1);
return this.files.get(r).getAbsolutePath();
}
public boolean isCellEditable(int r,int c)
{
return false;
}
public Class getColumnClass(int c)
{
if(c==0)return Integer.class;
return String.class;
}
public void add(File file)
{
this.files.add(file);
fireTableDataChanged();
}
public ArrayList<File> getFiles()
{
return this.files;
}
public void clearList()
{
this.files.clear();
fireTableDataChanged();
}
}

class FTClientFrame extends JFrame 
{
private String host;
private int portNumber;
private Container container;
private FileSelectionPanel fileSelectionPanel;
private FileUploadViewPanel fileUploadViewPanel;
FTClientFrame(String host,int portNumber)
{
this.host=host;
this.portNumber=portNumber;
fileSelectionPanel=new FileSelectionPanel();
fileUploadViewPanel=new FileUploadViewPanel();
container=getContentPane();
container.setLayout(new GridLayout(1,2));
container.add(fileSelectionPanel);
container.add(fileUploadViewPanel);
setLocation(30,30);
setSize(800,600);
setVisible(true);
}
class FileSelectionPanel extends JPanel implements ActionListener
{
private JLabel titleLabel;
private FileModel model;
private JTable table;
private JScrollPane jsp;
private JButton addFileButton;
FileSelectionPanel()
{
titleLabel=new JLabel("Selected Files");
model=new FileModel();
table=new JTable(model);
JTableHeader header=table.getTableHeader();
table.getColumnModel().getColumn(0).setPreferredWidth(10);
table.getColumnModel().getColumn(1).setPreferredWidth(400);
header.setReorderingAllowed(false);
header.setResizingAllowed(false);
jsp=new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
addFileButton=new JButton("Add File");
setLayout(new BorderLayout());
add(titleLabel,BorderLayout.NORTH);
add(jsp,BorderLayout.CENTER);
add(addFileButton,BorderLayout.SOUTH);
addFileButton.addActionListener(this);
}
public void actionPerformed(ActionEvent ev)
{
JFileChooser jfc=new JFileChooser();
jfc.setCurrentDirectory(new File("."));
int selectedOption=jfc.showOpenDialog(this);
if(selectedOption==jfc.APPROVE_OPTION)
{
File selectedFile=jfc.getSelectedFile();
model.add(selectedFile);
}
}
public ArrayList<File> getFiles()
{
return model.getFiles();
}
public void clearList()
{
this.model.clearList();
}
}//inner class ends

class FileUploadViewPanel extends JPanel implements ActionListener,FileUploadListener
{
private JButton uploadFilesButton;
private JPanel progressPanelsContainer;
private ArrayList<ProgressPanel> progressPanels;
private JScrollPane jsp;
ArrayList<File> files;
ArrayList<FileUploadThread> fileUploaders;
int count=0;
FileUploadViewPanel()
{
uploadFilesButton=new JButton("Upload File");
setLayout(new BorderLayout());
add(uploadFilesButton,BorderLayout.NORTH);
uploadFilesButton.addActionListener(this);
}
public void actionPerformed(ActionEvent ev)
{
files=fileSelectionPanel.getFiles();
count=files.size();
if(files.size()==0)
{
JOptionPane.showMessageDialog(FTClientFrame.this,"No Files selected for uploading");
return;
}
progressPanelsContainer=new JPanel();
progressPanelsContainer.setLayout(new GridLayout(files.size(),1));
progressPanels=new ArrayList<>();
ProgressPanel progressPanel;
fileUploaders=new ArrayList<>();
FileUploadThread fut;
String uploaderId;
for(File file:files)
{
uploaderId=UUID.randomUUID().toString();
progressPanel=new ProgressPanel(uploaderId,file);
progressPanels.add(progressPanel);
progressPanelsContainer.add(progressPanel);
fut=new FileUploadThread(this,uploaderId,file,host,portNumber);
fileUploaders.add(fut);
}
this.jsp=new JScrollPane(progressPanelsContainer,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
add(this.jsp,BorderLayout.CENTER);
this.revalidate();
this.repaint();
for(FileUploadThread fileUploadThread:fileUploaders)
{
fileUploadThread.start();
}
}

public void fileUploadStatusChanged(FileUploadEvent fileUploadEvent)
{
String uploaderId=fileUploadEvent.getUploaderId();
long numberOfBytesUploaded=fileUploadEvent.getNumberOfBytesUploaded();
File file=fileUploadEvent.getFile();
for(ProgressPanel progressPanel:progressPanels)
{
if(progressPanel.getId().equals(uploaderId))
{
progressPanel.updateProgressBar(numberOfBytesUploaded);
break;
}
}
}

class ProgressPanel extends JPanel
{
private JLabel fileNameLabel;
private JProgressBar progressBar;
private File file;
private long fileLength;
private String id;
private FileUploadViewPanel fuvp;
public ProgressPanel(String id,File file)
{
this.id=id;
this.file=file;
this.fuvp=new FileUploadViewPanel();
fileNameLabel=new JLabel("Uploading: "+file.getAbsolutePath());
progressBar=new JProgressBar(1,100);
this.fileLength=file.length();
setLayout(new GridLayout(2,1));
add(fileNameLabel);
add(progressBar);
}
public String getId()
{
return this.id;
}
public void updateProgressBar(long bytesUploaded)
{
int percentage;
if(bytesUploaded==fileLength) percentage=100;
else percentage=(int)((bytesUploaded*100)/fileLength);
progressBar.setValue(percentage);
if(percentage==100)
{
fileNameLabel.setText("Upload: "+file.getAbsolutePath());
count--;
}
if(count==0)
{
JOptionPane.showMessageDialog(FTClientFrame.this,"All Files Uploaded");
fileSelectionPanel.clearList();
fuvp.removeAll();
fuvp.updateUI();
}
}

}

}//inner class ends
public static void main(String gg[])
{
FTClientFrame fcf=new FTClientFrame("localhost",5500);
}
}

class FileUploadThread extends Thread
{
private String id;
private File file;
private String host;
private int portNumber;
private FileUploadListener fileUploadListener;
FileUploadThread(FileUploadListener fileUploadListener,String id,File file,String host,int portNumber)
{
this.fileUploadListener=fileUploadListener;
this.id=id;
this.file=file;
this.portNumber=portNumber;
this.host=host;
}
public void run()
{
try
{
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
Socket socket=new Socket(host,portNumber);
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
long brc=j;
SwingUtilities.invokeLater(()->{
FileUploadEvent fue=new FileUploadEvent();
fue.setUploaderId(id);
fue.setFile(file);
fue.setNumberOfBytesUploaded(brc);
fileUploadListener.fileUploadStatusChanged(fue);
});
}
fis.close();
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
}rs=new ArrayList<>();
FileUploadThread fut;
String uploaderId;
for(File file:files)
{
uploaderId=UUID.randomUUID().toString();
progressPanel=new ProgressPanel(uploaderId,file);
progressPanels.add(progressPanel);
progressPanelsContainer.add(progressPanel);
fut=new FileUploadThread(this,uploaderId,file,host,portNumber);
fileUploaders.add(fut);
}
this.jsp=new JScrollPane(progressPanelsContainer,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
add(this.jsp,BorderLayout.CENTER);
this.revalidate();
this.repaint();
for(FileUploadThread fileUploadThread:fileUploaders)
{
fileUploadThread.start();
}
}

public void fileUploadStatusChanged(FileUploadEvent fileUploadEvent)
{
String uploaderId=fileUploadEvent.getUploaderId();
long numberOfBytesUploaded=fileUploadEvent.getNumberOfBytesUploaded();
File file=fileUploadEvent.getFile();
for(ProgressPanel progressPanel:progressPanels)
{
if(progressPanel.getId().equals(uploaderId))
{
progressPanel.updateProgressBar(numberOfBytesUploaded);
break;
}
}
}

class ProgressPanel extends JPanel
{
private JLabel fileNameLabel;
private JProgressBar progressBar;
private File file;
private long fileLength;
private String id;
private FileUploadViewPanel fuvp;
public ProgressPanel(String id,File file)
{
this.id=id;
this.file=file;
this.fuvp=new FileUploadViewPanel();
fileNameLabel=new JLabel("Uploading: "+file.getAbsolutePath());
progressBar=new JProgressBar(1,100);
this.fileLength=file.length();
setLayout(new GridLayout(2,1));
add(fileNameLabel);
add(progressBar);
}
public String getId()
{
return this.id;
}
public void updateProgressBar(long bytesUploaded)
{
int percentage;
if(bytesUploaded==fileLength) percentage=100;
else percentage=(int)((bytesUploaded*100)/fileLength);
progressBar.setValue(percentage);
if(percentage==100)
{
fileNameLabel.setText("Upload: "+file.getAbsolutePath());
count--;
}
if(count==0)
{
JOptionPane.showMessageDialog(FTClientFrame.this,"All Files Uploaded");
fileSelectionPanel.clearList();
fuvp.removeAll();
fuvp.updateUI();
}
}

}

}//inner class ends
public static void main(String gg[])
{
FTClientFrame fcf=new FTClientFrame("localhost",5500);
}
}

class FileUploadThread extends Thread
{
private String id;
private File file;
private String host;
private int portNumber;
private FileUploadListener fileUploadListener;
FileUploadThread(FileUploadListener fileUploadListener,String id,File file,String host,int portNumber)
{
this.fileUploadListener=fileUploadListener;
this.id=id;
this.file=file;
this.portNumber=portNumber;
this.host=host;
}
public void run()
{
try
{
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
Socket socket=new Socket(host,portNumber);
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
os