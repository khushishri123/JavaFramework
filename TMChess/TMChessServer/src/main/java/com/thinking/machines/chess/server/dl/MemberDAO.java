package com.thinking.machines.chess.server.dl;
import java.util.*;
public class MemberDAO 
{
public List<MemberDTO> getAll()
{
List<MemberDTO> members=new LinkedList<>();
MemberDTO m;
m=new MemberDTO();
m.username="Amit";
m.password="amit";
members.add(m);
m=new MemberDTO();
m.username="Mukesh";
m.password="mukesh";
members.add(m);
m=new MemberDTO();
m.username="Namita";
m.password="namita";
members.add(m);
m=new MemberDTO();
m.username="Peyush";
m.password="peyush";
members.add(m);
m=new MemberDTO();
m.username="Anupam";
m.password="anupam";
members.add(m);
m=new MemberDTO();
m.username="Sundar";
m.password="sundar";
members.add(m);
m=new MemberDTO();
m.username="Sudha";
m.password="sudha";
members.add(m);
m=new MemberDTO();
m.username="Kajal";
m.password="kajal";
members.add(m);
m=new MemberDTO();
m.username="Sweety";
m.password="sweety";
members.add(m);
m=new MemberDTO();
m.username="Ashneer";
m.password="ashneer";
members.add(m);
return members;
}
}