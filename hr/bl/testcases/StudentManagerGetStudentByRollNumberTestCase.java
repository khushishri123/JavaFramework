import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
class StudentManagerGetStudentByRollNumberTestCase
{
public static void main(String gg[])
{
int rollNumber=2;
try
{
StudentManagerInterface studentManager;
studentManager=StudentManager.getStudentManager();
StudentInterface student;
student=studentManager.getStudentByRollNumber(rollNumber);
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
