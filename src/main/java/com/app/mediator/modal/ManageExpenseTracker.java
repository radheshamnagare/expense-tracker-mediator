package com.app.mediator.modal;

import com.app.mediator.bean.ExpenseTrackerBean;
import com.app.mediator.bean.FailRespose;
import com.app.mediator.bean.ResponseStatus;
import com.app.mediator.bean.SystemError;
import com.app.mediator.common.CommonService;
import com.app.mediator.common.CommonValidator;
import com.app.mediator.common.ConstantPool;
import com.app.mediator.common.ErrorConstatnt;
import com.app.mediator.requst.ExpenseTrackerDetailsRequest;
import com.app.mediator.requst.ExpenseTrackerListReq;
import com.app.mediator.response.DefaultApiResponse;
import com.app.mediator.response.DefaultResponse;
import com.app.mediator.response.ExpenseTrackerDetailsResponse;
import com.app.mediator.server.response.ExpenseTrackerDetailsServerResponse;
import com.app.mediator.service.ExpenseTrackerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class ManageExpenseTracker {
    private final static Logger LOGGER = LogManager.getLogger(ManageExpenseTracker.class);
    private HttpServletRequest request;
    private ExpenseTrackerService expenseTrackerService;

    private ExpenseTrackerDetailsResponse getExpenseTrackerDetailsResponse(List<ExpenseTrackerBean> expenseTrackerBeanList, List<FailRespose> fail, SystemError error, String sessionId, String token){
        ExpenseTrackerDetailsResponse expenseTrackerDetailsResponse = new ExpenseTrackerDetailsResponse();
        try{
            LOGGER.printf(Level.INFO,"Entry in getExpenseTrackerDetailsResponse()");
            expenseTrackerDetailsResponse.setExpenseTrackerBeanList(expenseTrackerBeanList);
            ResponseStatus status = CommonService.getResponseStatus(error,sessionId,token);
            expenseTrackerDetailsResponse.setStatus(status);
            expenseTrackerDetailsResponse.setFail(fail);
            LOGGER.printf(Level.INFO,"Exit from getExpenseTrackerDetailsResponse()");
        }catch (Exception exception){
            LOGGER.printf(Level.ERROR,"Exception in getExpenseTrackerDetailsResponse(),[%1$s]", exception.toString());
        }
        return expenseTrackerDetailsResponse;
    }

    public ExpenseTrackerDetailsResponse manageExpenseTrackerDetails(ExpenseTrackerListReq expenseTrackerListReq){
        ExpenseTrackerDetailsResponse response =null;
        String apiKey = CommonService.getSessionId(request);
        String token = CommonService.getToken();
        SystemError error;
        try{
            LOGGER.printf(Level.INFO,"Entry in manageExpenseTrackerDetails()");
            if(!CommonService.isValidSessionToken(apiKey,token)){
                error = CommonService.getSystemError(ConstantPool.ERROR_CODE_INVALID, ErrorConstatnt.DESC_SESSION_TOKEN,apiKey);
                response = getExpenseTrackerDetailsResponse(null,null,error,apiKey,token);
            }else{
                ExpenseTrackerDetailsServerResponse expenseTrackerDetails =
                        expenseTrackerService.expenseTrackerDetails(expenseTrackerListReq);
                error = CommonService.getSystemError(ConstantPool.ERROR_CODE_SUCCESS,"","");
                response = getExpenseTrackerDetailsResponse(expenseTrackerDetails.getExpenseTrackerBeanList(),null,error,apiKey,token);
            }
            LOGGER.printf(Level.INFO,"Exit from manageExpenseTrackerDetails()");
        }catch (Exception exception){
            error = CommonService.getSystemError(ConstantPool.ERROR_CODE_UNKNOWN,"","");
            response = getExpenseTrackerDetailsResponse(null,null,error,apiKey,token);
            LOGGER.printf(Level.ERROR,"Exception in manageExpenseTrackerDetails(),[%1$s]", exception.toString());
        }
        return response;
    }

    private boolean isValidActionDelete(String action){
        return ConstantPool.ACTION_DELETE.equals(action);
    }

    private boolean isValidActionUpdateDelete(String action){
        return (ConstantPool.ACTION_DELETE.equals(action) ||  ConstantPool.ACTION_UPDATE.equals(action));
    }

    private boolean isValidId(String id,List<FailRespose> fail){
        try{
            FailRespose failRespose =null;
            if(CommonValidator.isEmpty(id)){
                failRespose = getFailRespose(ErrorConstatnt.DESC_ID,ConstantPool.ERROR_CODE_REQUIRED,ErrorConstatnt.DESC_ID,"");
                fail.add(failRespose);
                return false;
            }else if(CommonValidator.isNumeric(id)){
                failRespose = getFailRespose(ErrorConstatnt.DESC_ID,ConstantPool.ERROR_CODE_INVALID,ErrorConstatnt.DESC_ID,id);
                fail.add(failRespose);
                return false;
            }
        }catch (Exception exception){
            LOGGER.printf(Level.ERROR,"Exception in isValidId(),[%1$s]",exception.toString());
        }
        return true;
    }
    private  FailRespose getFailRespose(String id,String errorCode,String val0,String val1){
        FailRespose failRespose=new FailRespose();
        try{
            failRespose.setId(id);
            failRespose.setReason(CommonService.getSystemError(errorCode,val0,val1).getErrorDescription());
        }catch (Exception exception){
            LOGGER.printf(Level.ERROR,"Exception in getFailRespose(),[%1$s]",exception.toString());
        }
        return failRespose;
    }
    private boolean isValidExpenseTrackerRequest(List<ExpenseTrackerBean> req,List<ExpenseTrackerBean> validBean,List<FailRespose> fail,String action){
        try{
            LOGGER.printf(Level.INFO,"Entry in isValidExpenseTrackerRequest()");
            if(CommonValidator.isEmpty(req)){
                FailRespose failRespose = getFailRespose(ErrorConstatnt.DESC_BEAN,ConstantPool.ERROR_CODE_INVALID,ErrorConstatnt.DESC_BEAN,ErrorConstatnt.DESC_NO_DATE_IN_REQUEST);
                fail.add(failRespose);
            }else{
                req.stream().forEach((bean)->{
                    if(isValidActionUpdateDelete(action) && !isValidId(String.valueOf(bean.getId()),fail)) {
                    }else if(CommonValidator.isEmpty(bean.getName())){
                        FailRespose failRespose = getFailRespose(ErrorConstatnt.DESC_EXPENSE_NAME,ConstantPool.ERROR_CODE_REQUIRED,"","");
                        fail.add(failRespose);
                    } else if (!isValidActionDelete(action) && CommonValidator.isEmpty(bean.getDescription())) {
                        FailRespose failRespose = getFailRespose(ErrorConstatnt.DESC_EXPENSE_DESCRIPTION,ConstantPool.ERROR_CODE_REQUIRED,"","");
                        fail.add(failRespose);
                    }else if (!isValidActionDelete(action) && CommonValidator.isNumeric(bean.getAmount())){
                        FailRespose failRespose = getFailRespose(ErrorConstatnt.DESC_EXPENSE_AMOUNT,ConstantPool.ERROR_CODE_REQUIRED,"","");
                        fail.add(failRespose);
                    }else {
                        validBean.add(bean);
                    }
                });
            }
            LOGGER.printf(Level.INFO,"Exit from isValidExpenseTrackerRequest()");
        }catch (Exception exception){
            LOGGER.printf(Level.ERROR,"Exception in isValidExpenseTrackerRequest(),[%1$s]",exception.toString());
        }
        return !validBean.isEmpty();
    }

    public DefaultApiResponse manageExpenseTracker(ExpenseTrackerDetailsRequest expenseTrackerDetailsRequest, String action){
     DefaultApiResponse response =null;
     DefaultResponse serverResponse=null;
     SystemError error;
     List<FailRespose> fail = new ArrayList<>();
     List<ExpenseTrackerBean> validBean = new ArrayList<>();
     String apiKey = CommonService.getSessionId(request);
     String token = CommonService.getToken();
     int success=0;
        try{
            LOGGER.printf(Level.INFO,"Entry in manageExpenseTracker()");
            if(!CommonService.isValidSessionToken(apiKey,token)){
                error = CommonService.getSystemError(ConstantPool.ERROR_CODE_INVALID,ErrorConstatnt.DESC_SESSION_TOKEN,apiKey+" "+token);
                response = CommonService.getDefaultApiResponse(String.valueOf(success),error,fail,apiKey,token);
            }else if(isValidExpenseTrackerRequest(expenseTrackerDetailsRequest.getExpenseTrackerDetails(),validBean,fail,action)){
                int userId= 0;
                switch (action){
                    case ConstantPool.ACTION_ADD :{
                        ExpenseTrackerDetailsRequest serverReq = new ExpenseTrackerDetailsRequest();
                        serverReq.setExpenseTrackerDetails(validBean);
                        serverResponse = expenseTrackerService.createExpenseTrackerDetails(serverReq);
                    }
                    break;
                    case ConstantPool.ACTION_DELETE:{
                        ExpenseTrackerDetailsRequest serverReq = new ExpenseTrackerDetailsRequest();
                        serverReq.setExpenseTrackerDetails(validBean);
                       serverResponse = expenseTrackerService.deleteExpenseTrackerDetails(serverReq);
                    }
                    break;
                    case ConstantPool.ACTION_UPDATE:{
                        ExpenseTrackerDetailsRequest serverReq = new ExpenseTrackerDetailsRequest();
                        serverReq.setExpenseTrackerDetails(validBean);
                        serverResponse = expenseTrackerService.updateExpenseTrackerDetails(serverReq);
                    }
                    break;
                }
                error = new SystemError(serverResponse.getErrorCode(),serverResponse.getErrorStatus(),serverResponse.getErrorDescription());
                response = CommonService.getDefaultApiResponse(String.valueOf(success),error, fail,apiKey,token);
            }
            LOGGER.printf(Level.INFO,"Exit from manageExpenseTracker()");
        }catch (Exception exception){
            error = CommonService.getSystemError(ConstantPool.ERROR_CODE_UNKNOWN,"","");
            response = CommonService.getDefaultApiResponse(String.valueOf(success),error,fail,apiKey,token);
            LOGGER.printf(Level.ERROR,"Exception in manageExpenseTracker(),[%1$s]", exception.toString());
        }
        return response;
    }
}
