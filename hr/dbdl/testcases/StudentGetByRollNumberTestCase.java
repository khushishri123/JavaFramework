import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.util.*;
public class StudentGetByRollNumberTestCase
{
public static void main(String gg[])
{
int rollNumber=Integer.parseInt(gg[0]);
try
{
StudentDAOInterface studentDAO;
studentDAO=new StudentDAO();
StudentDTOInterface studentDTO;
studentDTO=studentDAO.getByRollNumber(rollNumber);
System.out.println("Roll Number: "+studentDTO.getRollNumber()+" Name: "+studentDTO.getName()+" Course: "+studentDTO.getCourse());
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}