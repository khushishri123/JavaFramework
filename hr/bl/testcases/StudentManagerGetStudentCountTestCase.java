import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
class StudentManagerGetStudentCountTestCase
{
public static void main(String gg[])
{
try
{
StudentManagerInterface studentManager;
studentManager=StudentManager.getStudentManager();
System.out.println("Number of Students: "+studentManager.getStudentCount());
}catch(BLException blException)
{
if(blException.hasGenericException())
{
System.out.println(blException.getGenericException());
}
}
}
}

