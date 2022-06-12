import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dto.*;
public class StudentAddTestCase
{
public static void main(String gg[])
{
String name=gg[0];
String course=gg[1];
try
{
StudentDTOInterface studentDTO;
studentDTO=new StudentDTO();
studentDTO.setName(name);
studentDTO.setCourse(course);
StudentDAOInterface studentDAO;
studentDAO=new StudentDAO();
studentDAO.add(studentDTO);
System.out.println("Student Added with roll Number:"+studentDTO.getRollNumber());
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}

