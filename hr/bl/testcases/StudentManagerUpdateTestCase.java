import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
class StudentManagerUpdateTestCase
{
public static void main(String gg[])
{
try
{
StudentInterface student=new Student();
student.setRollNumber(4);
student.setName("Siddhi");
student.setCourse("C");
StudentManagerInterface studentManager=StudentManager.getStudentManager();
studentManager.updateStudent(student);
System.out.println("Student updated");
}catch(BLException blException)
{
if(blException.hasGenericException())
{
System.out.println(blException.getGenericException());
}
List<String>properties=blException.getProperties();
for(String property:properties)
{
System.out.println(blException.getException(property));
}
}
}
}