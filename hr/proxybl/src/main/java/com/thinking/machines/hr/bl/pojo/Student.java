package com.thinking.machines.hr.bl.pojo;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
public class Student implements StudentInterface
{
private int rollNumber;
private String name;
private String course;
public Student()
{
this.rollNumber=0;
this.name="";
this.course="";
}
public void setRollNumber( int rollNumber )
{
this.rollNumber=rollNumber;
}
public int getRollNumber()
{
return this.rollNumber;
}
public void setName( java.lang.String name )
{
this.name=name;
}
public java.lang.String getName()
{
return this.name;
}
public void setCourse( java.lang.String course )
{
this.course=course;
}
public java.lang.String getCourse()
{
return this.course;
}
public boolean equals(Object other)
{
if(!(other instanceof StudentInterface))return false;
StudentInterface student;
student=(StudentInterface)other;
return this.rollNumber==student.getRollNumber();
}
public int compareTo(StudentInterface studentDTO)
{
return this.rollNumber-studentDTO.getRollNumber();
}
public int hashCode()
{
return this.rollNumber;
}
}