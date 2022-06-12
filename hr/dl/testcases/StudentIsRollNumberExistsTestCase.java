import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.util.*;
public class StudentIsRollNumberExistsTestCase
{
public static void main(String gg[])
{
int rollNumber=Integer.parseInt(gg[0]);
try
{
System.out.println("Roll Number: "+rollNumber+" exists: "+new StudentDAO().isRollNumberExists(rollNumber));
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}