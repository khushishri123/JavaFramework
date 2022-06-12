import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
class StudentManagerStudentNameExistsTestCase
{
public static void main(String gg[])
{
String name="Stuti";
try
{
StudentManagerInterface studentManager;
studentManager=StudentManager.getStudentManager();
System.out.println("Name: "+name+" exists: "+studentManager.studentNameExists(name));
}catch(BLException blException)
{
if(blException.hasGenericException())
{
System.out.println(blException.getGenericException());
}
}
}
}

