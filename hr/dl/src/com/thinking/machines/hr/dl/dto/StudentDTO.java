package com.thinking.machines.hr.dl.dto;
import com.thinking.machines.hr.dl.interfaces.dto.*;
public class StudentDTO implements StudentDTOInterface
{
private int rollNumber;
private String name;
private String course;
public StudentDTO()
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
if(!(other instanceof StudentDTOInterface))return false;
StudentDTOInterface studentDTO;
studentDTO=(StudentDTO)other;
return this.rollNumber==studentDTO.getRollNumber();
}
public int compareTo(StudentDTOInterface studentDTO)
{
return this.rollNumber-studentDTO.getRollNumber();
}
public int hashCode()
{
return this.rollNumber;
}
}