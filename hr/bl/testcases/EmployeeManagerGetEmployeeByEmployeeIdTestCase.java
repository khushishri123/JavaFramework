import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
class EmployeeManagerGetEmployeeByEmployeeIdTestCase
{
public static void main(String gg[])
{
String employeeId="A10000001";
try
{
EmployeeManagerInterface employeeManager;
employeeManager=EmployeeManager.getEmployeeManager();
EmployeeInterface employee;
employee=employeeManager.getEmployeeByEmployeeId(employeeId);
System.out.println("Employee Id: "+employee.getEmployeeId());
System.out.println("Name: "+employee.getName());
System.out.println("Designation Code: "+employee.getDesignation().getCode());
System.out.println("Designation Title: "+employee.getDesignation().getTitle());
System.out.println("Date Of Birth: "+employee.getDateOfBirth());
System.out.println("Gender: "+employee.getGender());
System.out.println("isIndian: "+employee.getIsIndian());
System.out.println("Basic Salary: "+employee.getBasicSalary());
System.out.println("PAN Number: "+employee.getPANNumber());
System.out.println("Aadhar Card Number: "+employee.getAadharCardNumber());
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
