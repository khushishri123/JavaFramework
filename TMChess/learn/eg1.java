import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
class MyModel extends AbstractTableModel
{
private String title[]={"A","B"};
private Object data[][];
public MyModel()
{
data=new Object[2][2];
data[0][0]="One";
data[0][1]=new JButton("First Button");
data[1][0]="Two";
data[1][1]=new JButton("Second Button");
}
public int getRowCount()
{
return data.length ;
}
public int getColumnCount()
{
return this.title.length;
}
public String getColumnName(int c)
{
return this.title[c];
}
public Class getColumnClass(int c)
{
return this.title[c].getClass();
}
public boolean isCellEditable(int r,int c)
{
if(c==1)return true;
return false;
}
public Object getValueAt(int r,int c)
{
return data[r][c];
}
public void setValueAt(Object data,int r,int c)
{
System.out.println(r+","+c+","+data);
}
}
class Whatever extends JFrame
{
private JTable table;
MyModel myModel;
Container c;
public Whatever()
{
myModel=new MyModel();
table=new JTable(myModel);
table.getColumn("B").setCellRenderer(new ButtonCellRenderer());
table.getColumn("B").setCellEditor(new ButtonCellEditor());
c=getContentPane();
c.setLayout(new BorderLayout());
c.add(table,BorderLayout.CENTER);
setSize(500,400);
setLocation(60,30);
setVisible(true);
}
class ButtonCellRenderer implements TableCellRenderer
{
public Component getTableCellRendererComponent(JTable table,Object value,boolean a,boolean b,int r,int c)
{
//System.out.println(value.toString());
System.out.println(r+","+c);
return (JButton)value;
}
}
class ButtonCellEditor extends DefaultCellEditor
{
private JButton button;
private boolean isClicked;
private int row,col;
ButtonCellEditor()
{
super(new JCheckBox());
button=new JButton();
button.setOpaque(true);
button.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
System.out.println("Great");
fireEditingsStopped();
}
});
}
public Component getTableCellEditorComponent(JTable table,Object value,boolean a,int r,int c)
{
System.out.println("getTableCellEditorComponent got called");
this.row=r;
this.col=c;
button.setForeground(Color.black);
button.setBackground(UIManager.getColor("Button.background"));
button.setText("Whatever");
this.isClicked=true;
return button;
}
public Object getCellEditorValue()
{
System.out.println("getCellEditorValue got called");
System.out.println("Button at cell: "+this.row+","+this.col+" clicked");
return "Cool";
}
public boolean stopCellEditing()
{
this.isClicked=false;
return super.stopCellEditing();
}
public void fireEditingsStopped()
{
super.fireEditingStopped();
}
}
public static void main(String args[])
{
Whatever w=new Whatever();
}
}
