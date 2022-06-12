package com.thinking.machines.hr.pl.ui;
import com.thinking.machines.hr.pl.model.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.managers.*;
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
public class DesignationUI extends JFrame implements DocumentListener,ListSelectionListener
{
private JLabel titleLabel;// This is for showing Designation at top(Title)
private JLabel searchLabel;
private JTextField searchTextField;
private JButton clearSearchTextFieldButton;
private JLabel searchErrorLabel;
private DesignationModel designationModel;
private JTable designationTable;
private Container container;
private JScrollPane scrollPane;
private DesignationPanel designationPanel;//For Inner Class
private enum MODE{VIEW,ADD,EDIT,DELETE,EXPORT_TO_PDF};
private MODE mode;
private ImageIcon logoIcon;
private ImageIcon addIcon;
private ImageIcon editIcon;
private ImageIcon deleteIcon;
private ImageIcon cancelIcon;
private ImageIcon pdfIcon;
private ImageIcon saveIcon;
private ImageIcon crossIcon;
public DesignationUI()
{
initComponents();
setAppearance();
addListeners();
setViewMode();
designationPanel.setViewMode();
}
private void initComponents()//Function Initializing Components
{
logoIcon=new ImageIcon(this.getClass().getResource("/icons/logo_icon.png"));
addIcon=new ImageIcon(this.getClass().getResource("/icons/add_icon.png"));
editIcon=new ImageIcon(this.getClass().getResource("/icons/edit_icon.png"));
cancelIcon=new ImageIcon(this.getClass().getResource("/icons/cancel_icon.png"));
deleteIcon=new ImageIcon(this.getClass().getResource("/icons/delete_icon.png"));
pdfIcon=new ImageIcon(this.getClass().getResource("/icons/pdf_icon.png"));
saveIcon=new ImageIcon(this.getClass().getResource("/icons/save_icon.png"));
crossIcon=new ImageIcon(this.getClass().getResource("/icons/cross_icon.png"));

setIconImage(logoIcon.getImage());//setImageIcon is a method of frame class
//getImage will return address of an Image type object

designationModel=new DesignationModel();
titleLabel=new JLabel("Designations");
searchLabel=new JLabel("Search");
searchTextField=new JTextField();
clearSearchTextFieldButton=new JButton(crossIcon);
searchErrorLabel=new JLabel("");
designationTable=new JTable(designationModel);
scrollPane=new JScrollPane(designationTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
designationPanel=new DesignationPanel();
container=getContentPane();
}
private void setAppearance()
{
Font titleFont=new Font("Verdana",Font.BOLD,18);
Font captionFont=new Font("Verdana",Font.BOLD,16);
Font dataFont=new Font("Verdana",Font.PLAIN,16);
Font columnHeaderFont=new Font("Verdana",Font.BOLD,16);
Font searchErrorFont=new Font("Verdana",Font.BOLD,12);
titleLabel.setFont(titleFont);
searchLabel.setFont(captionFont);
searchTextField.setFont(dataFont);
searchErrorLabel.setFont(searchErrorFont);
searchErrorLabel.setForeground(Color.red);
designationTable.setFont(dataFont);


//Setting row height of table
designationTable.setRowHeight(35);
//Setting columnWidth
designationTable.getColumnModel().getColumn(0).setPreferredWidth(20);
designationTable.getColumnModel().getColumn(1).setPreferredWidth(400);
//Header Height
JTableHeader header=designationTable.getTableHeader();
header.setFont(columnHeaderFont);
//To select full row (not a cell)
designationTable.setRowSelectionAllowed(true);
//To select a single row at a time
designationTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//Not to reorder
header.setReorderingAllowed(false);
header.setResizingAllowed(false);

container.setLayout(null);
int lm,tm;//lm:Left Margin ,tm:Top Margin
lm=0;
tm=0;
titleLabel.setBounds(lm+10,tm+10,200,40);//here width=200 and height=40
searchErrorLabel.setBounds(lm+10+100+400+10-75,tm+10+10+20,100,20);
searchLabel.setBounds(lm+10,tm+10+40+10,100,30);
searchTextField.setBounds(lm+10+100+5,tm+10+40+10,400,30);
clearSearchTextFieldButton.setBounds(lm+10+100+400+10,tm+10+40+10,30,30);
scrollPane.setBounds(lm+10,tm+10+40+10+30+10,565,300);
designationPanel.setBounds(lm+10,tm+10+40+10+30+10+300+10,565,200);

container.add(titleLabel);
container.add(searchErrorLabel);
container.add(searchLabel);
container.add(searchTextField);
container.add(clearSearchTextFieldButton);
container.add(scrollPane);
container.add(designationPanel);
int w,h;
w=600;
h=660;
setSize(w,h);
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
setLocation((d.width/2)-(w/2),(d.height/2)-(h/2));
}
private void addListeners()
{
searchTextField.getDocument().addDocumentListener(this);//Because of 
//addDocumentListener,We implemented DocumentListener ,since the parameter
//of addDocumentListener is of DocumentListener type

clearSearchTextFieldButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ae)
{
searchTextField.setText("");
searchTextField.requestFocus();
}
});
designationTable.getSelectionModel().addListSelectionListener(this);
}
private void searchDesignation()
{
searchErrorLabel.setText("");
//For Searching the specified Designation
String title=searchTextField.getText().trim();
//If title.length()==0 then return
//Because of this searching will not get performed if title.length==0
if(title.length()==0)return;
//Now we are not going to search,model is going to search
//We are going to call indexOfTitle method but it has been declared with
//throws keyword followed by BLException
int rowIndex;
try
{
rowIndex=designationModel.indexOfTitle(title,true);
}catch(BLException blException)
{
searchErrorLabel.setText("Not Found");
return;
}
//If found,then we have to select that row,for that we called a method
//setRowSelectionInterval and we have to pass to int .The first int will
//show from where to start and the second int will show till where we have
//to select the row

designationTable.setRowSelectionInterval(rowIndex,rowIndex);

//To move the scrollPane downwards

Rectangle rectangle=designationTable.getCellRect(rowIndex,0,true);
designationTable.scrollRectToVisible(rectangle);
}
public void changedUpdate(DocumentEvent de)
{
searchDesignation();
}
public void removeUpdate(DocumentEvent de)
{
searchDesignation();
}
public void insertUpdate(DocumentEvent de)
{
searchDesignation();
}
//Since he have Implemented ListSelectionListener,we have to define 
//following methods
public void valueChanged(ListSelectionEvent ev)
{
//If this method got invoked it means that row Selection got changed
int selectedRowIndex=designationTable.getSelectedRow();
try
{
DesignationInterface designation=designationModel.getDesignationAt(selectedRowIndex);
designationPanel.setDesignation(designation);
}catch(BLException blException)
{
designationPanel.clearDesignation();//clear Designation Label ko khali ker degi
}
}
private void setViewMode()
{
this.mode=MODE.VIEW;
if(designationModel.getRowCount()==0)//If there is no records,then these all should be disabled
{
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
designationTable.setEnabled(false);
}
else
{
searchTextField.setEnabled(true);
clearSearchTextFieldButton.setEnabled(true);
designationTable.setEnabled(true);
}
}
private void setAddMode()
{
this.mode=MODE.ADD;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
designationTable.setEnabled(false);
}
private void setEditMode()
{
this.mode=MODE.EDIT;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
designationTable.setEnabled(false);
}
private void setDeleteMode()
{
this.mode=MODE.DELETE;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
designationTable.setEnabled(false);
}
private void setExportToPDFMode()
{
this.mode=MODE.EXPORT_TO_PDF;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
designationTable.setEnabled(false);
}
//Inner Class Starts
class DesignationPanel extends JPanel
{
private JLabel titleCaptionLabel;
private JLabel titleLabel;
private JTextField titleTextField;
private JButton clearTitleTextFieldButton;
private JButton addButton;
private JButton editButton;
private JButton cancelButton;
private JButton deleteButton;
private JButton exportToPDFButton;
private JPanel buttonsPanel;
private DesignationInterface designation;
DesignationPanel()
{
setBorder(BorderFactory.createLineBorder(new Color(165,165,165)));
initComponents();
setAppearance();
addListeners();
}
public void setDesignation(DesignationInterface designation)
{
this.designation=designation;
titleLabel.setText(designation.getTitle());
}
public void clearDesignation()
{
this.designation=null;
titleLabel.setText("");
}
private void initComponents()
{
designation=null;
titleCaptionLabel=new JLabel("Designations");
titleLabel=new JLabel("");
titleTextField=new JTextField();
clearTitleTextFieldButton=new JButton(crossIcon);
buttonsPanel=new JPanel();
addButton=new JButton(addIcon);
editButton=new JButton(editIcon);
cancelButton=new JButton(cancelIcon);
deleteButton=new JButton(deleteIcon);
exportToPDFButton=new JButton(pdfIcon);
}
private void setAppearance()
{
Font captionFont=new Font("Verdana",Font.BOLD,16);
Font dataFont=new Font("Verdana",Font.PLAIN,16);
titleCaptionLabel.setFont(captionFont);
titleLabel.setFont(dataFont);
titleTextField.setFont(dataFont);
setLayout(null);
int lm,tm;
lm=0;
tm=0;
titleCaptionLabel.setBounds(lm+10,tm+10,120,30);
titleLabel.setBounds(lm+10+120+10,tm+10,400,30);
titleTextField.setBounds(lm+10+120+5,tm+10,350,30);
clearTitleTextFieldButton.setBounds(lm+10+120+5+350+5,tm+10,30,30);
buttonsPanel.setBounds(50,tm+20+30,465,100);
buttonsPanel.setBorder(BorderFactory.createLineBorder(new Color(165,165,165)));
addButton.setBounds(70,tm+20+5,50,50);
editButton.setBounds(70+50+20,tm+20+5,50,50);
cancelButton.setBounds(70+50+20+50+20,tm+20+5,50,50);
deleteButton.setBounds(70+50+20+50+20+50+20,tm+20+5,50,50);
exportToPDFButton.setBounds(70+50+20+50+20+50+20+50+20,tm+20+5,50,50);

buttonsPanel.setLayout(null);
buttonsPanel.add(addButton);
buttonsPanel.add(editButton);
buttonsPanel.add(cancelButton);
buttonsPanel.add(deleteButton);
buttonsPanel.add(exportToPDFButton);

add(titleCaptionLabel);
add(titleTextField);
add(titleLabel);
add(clearTitleTextFieldButton);
add(buttonsPanel);
}
private boolean addDesignation()
{
String title=titleTextField.getText().trim();
if(title.length()==0)
{
JOptionPane.showMessageDialog(this,"Designation Required");
titleTextField.requestFocus();
return false;
}
DesignationInterface d=new Designation();
d.setTitle(title);
try
{
designationModel.add(d);
int rowIndex;
rowIndex=designationModel.indexOfTitle(title,true);
designationTable.setRowSelectionInterval(rowIndex,rowIndex);
Rectangle rectangle=designationTable.getCellRect(rowIndex,0,true);
designationTable.scrollRectToVisible(rectangle);
return true;
}catch(BLException blException)
{
if(blException.hasGenericException())
{
JOptionPane.showMessageDialog(this,blException.getGenericException());
}
else
{
if(blException.hasException("title"))
{
JOptionPane.showMessageDialog(this,blException.getException("title"));
}
}
titleTextField.requestFocus();
return false;
}
}
private boolean updateDesignation()
{
String title=titleTextField.getText().trim();
if(title.length()==0)
{
JOptionPane.showMessageDialog(this,"Designation Required");
titleTextField.requestFocus();
return false;
}
DesignationInterface d=new Designation();
d.setCode(this.designation.getCode());
d.setTitle(title);
try
{
designationModel.update(d);
int rowIndex;
rowIndex=designationModel.indexOfDesignation(d);
designationTable.setRowSelectionInterval(rowIndex,rowIndex);
Rectangle rectangle=designationTable.getCellRect(rowIndex,0,true);
designationTable.scrollRectToVisible(rectangle);
return true;
}catch(BLException blException)
{
if(blException.hasGenericException())
{
JOptionPane.showMessageDialog(this,blException.getGenericException());
}
else
{
if(blException.hasException("title"))
{
JOptionPane.showMessageDialog(this,blException.getException("title"));
}
}
titleTextField.requestFocus();
return false;
}
}
private void removeDesignation()
{
try
{
String title=this.designation.getTitle();
int selectedOption=JOptionPane.showConfirmDialog(this," Delete "+title+" ?","Confirmation",JOptionPane.YES_NO_OPTION);
if(selectedOption==JOptionPane.NO_OPTION)return;
designationModel.remove(this.designation.getCode());
JOptionPane.showMessageDialog(this,title+" Deleted");
this.clearDesignation();
}catch(BLException blException)
{
if(blException.hasGenericException())
{
JOptionPane.showMessageDialog(this,blException.getGenericException());
}
else
{
if(blException.hasException("title"))
{
JOptionPane.showMessageDialog(this,blException.getException("title"));
}
}
}
}
private void addListeners()
{
this.addButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ae)
{
if(mode==MODE.VIEW)//Agar abhi hum view mode me hai to ab hame add mode 
//me aana hai
{
setAddMode();
}
//agar add mode me hai to hame save kerna hai ya cancel
else
{
//logic to save and get back to view mode
//if addDesignation is completed fully then,it will go in if block
if(addDesignation())//Code to save will be written in this function
{
setViewMode();
}
}
}
});

