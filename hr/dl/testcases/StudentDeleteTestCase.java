import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.util.*;
public class StudentDeleteTestCase
{
public static void main(String gg[])
{
int rollNumber=Integer.parseInt(gg[0]);
try
{
StudentDAOInterface studentDAO=new StudentDAO();
studentDAO.delete(rollNumber);
System.out.println("Student Deleted");
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}