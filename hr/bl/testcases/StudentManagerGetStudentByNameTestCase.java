import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
class StudentManagerGetStudentByNameTestCase
{
public static void main(String gg[])
{
String name="Stuti";
try
{
StudentManagerInterface studentManager;
studentManager=StudentManager.getStudentManager();
StudentInterface student;
student=studentManager.getStudentByName(name);
System.out.println("RollNumber: "+student.getRollNumber()+" , Name: "+student.getName()+" ,Course: "+student.getCourse());
}catch(BLException blException)
{
List<String> properties=blException.getProperties();
for(String property:properties)
{
System.out.println(blException.getException(property));
}
}
}
}
