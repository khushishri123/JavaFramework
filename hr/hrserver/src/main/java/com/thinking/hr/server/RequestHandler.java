package com.thinking.machines.hr.server;
import com.thinking.machines.network.server.*;
import com.thinking.machines.network.common.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
public class RequestHandler implements RequestHandlerInterface
{
private DesignationManagerInterface designationManager;
public RequestHandler()
{
try
{
designationManager=DesignationManager.getDesignationManager();
}catch(BLException blException)
{
//do nothing
}
}
public Response process(Request request)
{
Response response=new Response();
String manager=request.getManager();
String action=request.getAction();
Object []arguments=request.getArguments();
if(manager.equals("DesignationManager"))
{
if(designationManager==null)
{
//will decide later on
}
if(action.equals("getDesignations"))
{
Object result=designationManager.getDesignations();
response.setSuccess(true);
response.setResult(result);
response.setException(null);
}//getDesignations ends

if(action.equals("addDesignation"))
{
try
{
DesignationInterface designation=(DesignationInterface)arguments[0];
designationManager.addDesignation(designation);
response.setSuccess(true);
response.setResult(designation);
response.setException(false);
}catch(Exception exception)
{
response.setSuccess(false);
response.setResult(null);
response.setException(true);
}
}

if(action.equals("removeDesignation"))
{
try
{
int code=(int)arguments[0];
designationManager.removeDesignation(code);
response.setSuccess(true);
response.setResult(null);
response.setException(false);
}catch(Exception exception)
{
response.setSuccess(false);
response.setResult(null);
response.setException(true);
}
}

if(action.equals("updateDesignation"))
{
try
{
DesignationInterface designation=(DesignationInterface)arguments[0];
designationManager.updateDesignation(designation);
response.setSuccess(true);
response.setResult(null);
response.setException(false);
}catch(Exception exception)
{
response.setSuccess(false);
response.setResult(null);
response.setException(true);
}
}


if(action.equals("getDesignationByCode"))
{
try
{
int code=(int)arguments[0];
Object result=designationManager.getDesignationByCode(code);
response.setSuccess(true);
response.setResult(result);
response.setException(false);
}catch(Exception exception)
{
response.setSuccess(false);
response.setResult(null);
response.setException(true);
}
}

if(action.equals("getDesignationByTitle"))
{
try
{
String title=(String)arguments[0];
Object result=designationManager.getDesignationByTitle(title);
response.setSuccess(true);
response.setResult(result);
response.setException(false);
}catch(Exception exception)
{
response.setSuccess(false);
response.setResult(null);
response.setException(true);
}
}

if(action.equals("getDesignationCount"))
{
try
{
Object result=designationManager.getDesignationCount();
response.setSuccess(true);
response.setResult(result);
response.setException(false);
}catch(Exception exception)
{
response.setSuccess(false);
response.setResult(null);
response.setException(true);
}
}

if(action.equals("designationCodeExists"))
{
try
{
int code=(int)arguments[0];
Object result=designationManager.designationCodeExists(code);
response.setSuccess(true);
response.setResult(result);
response.setException(false);
}catch(Exception exception)
{
response.setSuccess(false);
response.setResult(null);
response.setException(true);
}
}
if(action.equals("designationTitleExists"))
{
try
{
String title=(String)arguments[0];
Object result=designationManager.designationTitleExists(title);
response.setSuccess(true);
response.setResult(result);
response.setException(false);
}catch(Exception exception)
{
response.setSuccess(false);
response.setResult(null);
response.setException(true);
}
}


}//designationManager ends

return response;
}//process method ends
}