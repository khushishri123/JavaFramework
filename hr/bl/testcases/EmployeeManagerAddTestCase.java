import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
import java.math.*;
import java.text.*;
import com.thinking.machines.enums.*;
class EmployeeManagerAddTestCase
{
public static void main(String gg[])
{
try
{
EmployeeInterface employee;
employee=new Employee();
employee.setName("Sammer");
DesignationInterface designation;
designation=new Designation();
designation.setCode(2);
employee.setDesignation(designation);
Date dateOfBirth;
dateOfBirth=new Date();
dateOfBirth.setDate(2);
dateOfBirth.setMonth(10);
dateOfBirth.setYear(100);
employee.setDateOfBirth(dateOfBirth);
employee.setGender(GENDER.Male);
boolean isIndian=true;
employee.setIsIndian(isIndian);
BigDecimal basicSalary;
basicSalary=new BigDecimal("300000");
employee.setBasicSalary(basicSalary);
String panNumber="A1234567";
String aadharCardNumber="U1234567";
employee.setPANNumber(panNumber);
employee.setAadharCardNumber(aadharCardNumber);
EmployeeManagerInterface employeeManager;
employeeManager=EmployeeManager.getEmployeeManager();
employeeManager.addEmployee(employee);
System.out.println("Employee Added with Employee ID : "+employee.getEmployeeId());
}catch(BLException blException)
{
if(blException.hasGenericException())
{
System.out.println(blException.getGenericException());
}
List<String> properties=blException.getProperties();
for(String property:properties)
{
System.out.println(blException.getException(property));
}
}
}
}
