package com.thinking.machines.hr.dl.dao;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dto.*;
import java.util.*;
import java.io.*;
public class StudentDAO implements StudentDAOInterface
{
public final static String FILE_NAME="students.data"; 
public void add(StudentDTOInterface studentDTO)throws DAOException
{
if(studentDTO==null)throw new DAOException("Student is Null");
String name=studentDTO.getName();
if(name==null)throw new DAOException("Name is Null");
name=name.trim();
if(name.length()==0)throw new DAOException("Length of Name is zero");
String course=studentDTO.getCourse();
if(course==null)throw new DAOException("Course is null");
course=course.trim();
if(course.length()==0)throw new DAOException("Length of course is zero");
try
{
File file=new File(FILE_NAME);
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
int lastGeneratedCode=0;
int recordCount=0;
String lastGeneratedCodeString="";
String recordCountString="";
if(randomAccessFile.length()==0)
{
lastGeneratedCodeString=String.format("%-10d",lastGeneratedCode);
recordCountString=String.format("%-10d",recordCount);
randomAccessFile.writeBytes(lastGeneratedCodeString);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(recordCountString);
randomAccessFile.writeBytes("\n");
}
else
{
lastGeneratedCodeString=randomAccessFile.readLine().trim();
recordCountString=randomAccessFile.readLine().trim();
lastGeneratedCode=Integer.parseInt(lastGeneratedCodeString);
recordCount=Integer.parseInt(recordCountString);
}
String fName="";
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
randomAccessFile.readLine();
fName=randomAccessFile.readLine();
if(fName.equalsIgnoreCase(name))
{
throw new DAOException("Name: "+name+" already exists.");
}
randomAccessFile.readLine();
}
int rollNumber=lastGeneratedCode+1;
randomAccessFile.writeBytes(rollNumber+"\n");
randomAccessFile.writeBytes(name+"\n");
randomAccessFile.writeBytes(course+"\n");
studentDTO.setRollNumber(rollNumber);
lastGeneratedCode++;
recordCount++;
lastGeneratedCodeString=String.format("%-10d",lastGeneratedCode);
recordCountString=String.format("%-10d",recordCount);
randomAccessFile.seek(0);
randomAccessFile.writeBytes(lastGeneratedCodeString);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(recordCountString);
randomAccessFile.writeBytes("\n");
randomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public void update(StudentDTOInterface studentDTO)throws DAOException
{
if(studentDTO==null)throw new DAOException("Student is null");
int rollNumber=studentDTO.getRollNumber();
if(rollNumber<=0)throw new DAOException("Invalid Roll Number: "+rollNumber);
String name=studentDTO.getName();
if(name==null)throw new DAOException("Name is null");
name=name.trim();
if(name.length()==0)throw new DAOException("Length of name is zero");
String course=studentDTO.getCourse();
if(course==null)throw new DAOException("Course is null");
course=course.trim();
if(course.length()==0)throw new DAOException("Length of course is zero");
try
{
File file=new File(FILE_NAME);
if(file.exists()==false)throw new DAOException("Invalid Roll Number: "+rollNumber);
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid Roll Number: "+rollNumber);
}
randomAccessFile.readLine();
randomAccessFile.readLine();
int fRollNumber=0;
String fName="";
boolean rollNumberFound=false;
boolean nameFound=false;
int nameFoundAgainstRollNumber=0;
long foundAt=0;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
if(rollNumberFound==false)foundAt=randomAccessFile.getFilePointer();
fRollNumber=Integer.parseInt(randomAccessFile.readLine());
fName=randomAccessFile.readLine();
randomAccessFile.readLine();
if(rollNumberFound==false && rollNumber==fRollNumber)rollNumberFound=true;
if(fName.equalsIgnoreCase(name))
{
nameFound=true;
nameFoundAgainstRollNumber=fRollNumber;
}
if(rollNumberFound && nameFound)break;
}
if(rollNumberFound==false)
{
randomAccessFile.close();
throw new DAOException("Invalid Roll Number: "+rollNumber);
}
if(nameFound && nameFoundAgainstRollNumber!=rollNumber)//Name mila per kisee aur ke against mila
{
randomAccessFile.close();
throw new DAOException("Name already existed.");
}
randomAccessFile.seek(foundAt);
for(int x=0;x<3;x++)randomAccessFile.readLine();
File tmpFile=new File("tmp.tmp");
if(tmpFile.exists())tmpFile.delete();
RandomAccessFile tmpRandomAccessFile;
tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
}
randomAccessFile.seek(foundAt);
randomAccessFile.writeBytes(rollNumber+"\n");
randomAccessFile.writeBytes(name+"\n");
randomAccessFile.writeBytes(course+"\n");
tmpRandomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
}
randomAccessFile.setLength(randomAccessFile.getFilePointer());
randomAccessFile.close();
tmpRandomAccessFile.setLength(0);
tmpRandomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public void delete(int rollNumber)throws DAOException
{
if(rollNumber<=0)throw new DAOException("Invalid Roll Number: "+rollNumber);
try
{
File file=new File(FILE_NAME);
if(file.exists()==false)throw new DAOException("Invalid Roll Number: "+rollNumber);
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid Roll Number: "+rollNumber);
}
randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
int fRollNumber=0;
boolean rollNumberFound=false;
long foundAt=0;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
foundAt=randomAccessFile.getFilePointer();
fRollNumber=Integer.parseInt(randomAccessFile.readLine());
randomAccessFile.readLine();
randomAccessFile.readLine();
if(rollNumberFound==false && rollNumber==fRollNumber)
{
rollNumberFound=true;
break;
}
}
if(rollNumberFound==false)
{
randomAccessFile.close();
throw new DAOException("Invalid Roll Number: "+rollNumber);
}
File tmpFile=new File("tmp.tmp");
if(tmpFile.exists())tmpFile.delete();
RandomAccessFile tmpRandomAccessFile;
tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
}
randomAccessFile.seek(foundAt);
tmpRandomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
}
randomAccessFile.setLength(randomAccessFile.getFilePointer());
recordCount--;
String recordCountString=String.format("%-10d",recordCount);
randomAccessFile.seek(0);
randomAccessFile.readLine();
randomAccessFile.writeBytes(recordCountString+"\n");
randomAccessFile.close();
tmpRandomAccessFile.setLength(0);
tmpRandomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public Set<StudentDTOInterface> getAll()throws DAOException
{
Set<StudentDTOInterface>students=new TreeSet<>();
try
{
File file=new File(FILE_NAME);
if(file.exists()==false)return students;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return students;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
StudentDTOInterface studentDTO;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
studentDTO=new StudentDTO();
studentDTO.setRollNumber(Integer.parseInt(randomAccessFile.readLine()));
studentDTO.setName(randomAccessFile.readLine());
studentDTO.setCourse(randomAccessFile.readLine());
students.add(studentDTO);
}
randomAccessFile.close();
return students;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public StudentDTOInterface getByRollNumber(int rollNumber)throws DAOException
{
if(rollNumber<=0)throw new DAOException("Invalid Roll Number:"+rollNumber);
try
{
File file=new File(FILE_NAME);
if(file.exists()==false)throw new DAOException("Invalid Roll Number:"+rollNumber);
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid Roll Number:"+rollNumber);
}
int fRollNumber=0;
String fName="";
String fCourse="";
StudentDTOInterface studentDTO;
randomAccessFile.readLine();
randomAccessFile.readLine();
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fRollNumber=Integer.parseInt(randomAccessFile.readLine());
fName=randomAccessFile.readLine();
fCourse=randomAccessFile.readLine();
if(fRollNumber==rollNumber)
{
studentDTO=new StudentDTO();
studentDTO.setRollNumber(fRollNumber);
studentDTO.setName(fName);
studentDTO.setCourse(fCourse);
return studentDTO;
}
}
randomAccessFile.close();
throw new DAOException("Invalid Roll Number: "+rollNumber);
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public StudentDTOInterface getByName(String name)throws DAOException
{
if(name==null)throw new DAOException("Invalid Name: "+name);
name=name.trim();
if(name.length()==0)throw new DAOException("Invalid Name: "+name);
try
{
File file=new File(FILE_NAME);
if(file.exists()==false)throw new DAOException("Invalid Name: "+name);
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid Name: "+name);
}
randomAccessFile.readLine();
randomAccessFile.readLine();
int fRollNumber=0;
String fName="";
String fCourse="";
StudentDTOInterface studentDTO;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fRollNumber=Integer.parseInt(randomAccessFile.readLine());
fName=randomAccessFile.readLine();
fCourse=randomAccessFile.readLine();
if(fName.equalsIgnoreCase(name))
{
studentDTO=new StudentDTO();
studentDTO.setRollNumber(fRollNumber);
studentDTO.setName(fName);
studentDTO.setCourse(fCourse);
randomAccessFile.close();
return studentDTO;
}
}
randomAccessFile.close();
throw new DAOException("Invalid Name: "+name);
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public Set<StudentDTOInterface> getByCourse(String course)throws DAOException
{
Set<StudentDTOInterface>students=new TreeSet<>();
if(course==null)return students;
course=course.trim();
if(course.length()==0)return students;
try
{
File file=new File(FILE_NAME);
if(file.exists()==false)return students;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return students;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
int fRollNumber=0;
String fName="";
String fCourse="";
StudentDTOInterface studentDTO;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fRollNumber=Integer.parseInt(randomAccessFile.readLine());
fName=randomAccessFile.readLine();
fCourse=randomAccessFile.readLine();
if(fCourse.equalsIgnoreCase(course))
{
studentDTO=new StudentDTO();
studentDTO.setRollNumber(fRollNumber);
studentDTO.setName(fName);
studentDTO.setCourse(fCourse);
students.add(studentDTO);
}
}
randomAccessFile.close();
return students;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public boolean isNameExists(String name)throws DAOException
{

if(name==null)return false;
name=name.trim();
if(name.length()==0)return false;
try
{
File file=new File(FILE_NAME);
if(file.exists()==false)return false;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
String fName="";
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
randomAccessFile.readLine();
fName=randomAccessFile.readLine();
randomAccessFile.readLine();
if(fName.equalsIgnoreCase(name))
{
randomAccessFile.close();
return true;
}
}
randomAccessFile.close();
return false;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}


}
public boolean isRollNumberExists(int rollNumber)throws DAOException
{
if(rollNumber<=0)return false;
try
{
File file=new File(FILE_NAME);
if(file.exists()==false)return false;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
int fRollNumber=0;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fRollNumber=Integer.parseInt(randomAccessFile.readLine());
if(fRollNumber==rollNumber)
{
randomAccessFile.close();
return true;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
}
randomAccessFile.close();
return false;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public int getCount()throws DAOException
{
try
{
File file=new File(FILE_NAME);
if(file.exists()==false)return 0;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return 0;
}
randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
randomAccessFile.close();
return recordCount;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
}