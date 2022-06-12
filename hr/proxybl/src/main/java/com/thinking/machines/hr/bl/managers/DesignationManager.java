package com.thinking.machines.hr.bl.managers;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.network.common.*;
import com.thinking.machines.network.common.exceptions.*;
import com.thinking.machines.network.client.*;
import java.util.*;
public class DesignationManager implements DesignationManagerInterface
{
private static DesignationManager designationManager=null;
private DesignationManager()throws BLException
{
}
public static DesignationManagerInterface getDesignationManager()throws BLException
{
if(designationManager==null)designationManager=new DesignationManager();
return designationManager;
}
public void addDesignation(DesignationInterface designation)throws BLException
{
BLException blException;
blException=new BLException();
if(designation==null)
{
blException.setGenericException("Designation required");
throw blException;
}
int code=designation.getCode();
String title=designation.getTitle();
if(code!=0)
{
blException.addException("code","Code should be zero");
}
if(title==null)
{
blException.addException("title","Title required");
title="";
}
else
{
title=title.trim();
if(title.length()==0)
{
blException.addException("title","Title Required");
}
}
if(blException.hasExceptions())
{
throw blException;
}
Request request=new Request();
request.setManager(Managers.getManagerType(Managers.DESIGNATION));
request.setAction(Managers.getAction(Managers.Designation.ADD_DESIGNATION));
request.setArguments(designation);
NetworkClient networkClient=new NetworkClient();
try
{
Response response=networkClient.send(request);
if(response.hasException())
{
blException=(BLException)response.getException();
throw blException;
}
//Following two lines are very important
DesignationInterface d=(DesignationInterface)response.getResult();
designation.setCode(d.getCode());
}catch(NetworkException networkException)
{
blException.setGenericException(networkException.getMessage());
throw blException;
}
}
public void updateDesignation(DesignationInterface designation)throws BLException
{

BLException blException;
blException=new BLException();
if(designation==null)
{
blException.setGenericException("Designation required");
throw blException;
}
int code=designation.getCode();
String title=designation.getTitle();
if(code<=0)
{
blException.addException("code","Invalid Code: "+code);
}
if(title==null)
{
blException.addException("title","Title required");
title="";
}
else
{
title=title.trim();
if(title.length()==0)
{
blException.addException("title","Title Required");
}
}
if(blException.hasExceptions())
{
throw blException;
}

Request request=new Request();
request.setManager(Managers.getManagerType(Managers.DESIGNATION));
request.setAction(Managers.getAction(Managers.Designation.UPDATE_DESIGNATION));
request.setArguments(designation);
NetworkClient networkClient=new NetworkClient();
try
{
Response response=networkClient.send(request);
if(response.hasException())
{
blException=(BLException)response.getException();
throw blException;
}
}catch(NetworkException networkException)
{
blException.setGenericException(networkException.getMessage());
throw blException;
}
}
public void removeDesignation(int code)throws BLException
{
if(code<=0)
{
BLException blException;
blException=new BLException();
blException.addException("code","Invalid Code: "+code);
throw blException;
}
Request request=new Request();
request.setManager(Managers.getManagerType(Managers.DESIGNATION));
request.setAction(Managers.getAction(Managers.Designation.REMOVE_DESIGNATION));

request.setArguments(code);
NetworkClient networkClient=new NetworkClient();
try
{
Response response=networkClient.send(request);
if(response.hasException())
{
BLException blException;
blException=(BLException)response.getException();
throw blException;
}
}catch(NetworkException networkException)
{
BLException blException;
blException=new BLException();
blException.setGenericException(networkException.getMessage());
throw blException;
}
}

public DesignationInterface getDesignationByCode(int code)throws BLException
{
if(code<=0)
{
BLException blException;
blException=new BLException();
blException.addException("code","Invalid Code: "+code);
throw blException;
}
Request request=new Request();
request.setManager(Managers.getManagerType(Managers.DESIGNATION));
request.setAction(Managers.getAction(Managers.Designation.GET_DESIGNATION_BY_CODE));

request.setArguments(code);
NetworkClient networkClient=new NetworkClient();
try
{
Response response=networkClient.send(request);
if(response.hasException())
{
BLException blException;
blException=(BLException)response.getException();
throw blException;
}
DesignationInterface designation;
designation=(DesignationInterface)response.getResult();
return designation;
}catch(NetworkException networkException)
{
BLException blException;
blException=new BLException();
blException.setGenericException(networkException.getMessage());
throw blException;
}
}
public DesignationInterface getDesignationByTitle(String title)throws BLException
{
if(title==null)
{
BLException blException;
blException=new BLException();
blException.addException("title","Title required");
throw blException;
}
title=title.trim();
if(title.length()==0)
{
BLException blException;
blException=new BLException();
blException.addException("title","Title required");
throw blException;
}
Request request=new Request();
request.setManager(Managers.getManagerType(Managers.DESIGNATION));
request.setAction(Managers.getAction(Managers.Designation.GET_DESIGNATION_BY_TITLE));

request.setArguments(title);
NetworkClient networkClient=new NetworkClient();
try
{
Response response=networkClient.send(request);
if(response.hasException())
{
BLException blException;

blException=(BLException)response.getException();
throw blException;
}
DesignationInterface designation;
designation=(DesignationInterface)response.getResult();
return designation;
}catch(NetworkException networkException)
{
BLException blException;
blException=new BLException();
blException.setGenericException(networkException.getMessage());
throw blException;
}
}
public int getDesignationCount()
{
Request request=new Request();
request.setManager(Managers.getManagerType(Managers.DESIGNATION));
request.setAction(Managers.getAction(Managers.Designation.GET_DESIGNATION_COUNT));
NetworkClient networkClient=new NetworkClient();
try
{
Response response=networkClient.send(request);
Integer count;
count=(Integer)response.getResult();
return count.intValue(); //no need to call intValue(), unboxing will be done implicitly
}catch(NetworkException networkException)
{
throw new RuntimeException(networkException.getMessage());
}
}
public boolean designationCodeExists(int code)
{

if(code<=0)
{
return false;
}
Request request=new Request();
request.setManager(Managers.getManagerType(Managers.DESIGNATION));
request.setAction(Managers.getAction(Managers.Designation.DESIGNATION_CODE_EXISTS));

request.setArguments(code);
NetworkClient networkClient=new NetworkClient();
try
{
Response response=networkClient.send(request);
Boolean exists;
exists=(Boolean)response.getResult();
return exists; //unboxing will be done implicitly
}catch(NetworkException networkException)
{
throw new RuntimeException(networkException.getMessage());
}
}
public boolean designationTitleExists(String title)
{

if(title==null)
{
return false;
}
title=title.trim();
if(title.length()==0)
{
return false;
}
Request request=new Request();
request.setManager(Managers.getManagerType(Managers.DESIGNATION));
request.setAction(Managers.getAction(Managers.Designation.DESIGNATION_TITLE_EXISTS));

request.setArguments(title);
NetworkClient networkClient=new NetworkClient();
try
{
Response response=networkClient.send(request);
Boolean exists=(Boolean)response.getResult();
return exists;
}catch(NetworkException networkException)
{
throw new RuntimeException(networkException.getMessage());
}
}
public Set<DesignationInterface>getDesignations()
{
Request request=new Request();
request.setManager(Managers.getManagerType(Managers.DESIGNATION));
request.setAction(Managers.getAction(Managers.Designation.GET_DESIGNATIONS));
NetworkClient networkClient=new NetworkClient();
try
{
Response response=networkClient.send(request);
Set<DesignationInterface> designations;
designations=(Set<DesignationInterface>)response.getResult();
return designations;
}catch(NetworkException networkException)
{
throw new RuntimeException(networkException.getMessage());
}
}
}