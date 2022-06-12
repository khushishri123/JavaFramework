package com.thinking.machines.chess.server;
import com.thinking.machines.chess.server.dl.*;
import java.util.*;
import com.thinking.machines.nframework.server.*;
import com.thinking.machines.nframework.server.annotations.*;
import com.thinking.machines.chess.common.*;
@Path("/TMChessServer")
public class TMChessServer 
{
static private Map<String,Member> members;
static private Set<String> loggedInMembers;
static private Set<String> playingMembers;
static private Map<String,List<Message>> inboxes;
static private Map<String,Game> games; //Here the key is the random unique id
static 
{
populateDataStructures();
}
public TMChessServer()
{
}
static private void populateDataStructures()
{
members=new HashMap<>();
MemberDAO memberDAO;
memberDAO=new MemberDAO();
List<MemberDTO> dlMembers=memberDAO.getAll();
Member member;
for(MemberDTO memberDTO:dlMembers)
{
member=new Member();
member.username=memberDTO.username;
member.password=memberDTO.password;
members.put(member.username,member);
}
loggedInMembers=new HashSet<>();
playingMembers=new HashSet<>();
inboxes=new HashMap<>();
games=new HashMap<>();
}
//create services to enable client to perform login,logout action,e.t.c
@Path("/authenticateMember")
public boolean isMemberAuthenticate(String username,String password)
{
Member member=members.get(username);
if(member==null)return false;
boolean b=password.equals(member.password);
if(b) //if it is an authenticate user then we have to add that user in loggedInMembers 
{
loggedInMembers.add(username);
}
return b;
}
@Path("/logout")
public void logout(String username)
{
loggedInMembers.remove(username);
}

@Path("/getMembers")
public List<String> getAvailbleMembers(String username)
{
List<String> availableMembers=new LinkedList<>();
for(String u:loggedInMembers)
{
if(playingMembers.contains(u)==false && username.equals(u)==false)
{
availableMembers.add(u);
}
}
return availableMembers;
}

@Path("/inviteUser")
public void inviteUser(String fromUsername,String toUsername)
{
Message message=new Message();
message.fromUsername=fromUsername;
message.toUsername=toUsername;
message.type=MESSAGE_TYPE.CHALLENGE;
List<Message> messages=inboxes.get(toUsername); //toUsername ke against jitene bhi messages hai unki list aa jayegi
if(messages==null)
{
messages=new LinkedList<Message>();
inboxes.put(toUsername,messages); //is block ke against geya matlab abhi tak toUsername ke against koi linked list nahi thi but ab humene uske against ek linked list of message type objects create kar diya hai
}
messages.add(message);
}

//This method is called by both players because the one who has sent the invitation wnats to know taht whether his invation is accepted or not ,so he will call this method and see in message type that whether his invitation is accepted or not
//player 2 will call this method to know that whether there is any invitation available 
//we have taken list of Messages because there can be multiple players who have sent request to player2 
//and there can be multiple replies from diffrent players to player 1
public List<Message> getMessage(String username) 
{
List<Message> messages=inboxes.get(username);
if(messages!=null && messages.size()>0)
{
inboxes.put(username,new LinkedList<Message>());
}
return messages;
}

public String getGameId(String username)
{
return "abc";
}

public boolean canIPlay(String gameId,String username)
{
return false;
}

public void sumbitMove(String username,byte piece,int fromX,int fromY,int toX,int toY)
{
}
public Move getOpponentMove(String username)
{
return null;
}
}
