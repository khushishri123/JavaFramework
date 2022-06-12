import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
class StudentManagerGetStudentsByCourseTestCase
{
public static void main(String gg[])
{
String course="C,C++,Java,Python,Ai";
try
{
StudentManagerInterface studentManager;
studentManager=StudentManager.getStudentManager();
Set<StudentInterface> students;
students=studentManager.getStudentsByCourse(course);
for(StudentInterface student:students)
{
System.out.printf("Roll Number: %d ,Name: %s ,Course:%s\n",student.getRollNumber(),student.getName(),student.getCourse());
}
}catch(BLException blException)
{
if(blException.hasGenericException())
{
System.out.println(blException.getGenericException());
}
}
}
}