this.editButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ae)
{
if(mode==MODE.VIEW)
{
setEditMode();
}
else
{
if(updateDesignation())
{
setViewMode();
}
}
}
});
this.cancelButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ae)
{
setViewMode();
}
});

this.deleteButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ae)
{
setDeleteMode();
}
});

this.exportToPDFButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ae)
{
JFileChooser jfc=new JFileChooser();
jfc.setCurrentDirectory(new File("."));
int selectedOption=jfc.showSaveDialog(DesignationUI.this);
//If you will pass "this" to showSaveDialog ,then it will show error
//because here an anonyms class has been created.By writing "this" we are 
//passing the address of the anonyms class object and we cannot pass its
// address

if(selectedOption==jfc.APPROVE_OPTION)
{
try
{
File selectedFile=jfc.getSelectedFile();
String pdfFile=selectedFile.getAbsolutePath();
if(pdfFile.endsWith("."))pdfFile+="pdf";
else if(pdfFile.endsWith(".pdf")==false)pdfFile+=".pdf";
File file=new File(pdfFile);
/*
If somebody specified the File Name as: c:\tiger\cartoon
then getParent() is going to return the path till first folder after 
the c root
here it is going to return c:\tiger
*/
File parent=new File(file.getParent());
if(parent.exists()==false || parent.isDirectory()==false)
{
JOptionPane.showMessageDialog(DesignationUI.this,"Invalid Path: "+file.getAbsolutePath());
return;
}
try
{
designationModel.exportToPDF(file);
JOptionPane.showMessageDialog(DesignationUI.this,"Data exported to : "+file.getAbsolutePath());
}catch(BLException blException)
{
if(blException.hasGenericException())
{
JOptionPane.showMessageDialog(DesignationUI.this,blException.getGenericException());
}
}
}catch(Exception e)
{
System.out.println(e.getMessage());
}
}
}
});


}
void setViewMode()
{
DesignationUI.this.setViewMode();
this.addButton.setIcon(addIcon);
this.editButton.setIcon(editIcon);
this.titleTextField.setVisible(false);
this.titleLabel.setVisible(true);
this.clearTitleTextFieldButton.setVisible(false);
this.addButton.setEnabled(true);
this.cancelButton.setEnabled(false);
if(designationModel.getRowCount()>0)
{
this.editButton.setEnabled(true);
this.deleteButton.setEnabled(true);
this.exportToPDFButton.setEnabled(true);
}
else
{
this.editButton.setEnabled(false);
this.deleteButton.setEnabled(false);
this.exportToPDFButton.setEnabled(false);
}
}
void setAddMode()
{
DesignationUI.this.setAddMode();
this.titleTextField.setText("");
this.titleLabel.setVisible(false);
this.titleTextField.setVisible(true);
this.clearTitleTextFieldButton.setVisible(true);
this.addButton.setIcon(saveIcon);
this.editButton.setEnabled(false);
this.cancelButton.setEnabled(true);
this.deleteButton.setEnabled(false);
this.exportToPDFButton.setEnabled(false);
}
void setEditMode()
{
//If no row is selected then control will go in if block
if(designationTable.getSelectedRow()<0 || designationTable.getSelectedRow()>=designationModel.getRowCount())
{
JOptionPane.showMessageDialog(this,"Select Designation to edit");
return;
}
DesignationUI.this.setEditMode();
this.titleTextField.setText(this.designation.getTitle());
this.titleLabel.setVisible(false);
this.titleTextField.setVisible(true);
this.clearTitleTextFieldButton.setVisible(true);
this.addButton.setEnabled(false);
this.editButton.setIcon(saveIcon);
this.cancelButton.setEnabled(true);
this.deleteButton.setEnabled(false);
this.exportToPDFButton.setEnabled(false);
}
void setDeleteMode()
{
//If no row is selected then control will go in if block
if(designationTable.getSelectedRow()<0 || designationTable.getSelectedRow()>=designationModel.getRowCount())
{
JOptionPane.showMessageDialog(this,"Select Designation to delete");
return;
}
DesignationUI.this.setDeleteMode();
//if have not enabled textField because we have to delete the designation
//which has been displayed in Label
this.addButton.setEnabled(false);
this.editButton.setEnabled(false);
this.cancelButton.setEnabled(true);
this.deleteButton.setEnabled(false);//Do baar koi click na ker sake that's
//why disabled
this.exportToPDFButton.setEnabled(false);
removeDesignation();
DesignationUI.this.setViewMode();
this.setViewMode();
}
void setExportToPDFMode()
{
DesignationUI.this.setExportToPDFMode();
//if have not enabled textField because we have to delete the designation
//which has been displayed in Label
this.addButton.setEnabled(false);
this.editButton.setEnabled(false);
this.cancelButton.setEnabled(true);
this.deleteButton.setEnabled(false);//Do baar koi click na ker sake that's
//why disabled
this.exportToPDFButton.setEnabled(false);

}

}//Inner Class Ends
}