import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
class StudentManagerStudentRollNumberExistsTestCase
{
public static void main(String gg[])
{
int rollNumber=3;
try
{
StudentManagerInterface studentManager;
studentManager=StudentManager.getStudentManager();
System.out.println("Roll Number: "+rollNumber+" exists: "+studentManager.studentRollNumberExists(rollNumber));
}catch(BLException blException)
{
if(blException.hasGenericException())
{
System.out.println(blException.getGenericException());
}
}
}
}

