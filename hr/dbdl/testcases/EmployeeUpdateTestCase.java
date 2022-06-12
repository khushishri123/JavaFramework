import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import java.util.*;
import java.math.*;
import java.text.*;
import com.thinking.machines.enums.*;
public class EmployeeUpdateTestCase
{
public static void main(String gg[])
{
String employeeId=gg[0];
String name=gg[1];
int designationCode=Integer.parseInt(gg[2]);
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
Date dateOfBirth=null;
try
{
dateOfBirth=sdf.parse(gg[3]);
}catch(ParseException pe)
{
System.out.println(pe.getMessage());
return;
}
char gender=gg[4].charAt(0);
boolean isIndian=Boolean.parseBoolean(gg[5]);
BigDecimal basicSalary=new BigDecimal(gg[6]);
String panNumber=gg[7];
String aadharCardNumber=gg[8];
try
{
EmployeeDTOInterface employeeDTO;
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(employeeId);
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
employeeDTO.setDateOfBirth(dateOfBirth);
if(gender=='M')
{
employeeDTO.setGender(GENDER.Male);
}
if(gender=='F')
{
employeeDTO.setGender(GENDER.Female);
}
employeeDTO.setIsIndian(isIndian);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharCardNumber(aadharCardNumber);
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
employeeDAO.update(employeeDTO);
System.out.println("Employee Updated");
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}
