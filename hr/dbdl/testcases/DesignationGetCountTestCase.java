import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
public class DesignationGetCountTestCase
{
public static void main(String gg[])
{
try
{
DesignationDAO designationDAO;
designationDAO=new DesignationDAO();
System.out.println("Designation Count: "+designationDAO.getCount());
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}
