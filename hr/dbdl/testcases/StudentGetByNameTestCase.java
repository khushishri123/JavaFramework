import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.util.*;
public class StudentGetByNameTestCase
{
public static void main(String gg[])
{
String name=gg[0];
try
{
StudentDAOInterface studentDAO;
studentDAO=new StudentDAO();
StudentDTOInterface studentDTO;
studentDTO=studentDAO.getByName(name);
System.out.println("Roll Number: "+studentDTO.getRollNumber()+" Name: "+studentDTO.getName()+" Course: "+studentDTO.getCourse());
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}