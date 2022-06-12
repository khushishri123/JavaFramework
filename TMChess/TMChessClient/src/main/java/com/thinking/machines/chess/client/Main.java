package com.thinking.machines.chess.client;
import com.thinking.machines.nframework.client.*;
public class Main
{
public static void main(String args[])
{
String username=args[0];
String password=args[1];
try
{
NFrameworkClient client=new NFrameworkClient();
boolean authentic=(boolean)client.execute("/TMChessServer/authenticateMember",username,password);
if(authentic)
{
//System.out.println("Cool, I am authentic member");
ChessUI chessUI=new ChessUI(username);
chessUI.showUI();
}
else
{
System.out.println("Invalid Username/Password");
}
}catch(Throwable t)
{
//System.out.println("Error in Main");
System.out.println(t.getMessage());
}
}
}
