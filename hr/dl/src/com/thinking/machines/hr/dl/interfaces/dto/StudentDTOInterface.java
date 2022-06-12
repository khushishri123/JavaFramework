package com.thinking.machines.hr.dl.interfaces.dto;
public interface StudentDTOInterface extends Comparable<StudentDTOInterface>,java.io.Serializable
{
public void setRollNumber(int rollNumber);
public int getRollNumber();
public void setName(String name);
public String getName();
public void setCourse(String course);
public String getCourse();
}