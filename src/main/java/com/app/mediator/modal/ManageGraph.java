package com.app.mediator.modal;

import com.app.mediator.bean.FailRespose;
import com.app.mediator.bean.GraphDetails;
import com.app.mediator.bean.ResponseStatus;
import com.app.mediator.bean.SystemError;
import com.app.mediator.common.CommonService;
import com.app.mediator.common.CommonValidator;
import com.app.mediator.common.ConstantPool;
import com.app.mediator.common.ErrorConstatnt;
import com.app.mediator.requst.ExpenseTrackerGraphReq;
import com.app.mediator.response.ExpenseTrackerGraphResponse;
import com.app.mediator.server.response.ExpenseTrackerGraphServerResponse;
import com.app.mediator.service.GraphService;
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
public class ManageGraph {
    private static final Logger LOGGER = LogManager.getLogger(ManageGraph.class);
    HttpServletRequest request;
    GraphService graphService;

    private ExpenseTrackerGraphResponse getExpenseTrackerGraphResponse(List<GraphDetails> graphDetails, List<FailRespose> fail, SystemError error, String apiKey, String token){
        ExpenseTrackerGraphResponse response = new ExpenseTrackerGraphResponse();
        try{
            LOGGER.printf(Level.INFO,"Entry in getExpenseTrackerGraphResponse()");
            response.setGraphDetails(graphDetails);
            response.setFail(fail);
            ResponseStatus status = CommonService.getResponseStatus(error,apiKey,token);
            response.setStatus(status);
            LOGGER.printf(Level.INFO,"Exit from getExpenseTrackerGraphResponse()");
        }catch (Exception exception){
            LOGGER.printf(Level.ERROR,"Exception in getExpenseTrackerGraphResponse(),[%1$s]",exception.toString());
        }
        return response;
    }

    private boolean isValidExpenseTrackerGraphRequest(ExpenseTrackerGraphReq expenseTrackerGraphReq,List<FailRespose> fail){
        try{
           LOGGER.printf(Level.INFO,"Entry in isValidExpenseTrackerGraphRequest()");
           if(CommonValidator.isEmpty(expenseTrackerGraphReq.getMonth()) && CommonValidator.isEmpty(expenseTrackerGraphReq.getYear())){
               FailRespose failRespose = new FailRespose();
               failRespose.setId(ErrorConstatnt.DESC_MONTH_YEAR);
               failRespose.setReason(CommonService.getErrorDescription(ConstantPool.ERROR_CODE_REQUIRED,ErrorConstatnt.DESC_MONTH_YEAR,""));
               fail.add(failRespose);
               return  false;
           }else if(!CommonValidator.isEmpty(expenseTrackerGraphReq.getMonth()) && CommonValidator.isEmpty(expenseTrackerGraphReq.getYear())){
               FailRespose failRespose = new FailRespose();
               failRespose.setId(ErrorConstatnt.DESC_YEAR);
               failRespose.setReason(CommonService.getErrorDescription(ConstantPool.ERROR_CODE_REQUIRED,ErrorConstatnt.DESC_YEAR,""));
               fail.add(failRespose);
               return  false;
           }
           LOGGER.printf(Level.INFO,"Exit from isValidExpenseTrackerGraphRequest()");
        }catch (Exception exception){
            LOGGER.printf(Level.ERROR,"Exception in isValidExpenseTrackerGraphRequest(),[%1$s]",exception.toString());
        }
        return true;
    }
    public ExpenseTrackerGraphResponse graphDetails(ExpenseTrackerGraphReq expenseTrackerGraphReq){
        ExpenseTrackerGraphResponse response=null;
        String apiKey = CommonService.getSessionId(request);
        String token = CommonService.getToken();
        SystemError error;
        List<FailRespose> fail = new ArrayList<>();
        try{
            LOGGER.printf(Level.INFO,"Entry in graphDetails()");
            if(!CommonService.isValidSessionToken(apiKey,token)){
                error = CommonService.getSystemError(ConstantPool.ERROR_CODE_INVALID,ErrorConstatnt.DESC_SESSION_TOKEN,apiKey+" "+token);
                response = getExpenseTrackerGraphResponse(new ArrayList<>(),fail,error,apiKey,token);
            }else if(isValidExpenseTrackerGraphRequest(expenseTrackerGraphReq,fail)){
                ExpenseTrackerGraphServerResponse serverResponse = graphService.graphDetails(expenseTrackerGraphReq);
                error = new SystemError(serverResponse.getErrorCode(), serverResponse.getErrorStatus() ,serverResponse.getErrorDescription());
                if(!CommonValidator.isEmpty(serverResponse.getFail()))
                    fail.addAll(serverResponse.getFail());
                response = getExpenseTrackerGraphResponse(serverResponse.getGraphDetails(),fail,error,apiKey,token);
            }
            LOGGER.printf(Level.INFO,"Exit from graphDetails()");
        }catch (Exception exception){
            error = CommonService.getSystemError(ConstantPool.ERROR_CODE_UNKNOWN,"","");
            response = getExpenseTrackerGraphResponse(new ArrayList<>(),fail,error,apiKey,token);
            LOGGER.printf(Level.ERROR,"Exception in graphDetails(),[%1$s]",exception.toString());
        }
        return response;
    }
}
