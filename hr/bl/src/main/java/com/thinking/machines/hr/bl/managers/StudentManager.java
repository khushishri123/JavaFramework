package com.thinking.machines.hr.bl.managers;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.util.*;
public class StudentManager implements StudentManagerInterface 
{
private Map<Integer,StudentInterface>rollNumberWiseStudentsMap;
private Map<String,StudentInterface>nameWiseStudentsMap;
private Set<StudentInterface>studentsSet;
private Map<String,Set<StudentInterface>>courseWiseStudentsMap;
private static StudentManager studentManager=null;
private StudentManager()throws BLException
{
populateDataStructures();
}
private void populateDataStructures()throws BLException
{
this.rollNumberWiseStudentsMap=new HashMap<>();
this.nameWiseStudentsMap=new HashMap<>();
this.studentsSet=new TreeSet<>();
this.courseWiseStudentsMap=new HashMap<>();
try
{
Set<StudentDTOInterface> dlStudents;
dlStudents=new StudentDAO().getAll();
StudentInterface student;
for(StudentDTOInterface dlStudent:dlStudents)
{
student=new Student();
student.setRollNumber(dlStudent.getRollNumber());
student.setName(dlStudent.getName());
student.setCourse(dlStudent.getCourse());
rollNumberWiseStudentsMap.put(student.getRollNumber(),student);
nameWiseStudentsMap.put(student.getName().toUpperCase(),student);
studentsSet.add(student);
Set<StudentInterface>ss;
ss=this.courseWiseStudentsMap.get(student.getCourse().toUpperCase());
if(ss==null)
{
ss=new TreeSet<>();
ss.add(student);
this.courseWiseStudentsMap.put(student.getCourse().toUpperCase(),ss);
}
else
{
ss.add(student);
}
}
}catch(DAOException daoException)
{
BLException blException=new BLException();
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public static StudentManagerInterface getStudentManager()throws BLException
{
if(studentManager==null)studentManager=new StudentManager();
return studentManager;
}
public void addStudent(StudentInterface student)throws BLException
{
BLException blException;
blException=new BLException();
if(student==null)
{
blException.setGenericException("Student is NULLL");
throw blException;
}

int rollNumber=student.getRollNumber();
String name=student.getName();
String course=student.getCourse();

//Validations
if(rollNumber!=0)
{
blException.addException("rollNumber","Roll Number should be null");
throw blException;
}
if(name==null)
{
blException.addException("name","Name Required");
}
else
{
name=name.trim();
if(name.length()==0)
{
blException.addException("name","Length of Name is zero");
}
else
{
if(nameWiseStudentsMap.containsKey(name.toUpperCase()))
{
blException.addException("name","Name: "+name+" exists.");
}
}
}

if(course==null)
{
blException.addException("course","Course required");
}
else
{
course=course.trim();
if(course.length()==0)
{
blException.addException("course","Length of Course is zero");
}
}
if(blException.hasExceptions())
{
throw blException;
}
try
{
StudentDAOInterface studentDAO=new StudentDAO();
StudentDTOInterface studentDTO=new StudentDTO();
studentDTO.setName(name);
studentDTO.setCourse(course);
studentDAO.add(studentDTO);
student.setRollNumber(studentDTO.getRollNumber());
//Making clown

StudentInterface dsStudent;
dsStudent=new Student();
dsStudent.setRollNumber(studentDTO.getRollNumber());
dsStudent.setName(name);
dsStudent.setCourse(course);

//Adding to DS

this.rollNumberWiseStudentsMap.put(rollNumber,dsStudent);
this.nameWiseStudentsMap.put(name.toUpperCase(),dsStudent);
this.studentsSet.add(dsStudent);
Set<StudentInterface>ss;
ss=this.courseWiseStudentsMap.get(dsStudent.getCourse().toUpperCase());
if(ss==null)
{
ss=new TreeSet<>();
ss.add(dsStudent);
this.courseWiseStudentsMap.put(dsStudent.getCourse().toUpperCase(),ss);
}
else
{
ss.add(dsStudent);
}
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}

}
public void updateStudent(StudentInterface student)throws BLException
{


BLException blException;
blException=new BLException();
if(student==null)
{
blException.setGenericException("Student is NULLL");
throw blException;
}

int rollNumber=student.getRollNumber();
String name=student.getName();
String course=student.getCourse();

//Validations
if(rollNumber<=0)
{
blException.addException("rollNumber","Invalid Roll Number: "+rollNumber);
throw blException;
}
if(rollNumberWiseStudentsMap.containsKey(rollNumber)==false)
{
blException.addException("rollNumber","Invalid Roll Number: "+rollNumber);
throw blException;
}
if(name==null)
{
blException.addException("name","Name Required");
}
else
{
name=name.trim();
if(name.length()==0)
{
blException.addException("name","Length of Name is zero");
}
else
{
StudentInterface ss=nameWiseStudentsMap.get(name.toUpperCase());
if(ss!=null && ss.getRollNumber()!=rollNumber)
{
blException.addException("name","Name: "+name+" exists.");
}
}
}

if(course==null)
{
blException.addException("course","Course required");
}
else
{
course=course.trim();
if(course.length()==0)
{
blException.addException("course","Length of Course is zero");
}
}
if(blException.hasExceptions())
{
throw blException;
}
try
{
StudentInterface dsStudent;
dsStudent=rollNumberWiseStudentsMap.get(rollNumber);
String oldName=dsStudent.getName().toUpperCase();
String oldCourse=dsStudent.getCourse().toUpperCase();
StudentDAOInterface studentDAO=new StudentDAO();
StudentDTOInterface studentDTO=new StudentDTO();
studentDTO.setRollNumber(dsStudent.getRollNumber());
studentDTO.setName(name);
studentDTO.setCourse(course);
studentDAO.update(studentDTO);
//Making clown
dsStudent.setName(name);
dsStudent.setCourse(course);
//Remove old record from DS
studentsSet.remove(dsStudent);
rollNumberWiseStudentsMap.remove(rollNumber);
nameWiseStudentsMap.remove(oldName);
courseWiseStudentsMap.remove(oldCourse);
//Adding to DS
this.rollNumberWiseStudentsMap.put(dsStudent.getRollNumber(),dsStudent);
this.nameWiseStudentsMap.put(name.toUpperCase(),dsStudent);
this.studentsSet.add(dsStudent);
Set<StudentInterface>ss;
ss=this.courseWiseStudentsMap.get(course.toUpperCase());
if(ss==null)
{
ss=new TreeSet<>();
ss.add(dsStudent);
this.courseWiseStudentsMap.put(course.toUpperCase(),ss);
}
else
{
ss.add(dsStudent);
}
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public void removeStudent(int rollNumber)throws BLException
{

BLException blException;
blException=new BLException();

//Validations
if(rollNumberWiseStudentsMap.containsKey(rollNumber)==false)
{
blException.addException("rollNumber","Invalid Roll Number: "+rollNumber);
throw blException;
}
try
{
StudentInterface dsStudent;
dsStudent=rollNumberWiseStudentsMap.get(rollNumber);
String oldName=dsStudent.getName().toUpperCase();
String oldCourse=dsStudent.getCourse().toUpperCase();
StudentDAOInterface studentDAO=new StudentDAO();
studentDAO.delete(rollNumber);
//Remove old record from DS
studentsSet.remove(dsStudent);
rollNumberWiseStudentsMap.remove(rollNumber);
nameWiseStudentsMap.remove(oldName);
courseWiseStudentsMap.remove(oldCourse);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public StudentInterface getStudentByRollNumber(int rollNumber)throws BLException
{
StudentInterface student=new Student();
StudentInterface dsStudent=this.rollNumberWiseStudentsMap.get(rollNumber);
if(dsStudent==null)return student;
student.setRollNumber(dsStudent.getRollNumber());
student.setName(dsStudent.getName());
student.setCourse(dsStudent.getCourse());
return student;
}
public StudentInterface getStudentByName(String name)throws BLException
{

StudentInterface student=new Student();
StudentInterface dsStudent=this.nameWiseStudentsMap.get(name.toUpperCase());
if(dsStudent==null)return student;
student.setRollNumber(dsStudent.getRollNumber());
student.setName(dsStudent.getName());
student.setCourse(dsStudent.getCourse());
return student;
}
public int getStudentCount()throws BLException
{
return this.studentsSet.size();
}
public boolean studentRollNumberExists(int rollNumber)throws BLException
{

return this.rollNumberWiseStudentsMap.containsKey(rollNumber);

}
public boolean studentNameExists(String name)throws BLException
{
return this.nameWiseStudentsMap.containsKey(name.toUpperCase());
}
public Set<StudentInterface>getStudents()throws BLException
{
Set<StudentInterface>students=new TreeSet<>();
StudentInterface student;
for(StudentInterface dsStudent:studentsSet)
{
student=new Student();
student.setRollNumber(dsStudent.getRollNumber());
student.setName(dsStudent.getName());
student.setCourse(dsStudent.getCourse());
students.add(student);
}
return students;
}
public Set<StudentInterface>getStudentsByCourse(String course)throws BLException
{
Set<StudentInterface>students=new TreeSet<>();
Set<StudentInterface>ss=this.courseWiseStudentsMap.get(course.toUpperCase());
if(ss==null)return students;
StudentInterface student;
for(StudentInterface dsStudent:ss)
{
student=new Student();
student.setRollNumber(dsStudent.getRollNumber());
student.setName(dsStudent.getName());
student.setCourse(dsStudent.getCourse());
students.add(student);
}
return students;
}
}