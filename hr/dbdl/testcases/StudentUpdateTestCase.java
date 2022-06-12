import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dto.*;
public class StudentUpdateTestCase
{
public static void main(String gg[])
{
int rollNumber=Integer.parseInt(gg[0]);
String name=gg[1];
String course=gg[2];
try
{
StudentDTOInterface studentDTO;
studentDTO=new StudentDTO();
studentDTO.setRollNumber(rollNumber);
studentDTO.setName(name);
studentDTO.setCourse(course);
StudentDAOInterface studentDAO;
studentDAO=new StudentDAO();
studentDAO.update(studentDTO);
System.out.println("Student Updated");
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}

