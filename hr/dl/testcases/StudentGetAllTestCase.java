import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.util.*;
public class StudentGetAllTestCase
{
public static void main(String gg[])
{
try
{
StudentDAOInterface studentDAO;
studentDAO=new StudentDAO();
Set<StudentDTOInterface>students;
students=studentDAO.getAll();
for(StudentDTOInterface studentDTO:students)
{
System.out.println("Roll Number: "+studentDTO.getRollNumber()+" Name: "+studentDTO.getName()+" Course: "+studentDTO.getCourse());
}
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}