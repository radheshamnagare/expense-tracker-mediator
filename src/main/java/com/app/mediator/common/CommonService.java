package com.app.mediator.common;

import com.app.mediator.bean.FailRespose;
import com.app.mediator.bean.ResponseStatus;
import com.app.mediator.bean.SystemError;
import com.app.mediator.response.DefaultApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.UUID;

public class CommonService {
    private final static Logger LOGGER = LogManager.getLogger(CommonService.class);
    private static String buildMessage(String var0,String  var1,String msgBody){

        try{
            if(!CommonValidator.isEmpty(var0))
                msgBody = msgBody.replace("{var0}",var0);
            else
                msgBody = msgBody.replace("{var0}","");
            if(!CommonValidator.isEmpty(var1))
                msgBody = msgBody.replace("{var1}",var1);
            else
                msgBody = msgBody.replace("{var1}","");
        }catch (Exception ex){
            LOGGER.printf(Level.ERROR,"Exception in buildMessage,[%1$s]",ex.toString());
        }
        return msgBody.trim();
    }
    public static String getErrorDescription(String errorCode,String var1,String var2){
        String msg="";
        try{
            switch (errorCode){
                case ConstantPool.ERROR_CODE_INVALID:
                  msg = buildMessage(var1,var2, ConstantPool.DESC_INVALID_MESG);
                  break;
                case ConstantPool.ERROR_CODE_REQUIRED:
                  msg = buildMessage(var1,var2, ConstantPool.DESC_REQUIRED_MESG);
                  break;
                case ConstantPool.ERROR_CODE_DUPLICATE:
                    msg = buildMessage(var1,var2,ConstantPool.DESC_DUPLICATE_MESG);
                    break;
                case ConstantPool.ERROR_CODE_UNKNOWN:
                    msg = buildMessage(var1,var2,ConstantPool.DESC_UNKNOWN_MESG);
                    break;
                case ConstantPool.ERROR_CODE_SUCCESS:
                    msg = buildMessage(var1,var2,ConstantPool.DESC_SUCCESS_MESG);
                    break;
                case ConstantPool.ERROR_CODE_FAIL:
                    msg = buildMessage(var1,var2,ConstantPool.DESC_FAIL_MESG);
                    break;
            }
        }catch (Exception ex){
            LOGGER.printf(Level.ERROR,"Exception in getErrorDescription [%1$s]",ex.toString());
        }
        return msg;
    }

    public static SystemError getSystemError(String errorCode, String var1, String var2){
        SystemError error = new SystemError();
        try{
            error.setErrorCode(errorCode);
            switch (errorCode){
                case ConstantPool.ERROR_CODE_SUCCESS : error.setErrorStatus(ConstantPool.DESC_SUCCESS);
                  break;
                case ConstantPool.ERROR_CODE_INVALID: error.setErrorStatus(ConstantPool.DESC_INVALID);
                  break;
                case ConstantPool.ERROR_CODE_REQUIRED: error.setErrorStatus(ConstantPool.DESC_REQUIRED);
                  break;
                case ConstantPool.ERROR_CODE_FAIL: error.setErrorStatus(ConstantPool.DESC_FAIL);
                 break;
                case ConstantPool.ERROR_CODE_UNKNOWN:error.setErrorStatus(ConstantPool.DESC_UNKNOWN);
                 break;
                case ConstantPool.ERROR_CODE_DUPLICATE:error.setErrorStatus(ConstantPool.DESC_DUPLICATE);
                 break;
            }
            error.setErrorDescription(getErrorDescription(errorCode,var1,var2));
        }catch (Exception ex){
            LOGGER.printf(Level.ERROR,"Exception in getSystemError,[%1$s]",ex.toString());
        }
        return error;
    }
    public static String getToken(){
        return UUID.randomUUID().toString();
    }

    public static String getSessionId(HttpServletRequest request){
        HttpSession currentSession = request.getSession(true);
        if(currentSession!=null)
            return currentSession.getId();
        return null;
    }

    public static boolean isValidSessionToken(String sessionId,String token){
        try{
           if(CommonValidator.isEmpty(sessionId) || CommonValidator.isEmpty(token))
               return false;
        }catch (Exception ex){
            LOGGER.printf(Level.ERROR,"Exception in isValidSessionToken,[%1$s]",ex.toString());
        }
        return true;
    }


    public static ResponseStatus getResponseStatus(SystemError error, String sessionId, String token){
        ResponseStatus status = new ResponseStatus();
        try {
            status.setErrorCode(error.getErrorCode());
            status.setErrorStatus(error.getErrorStatus());
            status.setErrorDescription(error.getErrorDescription());
            status.setSessionId(sessionId);
            status.setToken(token);
        }catch (Exception exception){
            LOGGER.printf(Level.ERROR,"Exception in getResponseStatus()",exception.toString());
        }
        return status;
    }

    public static DefaultApiResponse getDefaultApiResponse(String success, SystemError error, List<FailRespose> fail, String sessionId, String token){
        DefaultApiResponse response = new DefaultApiResponse();
        try{
            LOGGER.printf(Level.INFO,"Entry in getDefaultResponse()");
            response.setSuccess(success);
            ResponseStatus status = getResponseStatus(error,sessionId,token);
            response.setStatus(status);
            response.setFail(fail);
            LOGGER.printf(Level.INFO,"Exit from getDefaultResponse()");
        }catch (Exception exception){
            LOGGER.printf(Level.ERROR,"Exception in getDefaultResponse(),[%1$s]", exception.toString());
        }
        return response;
    }
}
