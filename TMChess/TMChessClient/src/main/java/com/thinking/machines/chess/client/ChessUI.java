package com.thinking.machines.chess.client;
import com.thinking.machines.nframework.client.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.event.*;
import javax.swing.table.*;
import com.thinking.machines.chess.common.*;
public class ChessUI extends JFrame
{
private Container container;
private JTable availableMembersList;
private AvailableMembersListModel availableMembersListModel;
private JScrollPane availableMembersListScrollPane;
private NFrameworkClient client;
private javax.swing.Timer timer;
private String username;
public ChessUI(String username)
{
super("Member: "+username); //username title pe dikhega
this.username=username;
this.client=new NFrameworkClient();
initComponents();
setAppearance();
addListeners();
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
int w=500;
int h=400;
setSize(w,h);
setLocation(d.width/2-w/2,d.height/2-h/2);
}
private void initComponents()
{
JPanel p=new JPanel();
p.setLayout(new BorderLayout());
p.add(new JLabel("Members"),BorderLayout.NORTH);
this.availableMembersListModel=new AvailableMembersListModel();
this.availableMembersList=new JTable(this.availableMembersListModel);
this.availableMembersList.getColumn(" ").setCellRenderer(new AvailableMembersListButtonRenderer());
this.availableMembersList.getColumn(" ").setCellEditor(new AvailableMembersListButtonCellEditor());
this.availableMembersListScrollPane=new JScrollPane(this.availableMembersList,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
p.add(availableMembersList,BorderLayout.CENTER);
container=getContentPane();
container.setLayout(new BorderLayout());
container.add(p,BorderLayout.EAST);
}
private void setAppearance()
{
//later on
}
private void addListeners()
{
this.timer=new javax.swing.Timer(3000,new ActionListener(){
public void actionPerformed(ActionEvent e)
{
timer.stop();
try
{
//To get and update list of available users
java.util.List<String> members=(java.util.List<String>)client.execute("/TMChessServer/getMembers",username);
ChessUI.this.availableMembersListModel.setMembers(members);
timer.start();
}catch(Throwable t)
{
JOptionPane.showMessageDialog(ChessUI.this,t.toString());
}
} //actionPerformed ends
});
 
//when cross clicked ,then logout
addWindowListener(new WindowAdapter(){
public void windowClosing(WindowEvent e)
{
try
{
client.execute("/TMChessServer/logout",username);
}catch(Throwable t)
{
JOptionPane.showMessageDialog(ChessUI.this,t.toString());
}
System.exit(0);
}
});

//now all is setup ,lets start the timer
timer.start();
}
public void showUI()
{
this.setVisible(true);
}
private void sendInvitation(String toUsername)
{
System.out.println("Invitaion send to: "+toUsername);
try
{
client.execute("/TMChessServer/inviteUser",username,toUsername);
JOptionPane.showMessageDialog(this,"Invitation for game has sent to: "+toUsername);
}catch(Throwable t)
{
JOptionPane.showMessageDialog(this,t.toString());
}
}
//inner class starts

class AvailableMembersListModel extends AbstractTableModel
{
private String title[]={"Members"," "};
private java.util.List<String> members;
private java.util.List<JButton> inviteButtons;
private boolean awaitingInvitationReply;
public AvailableMembersListModel()
{
this.awaitingInvitationReply=false;
this.members=new LinkedList<>();
this.inviteButtons=new LinkedList<>();
}
public int getRowCount()
{
return this.members.size();
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
if(c==0)return String.class;
return JButton.class;
}
public boolean isCellEditable(int r,int c)
{
if(c==1)return true;
return false;
}
public Object getValueAt(int r,int c)
{
if(c==0)return this.members.get(r);
return this.inviteButtons.get(r);
}
public void setValueAt(Object data,int row,int col)
{
if(col==1)
{
JButton button=this.inviteButtons.get(row);
String text=(String)data;
button.setText(text);
if(text.equalsIgnoreCase("Invited"))
{
this.awaitingInvitationReply=true;
for(JButton inviteButton:this.inviteButtons)inviteButton.setEnabled(false);
this.fireTableDataChanged();
ChessUI.this.sendInvitation(this.members.get(row));
}
if(text.equalsIgnoreCase("Invite"))
{
this.awaitingInvitationReply=false;
for(JButton inviteButton:this.inviteButtons)inviteButton.setEnabled(true);
this.fireTableDataChanged();
}
}
}
public void setMembers(java.util.List<String> members)
{
if(this.awaitingInvitationReply)return;
this.members=members;
this.inviteButtons.clear();
for(int i=0;i<this.members.size();i++)this.inviteButtons.add(new JButton("Invite"));
fireTableDataChanged();
}
}
class AvailableMembersListButtonRenderer implements TableCellRenderer
{
public Component getTableCellRendererComponent(JTable table,Object value,boolean a,boolean b,int r,int c)
{
return (JButton)value;
}
}
class AvailableMembersListButtonCellEditor extends DefaultCellEditor
{
private boolean isClicked;
private int row,col;
private JButton button;
private ActionListener actionListener;
AvailableMembersListButtonCellEditor()
{
super(new JCheckBox());
//button.setOpaque(true);
this.actionListener=new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
fireEditingStopped();
}
};
}
public Component getTableCellEditorComponent(JTable table,Object value,boolean a,int r,int c)
{
System.out.println("getTableCellEditorComponent get called");
this.row=r;
this.col=c;
this.button=(JButton)availableMembersListModel.getValueAt(row,col);
this.button.removeActionListener(this.actionListener);
this.button.addActionListener(this.actionListener);
button.setForeground(Color.black);
button.setBackground(UIManager.getColor("Button.background"));
button.setOpaque(true);
this.isClicked=true;
return button;
}
public Object getCellEditorValue()
{
System.out.println("getCellEditorValue get called");
return "Invited";
}
public boolean stopCellEditing()
{
this.isClicked=false;
return super.stopCellEditing();
}
public void fireEditingStopped()
{
super.fireEditingStopped();
}
} 
} //ChessUI class ends
