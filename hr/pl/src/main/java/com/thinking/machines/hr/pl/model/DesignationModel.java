package com.thinking.machines.hr.pl.model;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
import javax.swing.table.*;
import java.io.*;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.io.font.constants.*;
import com.itextpdf.kernel.font.*;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.*;
import com.itextpdf.io.image.*;
import com.itextpdf.layout.borders.*;
public class DesignationModel extends AbstractTableModel
{
private java.util.List<DesignationInterface>designations;
private DesignationManagerInterface designationManager;
private String columnTitle[];
public DesignationModel()
{
populateDataStructures();
}

void populateDataStructures()
{
columnTitle=new String[2];
columnTitle[0]="S.NO";
columnTitle[1]="Designation";
try
{
designationManager=DesignationManager.getDesignationManager();
Set<DesignationInterface> blDesignations=designationManager.getDesignations();
this.designations=new LinkedList<>();
for(DesignationInterface designation:blDesignations)
{
this.designations.add(designation);
}
//For Sorting data according to Title
Collections.sort(this.designations,new Comparator<DesignationInterface>(){
public int compare(DesignationInterface left,DesignationInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
}catch(BLException blException)
{
//??? what to do
}
}



public int getRowCount()
{
return this.designations.size();
}
public int getColumnCount()
{
return this.columnTitle.length;
}
public String getColumnName(int columnIndex)
{
return columnTitle[columnIndex];
}
public Object getValueAt(int rowIndex,int columnIndex)
{
if(columnIndex==0)return rowIndex+1;//Here we are talking about S.NO
return this.designations.get(rowIndex).getTitle();//get method will return
//DesignationInterface type object's address 
}
public Class getColumnClass(int columnIndex)
{
if(columnIndex==0)return Integer.class;//it is as good as writting Class.forName("java.lang.Integer")
//Special Treatment
return String.class;
}
public boolean isCellEditable(int rowIndex,int columnIndex)
{
return false;
}

//Application Specific Methods
public void add(DesignationInterface designation)throws BLException
{
designationManager.addDesignation(designation);
//if added then,we have to add that designation in our Linked List
this.designations.add(designation);
//now whatever we are writting below is very bad thing
//because as we are adding a designation we have to sort the full list again
Collections.sort(this.designations,new Comparator<DesignationInterface>(){
public int compare(DesignationInterface left,DesignationInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
//Now to update Table immediately ,we call a method of AbstractTableModel
//This is Very Very Very Important Concept
fireTableDataChanged();
}
public int indexOfDesignation(DesignationInterface designation)throws BLException
{
Iterator<DesignationInterface> iterator=this.designations.iterator();
DesignationInterface d;
int index=0;
int designationCode=designation.getCode();
while(iterator.hasNext())
{
d=iterator.next();
if(d.getCode()==designationCode)
{
return index;
}
index++;
}
BLException blException=new BLException();
blException.setGenericException("Invalid Designation:"+designation.getTitle());
throw blException;
}
public int indexOfTitle(String title,boolean partialLeftSearch)throws BLException
{
Iterator<DesignationInterface> iterator=this.designations.iterator();
DesignationInterface d;
int index=0;
while(iterator.hasNext())
{
d=iterator.next();
if(partialLeftSearch)//it means that we have to not compare full string
{
if(d.getTitle().toUpperCase().startsWith(title.toUpperCase()))
{
return index;
}
}
else
{
if(d.getTitle().equalsIgnoreCase(title))
{
return index;
}
}
index++;
}
BLException blException=new BLException();
blException.setGenericException("Invalid title:"+title);
throw blException;
}

public void update(DesignationInterface designation)throws BLException
{
designationManager.updateDesignation(designation);
//if updated then,we have to add that designation in our Linked List
//But before adding the updated designation ,we have to delete the old one

this.designations.remove(indexOfDesignation(designation));
this.designations.add(designation);
//now whatever we are writting below is very bad thing
//because as we are adding a designation we have to sort the full list again
Collections.sort(this.designations,new Comparator<DesignationInterface>(){
public int compare(DesignationInterface left,DesignationInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
//Now to update Table immediately ,we call a method of AbstractTableModel
//This is Very Very Very Important Concept
fireTableDataChanged();
}

public void remove(int code)throws BLException
{
designationManager.removeDesignation(code);
//Here we have to apply loop because we have deleted that code from manager
Iterator<DesignationInterface> iterator=this.designations.iterator();
int index=0;
while(iterator.hasNext())
{
if(iterator.next().getCode()==code)break;
index++;
}
//if not found then index will be equal to designations.size()
if(index==designations.size())
{
BLException blException=new BLException();
blException.setGenericException("Invalid Designation Code: "+code);
throw blException;
}
this.designations.remove(index);
fireTableDataChanged();
}
public DesignationInterface getDesignationAt(int index)throws BLException
{
if(index<0 || index>=this.designations.size())
{
BLException blException=new BLException();
blException.setGenericException("Invalid Index: "+index);
throw blException;
}
return this.designations.get(index);
}
public void exportToPDF(File file)throws BLException
{
try
{
if(file.exists())file.delete();
PdfWriter pdfWriter=new PdfWriter(file);
PdfDocument pdfDocument=new PdfDocument(pdfWriter);
Document doc=new Document(pdfDocument);
Image logo=new Image(ImageDataFactory.create(this.getClass().getResource("/icons/logo_icon.png")));
logo.setWidth(50);
logo.setHeight(50);
//We have to put this image in a para
Paragraph logoPara=new Paragraph();
logoPara.add(logo);
Paragraph companyNamePara=new Paragraph();
companyNamePara.add("ABCD Corporation");
PdfFont companyNameFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
companyNamePara.setFont(companyNameFont);
companyNamePara.setFontSize(18);

Paragraph reportTitlePara=new Paragraph("List Of Designations");
PdfFont reportTitleFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
reportTitlePara.setFont(reportTitleFont);
reportTitlePara.setFontSize(16);

//Now we are creating font for our column heading
PdfFont columnTitleFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
PdfFont dataFont=PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
PdfFont pageNumberFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
//Now creating Column Heading
Paragraph columnTitle1=new Paragraph("S.No.");
columnTitle1.setFont(columnTitleFont);
columnTitle1.setFontSize(15);

Paragraph columnTitle2=new Paragraph("Designations");
columnTitle2.setFont(columnTitleFont);
columnTitle2.setFontSize(15);
//We cannot instantiate pageNumberPara because page number is a dynamic factor
Paragraph pageNumberParagraph;
//Now for data of cells
Paragraph dataParagraph;
//we have created topTableColumnWidths array for logo and company Name
float topTableColumnWidths[]={1,5};
float dataTableColumnWidths[]={1,5};

int sno,x,pageSize;
pageSize=5;//Means 5 records in a page
boolean newPage=true;
Table pageNumberTable;
Table topTable;
Table dataTable=null;
Cell cell;

int numberOfPages=this.designations.size()/pageSize;
if((this.designations.size())%pageSize!=0)numberOfPages++;
DesignationInterface designation;
int pageNumber=0;
sno=0;
x=0;
while(x<this.designations.size())
{

if(newPage==true)
{
//create new Page header
pageNumber++;
topTable=new Table(UnitValue.createPercentArray(topTableColumnWidths));
cell=new Cell();
cell.setBorder(Border.NO_BORDER);
cell.add(logoPara);
topTable.addCell(cell);

cell=new Cell();
cell.setBorder(Border.NO_BORDER);
cell.add(companyNamePara);
//Next Line humene islia likhi taki company Name ,logo ke center me aaye
cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
topTable.addCell(cell);
doc.add(topTable);

//Abhi tak humne logo aur company name ki baat kari

pageNumberParagraph=new Paragraph("Page: "+pageNumber+"/"+numberOfPages);
pageNumberParagraph.setFont(pageNumberFont);
pageNumberParagraph.setFontSize(14);

//This time we are using new Constructor of Table
//We are saying that in this table, only one cell is there
pageNumberTable=new Table(1);
//Next Line is Very Very Important
//100 islia pass kiya taki ,vo pura page cover ker le
pageNumberTable.setWidth(UnitValue.createPercentValue(100));

cell=new Cell();
cell.setBorder(Border.NO_BORDER);
cell.add(pageNumberParagraph);	
cell.setTextAlignment(TextAlignment.RIGHT);
pageNumberTable.addCell(cell);
doc.add(pageNumberTable);

dataTable=new Table(UnitValue.createPercentArray(dataTableColumnWidths));
dataTable.setWidth(UnitValue.createPercentValue(100));

cell=new Cell(1,2);
cell.add(reportTitlePara);
cell.setTextAlignment(TextAlignment.CENTER);
dataTable.addHeaderCell(cell);

//Now we are directly adding paragraph to a table
dataTable.addHeaderCell(columnTitle1);
dataTable.addHeaderCell(columnTitle2);
newPage=false;
}

designation=this.designations.get(x);
//add row to table

sno++;
cell=new Cell();
dataParagraph=new Paragraph(String.valueOf(sno));
dataParagraph.setFont(dataFont);
dataParagraph.setFontSize(14);
cell.add(dataParagraph);
cell.setTextAlignment(TextAlignment.RIGHT);
dataTable.addCell(cell);

cell=new Cell();
dataParagraph=new Paragraph(designation.getTitle());
dataParagraph.setFont(dataFont);
dataParagraph.setFontSize(14);
//By default alignment left hota hai,islia set nahi kara
cell.add(dataParagraph);
dataTable.addCell(cell);
x++;

if(sno%pageSize==0 || x==this.designations.size())
{
//create footer
doc.add(dataTable);
doc.add(new Paragraph("Software By: Khushi Shrivastava"));

if(x<this.designations.size())
{
//add new Page to document
doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
newPage=true;
}
}
}
doc.close();
}catch(Exception exception)
{
BLException blException;
blException=new BLException();
blException.setGenericException(exception.getMessage());
throw blException;
}
}
}//Class Ends

