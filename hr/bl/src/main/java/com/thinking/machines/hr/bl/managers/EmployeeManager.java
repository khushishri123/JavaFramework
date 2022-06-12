package com.thinking.machines.hr.bl.managers;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.enums.*;
import java.math.*;
import java.text.*;
public class EmployeeManager implements EmployeeManagerInterface
{
private Map<String,EmployeeInterface> employeeIdWiseEmployeesMap;
private Map<String,EmployeeInterface> panNumberWiseEmployeesMap;
private Map<String,EmployeeInterface> aadharCardNumberWiseEmployeesMap;
private Set<EmployeeInterface> employeesSet;
private Map<Integer,Set<EmployeeInterface>> designationCodeWiseEmployeesMap;
private static EmployeeManager employeeManager=null;
private EmployeeManager()throws BLException
{
this.populateDataStructures();
}
private void populateDataStructures()throws BLException
{
this.employeeIdWiseEmployeesMap=new HashMap<>();
this.panNumberWiseEmployeesMap=new HashMap<>();
this.aadharCardNumberWiseEmployeesMap=new HashMap<>();
this.employeesSet=new TreeSet<>();
this.designationCodeWiseEmployeesMap=new HashMap<>();
try
{
Set<EmployeeDTOInterface> dlEmployees;
dlEmployees=new EmployeeDAO().getAll();
EmployeeInterface employee;
DesignationInterface designation;
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
Set<EmployeeInterface> ets;
for(EmployeeDTOInterface dlEmployee:dlEmployees)
{
employee=new Employee();
employee.setEmployeeId(dlEmployee.getEmployeeId());
employee.setName(dlEmployee.getName());
designation=designationManager.getDesignationByCode(dlEmployee.getDesignationCode());
employee.setDesignation(designation);
employee.setDateOfBirth((Date)dlEmployee.getDateOfBirth().clone());
if(dlEmployee.getGender()=='M')
{
employee.setGender(GENDER.Male);
}
else
{
employee.setGender(GENDER.Female);
}
employee.setIsIndian(dlEmployee.getIsIndian());
employee.setBasicSalary(dlEmployee.getBasicSalary());
employee.setPANNumber(dlEmployee.getPANNumber());
employee.setAadharCardNumber(dlEmployee.getAadharCardNumber());
this.employeeIdWiseEmployeesMap.put(employee.getEmployeeId().toUpperCase(),employee);
this.panNumberWiseEmployeesMap.put(employee.getPANNumber().toUpperCase(),employee);
this.aadharCardNumberWiseEmployeesMap.put(employee.getAadharCardNumber().toUpperCase(),employee);
this.employeesSet.add(employee);
ets=this.designationCodeWiseEmployeesMap.get(designation.getCode());
if(ets==null)
{
ets=new TreeSet<>();
ets.add(employee);
designationCodeWiseEmployeesMap.put(new Integer(designation.getCode()),ets);
}
else
{
ets.add(employee);
}
}
}catch(DAOException daoException)
{
BLException blException;
blException=new BLException();
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public static EmployeeManagerInterface getEmployeeManager()throws BLException
{
if(employeeManager==null)employeeManager=new EmployeeManager();
return employeeManager;
}
public void addEmployee(EmployeeInterface employee)throws BLException
{
BLException blException;
blException=new BLException();

if(employee==null)
{
blException.setGenericException("Employee is Null");
throw blException;
}
String employeeId=employee.getEmployeeId(); 
if(employeeId!=null)
{
employeeId=employeeId.trim();
if(employeeId.length()>0)
{
blException.addException("employeeId","Employee Id should be nill/empty");
}
}
String name=employee.getName();
if(name==null)
{
blException.addException("name","Name is null");
name="";
}
else
{
name=name.trim();
if(name.length()==0)blException.addException("name","Length of name is zero");
}
DesignationInterface designation=employee.getDesignation();
int designationCode=0;
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
if(designation==null)blException.addException("designation","Designation is null");
else
{
designationCode=designation.getCode();
if(designationManager.designationCodeExists(designationCode)==false)
{
blException.addException("designation","Invalid Designation");
}
}
Date dateOfBirth=employee.getDateOfBirth();
if(dateOfBirth==null)
{
blException.addException("dateOfBirth","Date Of Birth is Null");
}
char gender=employee.getGender();
if(gender==' ')
{
blException.addException("gender","Gender Required");
}
boolean isIndian=employee.getIsIndian();
BigDecimal basicSalary=employee.getBasicSalary();
if(basicSalary==null)
{
blException.addException("basicSalary","Basic Salary is null");
}
if(basicSalary.signum()==-1)
{
blException.addException("basicSalary","Basic Salary is negative");
}
String panNumber=employee.getPANNumber();
if(panNumber==null)
{
blException.addException("panNumber","Pan Number is null");
}
else
{
panNumber=panNumber.trim();
if(panNumber.length()==0)
{
blException.addException("panNumber","Length of Pan Number is zero");
}
}
String aadharCardNumber=employee.getAadharCardNumber();
if(aadharCardNumber==null)
{
blException.addException("aadharCardNumber","Aadhar Card Number is null");
}
else
{
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0)
{
blException.addException("aadharCardNumber","Length of Aadhar Card Number is zero");
}
}
if(panNumber!=null && panNumber.length()>0)
{
if(panNumberWiseEmployeesMap.containsKey(panNumber.toUpperCase()))
{
blException.addException("panNumber","PAN Number: "+panNumber+" exists.");
}
}
if(aadharCardNumber!=null && aadharCardNumber.length()>0)
{
if(aadharCardNumberWiseEmployeesMap.containsKey(aadharCardNumber.toUpperCase()))
{
blException.addException("aadharCardNumber","Aadhar Card Number: "+aadharCardNumber+" exists.");
}
}
if(blException.hasExceptions())
{
throw blException;
}
try
{
EmployeeDTOInterface employeeDTO;
employeeDTO=new EmployeeDTO();
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designation.getCode());
employeeDTO.setDateOfBirth((Date)dateOfBirth.clone());
employeeDTO.setGender((gender=='M')?GENDER.Male:GENDER.Female);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharCardNumber(aadharCardNumber);
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
employeeDAO.add(employeeDTO);
employee.setEmployeeId(employeeDTO.getEmployeeId());

//Making clown
EmployeeInterface dsEmployee;
dsEmployee=new Employee();
dsEmployee.setName(name);
dsEmployee.setDesignation(((DesignationManager)designationManager).getDSDesignationByCode(designation.getCode()));
dsEmployee.setDateOfBirth((Date)dateOfBirth.clone());
dsEmployee.setGender((gender=='M')?GENDER.Male:GENDER.Female);
dsEmployee.setIsIndian(isIndian);
dsEmployee.setBasicSalary(basicSalary);
dsEmployee.setPANNumber(panNumber);
dsEmployee.setAadharCardNumber(aadharCardNumber);
this.employeeIdWiseEmployeesMap.put(dsEmployee.getEmployeeId().toUpperCase(),dsEmployee);
this.panNumberWiseEmployeesMap.put(dsEmployee.getPANNumber().toUpperCase(),dsEmployee);
this.aadharCardNumberWiseEmployeesMap.put(dsEmployee.getAadharCardNumber().toUpperCase(),dsEmployee);
this.employeesSet.add(dsEmployee);
Set<EmployeeInterface>ets;
ets=this.designationCodeWiseEmployeesMap.get(dsEmployee.getDesignation().getCode());
if(ets==null)
{
ets=new TreeSet<>();
ets.add(dsEmployee);
this.designationCodeWiseEmployeesMap.put(new Integer(dsEmployee.getDesignation().getCode()),ets);
}
else
{
ets.add(dsEmployee);
}
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public void updateEmployee(EmployeeInterface employee)throws BLException
{

BLException blException;
blException=new BLException();

if(employee==null)
{
blException.setGenericException("Employee is Null");
throw blException;
}
String employeeId=employee.getEmployeeId(); 
if(employeeId==null)
{
blException.addException("employeeId","Employee Id required");
throw blException;
}
else
{
employeeId=employeeId.trim();
if(employeeId.length()==0)
{
blException.addException("employeeId","Employee Id required");
throw blException;
}
else
{
if(employeeIdWiseEmployeesMap.containsKey(employeeId.toUpperCase())==false)
{
blException.addException("employeeId","Invalid Employee Id: "+employeeId);
throw blException;
}
}
}
String name=employee.getName();
if(name==null)
{
blException.addException("name","Name is null");
name="";
}
else
{
name=name.trim();
if(name.length()==0)blException.addException("name","Length of name is zero");
}
DesignationInterface designation=employee.getDesignation();
int designationCode=0;
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
if(designation==null)blException.addException("designation","Designation is null");
else
{
designationCode=designation.getCode();
if(designationManager.designationCodeExists(designationCode)==false)
{
blException.addException("designation","Invalid Designation");
}
}
Date dateOfBirth=employee.getDateOfBirth();
if(dateOfBirth==null)
{
blException.addException("dateOfBirth","Date Of Birth is Null");
}
char gender=employee.getGender();
if(gender==' ')
{
blException.addException("gender","Gender Required");
}
boolean isIndian=employee.getIsIndian();
BigDecimal basicSalary=employee.getBasicSalary();
if(basicSalary==null)
{
blException.addException("basicSalary","Basic Salary is null");
}
if(basicSalary.signum()==-1)
{
blException.addException("basicSalary","Basic Salary is negative");
}
String panNumber=employee.getPANNumber();
if(panNumber==null)
{
blException.addException("panNumber","Pan Number is null");
}
else
{
panNumber=panNumber.trim();
if(panNumber.length()==0)
{
blException.addException("panNumber","Length of Pan Number is zero");
}
}
String aadharCardNumber=employee.getAadharCardNumber();
if(aadharCardNumber==null)
{
blException.addException("aadharCardNumber","Aadhar Card Number is null");
}
else
{
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0)
{
blException.addException("aadharCardNumber","Length of Aadhar Card Number is zero");
}
}
if(panNumber!=null && panNumber.length()>0)
{
EmployeeInterface ee=panNumberWiseEmployeesMap.get(panNumber.toUpperCase());
if(ee!=null && ee.getEmployeeId().equalsIgnoreCase(employeeId)==false)
{
blException.addException("panNumber","PAN Number "+panNumber+" exists.");
}
}

if(aadharCardNumber!=null && aadharCardNumber.length()>0)
{
EmployeeInterface ee=aadharCardNumberWiseEmployeesMap.get(aadharCardNumber.toUpperCase());
if(ee!=null && ee.getEmployeeId().equalsIgnoreCase(employeeId)==false)
{
blException.addException("aadharCardNumber","Aadhar Card Number "+aadharCardNumber+" exists.");
}
}


if(blException.hasExceptions())
{
throw blException;
}
try
{
EmployeeInterface dsEmployee;
dsEmployee=employeeIdWiseEmployeesMap.get(employeeId.toUpperCase());
String oldPANNumber=dsEmployee.getPANNumber().toUpperCase();
String oldAadharCardNumber=dsEmployee.getAadharCardNumber().toUpperCase();
int oldDesignationCode=dsEmployee.getDesignation().getCode();

EmployeeDTOInterface employeeDTO;
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(dsEmployee.getEmployeeId());
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designation.getCode());
employeeDTO.setDateOfBirth((Date)dateOfBirth.clone());
employeeDTO.setGender((gender=='M')?GENDER.Male:GENDER.Female);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharCardNumber(aadharCardNumber);
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
employeeDAO.update(employeeDTO);
//Making clown
dsEmployee.setName(name);
dsEmployee.setDesignation(((DesignationManager)designationManager).getDSDesignationByCode(designation.getCode()));
dsEmployee.setDateOfBirth((Date)dateOfBirth.clone());
dsEmployee.setGender((gender=='M')?GENDER.Male:GENDER.Female);
dsEmployee.setIsIndian(isIndian);
dsEmployee.setBasicSalary(basicSalary);
dsEmployee.setPANNumber(panNumber);
dsEmployee.setAadharCardNumber(aadharCardNumber);

//Remove old dsEmployee from set 
//Remove employee ID ,old  pan Number and old aadhar Card Number from all maps

employeesSet.remove(dsEmployee);
employeeIdWiseEmployeesMap.remove(dsEmployee.getEmployeeId().toUpperCase());
panNumberWiseEmployeesMap.remove(oldPANNumber);
aadharCardNumberWiseEmployeesMap.remove(oldAadharCardNumber);

//Updating all DS
this.employeeIdWiseEmployeesMap.put(dsEmployee.getEmployeeId().toUpperCase(),dsEmployee);
this.panNumberWiseEmployeesMap.put(panNumber.toUpperCase(),dsEmployee);
this.aadharCardNumberWiseEmployeesMap.put(aadharCardNumber.toUpperCase(),dsEmployee);
this.employeesSet.add(dsEmployee);
if(oldDesignationCode!=dsEmployee.getDesignation().getCode())
{
Set<EmployeeInterface>ets;
ets=this.designationCodeWiseEmployeesMap.get(oldDesignationCode);
ets.remove(dsEmployee);
ets=this.designationCodeWiseEmployeesMap.get(dsEmployee.getDesignation().getCode());
if(ets==null)
{
ets=new TreeSet<>();
ets.add(dsEmployee);
this.designationCodeWiseEmployeesMap.put(new Integer(dsEmployee.getDesignation().getCode()),ets);
}
else
{
ets.add(dsEmployee);
}
}
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public void removeEmployee(String employeeId)throws BLException
{
BLException blException;
blException=new BLException();
if(employeeId==null)
{
blException.addException("employeeId","Employee Id required");
throw blException;
}
else
{
employeeId=employeeId.trim();
if(employeeId.length()==0)
{
blException.addException("employeeId","Employee Id required");
throw blException;
}
else
{
if(employeeIdWiseEmployeesMap.containsKey(employeeId.toUpperCase())==false)
{
blException.addException("employeeId","Invalid Employee Id: "+employeeId);
throw blException;
}
}
}
try
{
EmployeeInterface dsEmployee;
dsEmployee=employeeIdWiseEmployeesMap.get(employeeId.toUpperCase());
String oldPANNumber=dsEmployee.getPANNumber().toUpperCase();
String oldAadharCardNumber=dsEmployee.getAadharCardNumber().toUpperCase();

EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
employeeDAO.delete(dsEmployee.getEmployeeId());

//Remove  dsEmployee from set 
//Remove employee ID ,old  pan Number and old aadhar Card Number from all maps

employeesSet.remove(dsEmployee);
employeeIdWiseEmployeesMap.remove(dsEmployee.getEmployeeId().toUpperCase());
panNumberWiseEmployeesMap.remove(oldPANNumber);
aadharCardNumberWiseEmployeesMap.remove(oldAadharCardNumber);
Set<EmployeeInterface> ets;
ets=this.designationCodeWiseEmployeesMap.get(dsEmployee.getDesignation().getCode());
ets.remove(dsEmployee);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public EmployeeInterface getEmployeeByEmployeeId(String employeeId)throws BLException
{
EmployeeInterface dsEmployee=employeeIdWiseEmployeesMap.get(employeeId.toUpperCase());
if(dsEmployee==null)
{
BLException blException;
blException=new BLException();
blException.addException("employeeId","Invalid Employee Id:"+employeeId);
throw blException;
}
EmployeeInterface employee;
employee=new Employee();
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setName(dsEmployee.getName());
DesignationInterface dsDesignation;
DesignationInterface designation;
dsDesignation=dsEmployee.getDesignation();//Actual Designation taken out from DS
designation=new Designation();//Making clown of dsDesignation
designation.setCode(dsDesignation.getCode());
designation.setTitle(dsDesignation.getTitle());
employee.setDesignation(designation);
employee.setDateOfBirth((Date)dsEmployee.getDateOfBirth().clone());
employee.setGender((dsEmployee.getGender()=='M')?GENDER.Male:GENDER.Female);
employee.setIsIndian(dsEmployee.getIsIndian());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employee.setPANNumber(dsEmployee.getPANNumber());
employee.setAadharCardNumber(dsEmployee.getAadharCardNumber());
return employee;
}
public EmployeeInterface getEmployeeByPANNumber(String panNumber)throws BLException
{

EmployeeInterface dsEmployee=panNumberWiseEmployeesMap.get(panNumber.toUpperCase());
if(dsEmployee==null)
{
BLException blException;
blException=new BLException();
blException.addException("panNumber","Invalid PAN Number:"+panNumber);
throw blException;
}
EmployeeInterface employee;
employee=new Employee();
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setName(dsEmployee.getName());
DesignationInterface dsDesignation;
DesignationInterface designation;
dsDesignation=dsEmployee.getDesignation();//Actual Designation taken out from DS
designation=new Designation();//Making clown of dsDesignation
designation.setCode(dsDesignation.getCode());
designation.setTitle(dsDesignation.getTitle());
employee.setDesignation(designation);
employee.setDateOfBirth((Date)dsEmployee.getDateOfBirth().clone());
employee.setGender((dsEmployee.getGender()=='M')?GENDER.Male:GENDER.Female);
employee.setIsIndian(dsEmployee.getIsIndian());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employee.setPANNumber(dsEmployee.getPANNumber());
employee.setAadharCardNumber(dsEmployee.getAadharCardNumber());
return employee;

}
public EmployeeInterface getEmployeeByAadharCardNumber(String aadharCardNumber)throws BLException
{

EmployeeInterface dsEmployee=aadharCardNumberWiseEmployeesMap.get(aadharCardNumber.toUpperCase());
if(dsEmployee==null)
{
BLException blException;
blException=new BLException();
blException.addException("aadharCardNumber","Invalid Aadhar Card Number:"+aadharCardNumber);
throw blException;
}

EmployeeInterface employee;
employee=new Employee();
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setName(dsEmployee.getName());
DesignationInterface dsDesignation;
DesignationInterface designation;
dsDesignation=dsEmployee.getDesignation();//Actual Designation taken out from DS
designation=new Designation();//Making clown of dsDesignation
designation.setCode(dsDesignation.getCode());
designation.setTitle(dsDesignation.getTitle());
employee.setDesignation(designation);
employee.setDateOfBirth((Date)dsEmployee.getDateOfBirth().clone());
employee.setGender((dsEmployee.getGender()=='M')?GENDER.Male:GENDER.Female);
employee.setIsIndian(dsEmployee.getIsIndian());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employee.setPANNumber(dsEmployee.getPANNumber());
employee.setAadharCardNumber(dsEmployee.getAadharCardNumber());
return employee;

}
public int getEmployeeCount()
{
return employeesSet.size();
}
public boolean employeeIdExists(String employeeId)
{
return employeeIdWiseEmployeesMap.containsKey(employeeId.toUpperCase());
}
public boolean employeePANNumberExists(String panNumber)
{
return panNumberWiseEmployeesMap.containsKey(panNumber.toUpperCase());
}
public boolean employeeAadharCardNumberExists(String aadharCardNumber)
{
return aadharCardNumberWiseEmployeesMap.containsKey(aadharCardNumber.toUpperCase());
}
public Set<EmployeeInterface>getEmployees()
{
Set<EmployeeInterface> employees=new TreeSet<>();
DesignationInterface designation;
DesignationInterface dsDesignation;
EmployeeInterface employee;
for(EmployeeInterface dsEmployee:employeesSet)
{
employee=new Employee();
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setName(dsEmployee.getName());
dsDesignation=dsEmployee.getDesignation();//Actual Designation taken out from DS
designation=new Designation();//Making clown of dsDesignation
designation.setCode(dsDesignation.getCode());
designation.setTitle(dsDesignation.getTitle());
employee.setDesignation(designation);
employee.setDateOfBirth((Date)dsEmployee.getDateOfBirth().clone());
employee.setGender((dsEmployee.getGender()=='M')?GENDER.Male:GENDER.Female);
employee.setIsIndian(dsEmployee.getIsIndian());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employee.setPANNumber(dsEmployee.getPANNumber());
employee.setAadharCardNumber(dsEmployee.getAadharCardNumber());
employees.add(employee);
}
return employees;
}
public Set<EmployeeInterface>getEmployeesByDesignationCode(int designationCode)throws BLException
{
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
if(designationManager.designationCodeExists(designationCode)==false)
{
BLException blException=new BLException();
blException.setGenericException("Invalid Designation Code: "+designationCode);
throw blException;
}
Set<EmployeeInterface> employees=new TreeSet<>();//for making clone
Set<EmployeeInterface> ets=this.designationCodeWiseEmployeesMap.get(designationCode);
if(ets==null)return employees;
DesignationInterface designation;
DesignationInterface dsDesignation;
EmployeeInterface employee;
for(EmployeeInterface dsEmployee:ets)
{
employee=new Employee();
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setName(dsEmployee.getName());
dsDesignation=dsEmployee.getDesignation();//Actual Designation taken out from DS
designation=new Designation();//Making clown of dsDesignation
designation.setCode(dsDesignation.getCode());
designation.setTitle(dsDesignation.getTitle());
employee.setDesignation(designation);
employee.setDateOfBirth((Date)dsEmployee.getDateOfBirth().clone());
employee.setGender((dsEmployee.getGender()=='M')?GENDER.Male:GENDER.Female);
employee.setIsIndian(dsEmployee.getIsIndian());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employee.setPANNumber(dsEmployee.getPANNumber());
employee.setAadharCardNumber(dsEmployee.getAadharCardNumber());
employees.add(employee);
}
return employees;
}
public int getEmployeeCountByDesignationCode(int designationCode)throws BLException
{
Set<EmployeeInterface> ets;
ets=this.designationCodeWiseEmployeesMap.get(designationCode);
if(ets==null)return 0;
return ets.size();
}
public boolean designationAlloted(int designationCode)throws BLException
{
return this.designationCodeWiseEmployeesMap.containsKey(designationCode);
}
}