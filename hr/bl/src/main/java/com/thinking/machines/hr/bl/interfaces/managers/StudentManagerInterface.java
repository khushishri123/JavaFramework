package com.thinking.machines.hr.bl.interfaces.managers;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
public interface StudentManagerInterface
{
public void addStudent(StudentInterface student)throws BLException;
public void updateStudent(StudentInterface student)throws BLException;
public void removeStudent(int rollNumber)throws BLException;
public StudentInterface getStudentByRollNumber(int rollNumber)throws BLException;
public StudentInterface getStudentByName(String name)throws BLException;
public int getStudentCount()throws BLException;
public boolean studentRollNumberExists(int rollNumber)throws BLException;
public boolean studentNameExists(String name)throws BLException;
public Set<StudentInterface>getStudents()throws BLException;
public Set<StudentInterface>getStudentsByCourse(String course)throws BLException;
}