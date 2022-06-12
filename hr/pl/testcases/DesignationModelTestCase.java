import java.awt.*;
import javax.swing.*;
import com.thinking.machines.hr.pl.model.*;
class DesignationModelTestCase extends JFrame
{
private DesignationModel designationModel;
private JTable tb;
private Container container;
private JScrollPane jsp;
public DesignationModelTestCase()
{
designationModel=new DesignationModel();
tb=new JTable(designationModel);
jsp=new JScrollPane(tb,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
container=getContentPane();
container.setLayout(new BorderLayout());
container.add(jsp);
setLocation(100,200);
setSize(500,300);
setVisible(true);
}
public static void main(String gg[])
{
DesignationModelTestCase dmtc=new DesignationModelTestCase();
}
}