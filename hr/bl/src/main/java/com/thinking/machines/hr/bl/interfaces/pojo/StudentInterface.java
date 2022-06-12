package com.thinking.machines.hr.bl.interfaces.pojo;
public interface StudentInterface extends Comparable<StudentInterface>,java.io.Serializable
{
public void setRollNumber(int rollNumber);
public int getRollNumber();
public void setName(String name);
public String getName();
public void setCourse(String course);
public String getCourse();
}