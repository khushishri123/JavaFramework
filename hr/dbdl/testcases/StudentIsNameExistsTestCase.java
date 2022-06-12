import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.util.*;
public class StudentIsNameExistsTestCase
{
public static void main(String gg[])
{
String name=gg[0];
try
{
System.out.println("Name: "+name+" exists: "+new StudentDAO().isNameExists(name));
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}