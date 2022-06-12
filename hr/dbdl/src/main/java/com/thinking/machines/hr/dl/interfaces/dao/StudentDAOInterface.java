package com.thinking.machines.hr.dl.interfaces.dao;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dto.*;
import java.util.*;
public interface StudentDAOInterface
{
public void add(StudentDTOInterface studentDTO)throws DAOException;
public void update(StudentDTOInterface studentDTO)throws DAOException;
public void delete(int rollNumber)throws DAOException;
public Set<StudentDTOInterface> getAll()throws DAOException;
public StudentDTOInterface getByRollNumber(int rollNumber)throws DAOException;
public StudentDTOInterface getByName(String name)throws DAOException;
public Set<StudentDTOInterface> getByCourse(String course)throws DAOException;
public boolean isRollNumberExists(int rollNumber)throws DAOException;
public boolean isNameExists(String name)throws DAOException;
public int getCount()throws DAOException;
}