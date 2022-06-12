import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
import java.math.*;
import java.text.*;
import com.thinking.machines.enums.*;
class EmployeeManagerUpdateTestCase
{
public static void main(String gg[])
{
try
{
EmployeeInterface employee;
employee=new Employee();
employee.setEmployeeId("A10000003");
employee.setName("Shruti");
DesignationInterface designation;
designation=new Designation();
designation.setCode(3);
employee.setDesignation(designation);
Date dateOfBirth;
dateOfBirth=new Date();
dateOfBirth.setDate(6);
dateOfBirth.setMonth(2);
dateOfBirth.setYear(80);
employee.setDateOfBirth(dateOfBirth);
employee.setGender(GENDER.Female);
boolean isIndian=true;
employee.setIsIndian(isIndian);
BigDecimal basicSalary;
basicSalary=new BigDecimal("500000");
employee.setBasicSalary(basicSalary);
String panNumber="A1234567";
String aadharCardNumber="U1234567";
employee.setPANNumber(panNumber);
employee.setAadharCardNumber(aadharCardNumber);
EmployeeManagerInterface employeeManager;
employeeManager=EmployeeManager.getEmployeeManager();
employeeManager.updateEmployee(employee);
System.out.println("Employee Updated");
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
