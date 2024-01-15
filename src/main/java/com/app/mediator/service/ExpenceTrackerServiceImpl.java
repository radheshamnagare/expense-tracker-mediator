package com.app.mediator.service;

import com.app.mediator.common.CommonValidator;
import com.app.mediator.proxy.CallProxy;
import com.app.mediator.requst.ExpenseTrackerDetailsRequest;
import com.app.mediator.requst.ExpenseTrackerListReq;
import com.app.mediator.response.DefaultResponse;
import com.app.mediator.server.response.ExpenseTrackerDetailsResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenceTrackerServiceImpl implements ExpenseTrackerService{

    @Autowired
    ServerApi serverApi;
    @Autowired
    HttpServletRequest request;
    @Override
    public ExpenseTrackerDetailsResponse expenseTrackerDetails(ExpenseTrackerListReq req) {
        String authToken = request.getHeader("Authorization");
        authToken = CommonValidator.isEmpty(authToken)?"":authToken;
        return CallProxy.doCall(req,serverApi.expenseTrackerLookupUrl().trim(),authToken.trim(),new ExpenseTrackerDetailsResponse(), ExpenseTrackerDetailsResponse.class);
    }

    @Override
    public DefaultResponse createExpenseTrackerDetails(ExpenseTrackerDetailsRequest expenseTrackerDetailsRequest) {
        String authToken = request.getHeader("Authorization");
        authToken = CommonValidator.isEmpty(authToken)?"":authToken;
        return CallProxy.doCall(expenseTrackerDetailsRequest, serverApi.expenseTrackerCreateUrl().trim(),authToken,new DefaultResponse(), DefaultResponse.class );
    }

    @Override
    public DefaultResponse updateExpenseTrackerDetails(ExpenseTrackerDetailsRequest expenseTrackerDetailsRequest) {
        String authToken = request.getHeader("Authorization");
        authToken = CommonValidator.isEmpty(authToken)?"":authToken;
        return CallProxy.doCall(expenseTrackerDetailsRequest, serverApi.expenseTrackerUpdateUrl().trim(),authToken,new DefaultResponse(), DefaultResponse.class );
    }
    @Override
    public DefaultResponse deleteExpenseTrackerDetails(ExpenseTrackerDetailsRequest expenseTrackerDetailsRequest) {
        String authToken = request.getHeader("Authorization");
        authToken = CommonValidator.isEmpty(authToken)?"":authToken;
        return CallProxy.doCall(expenseTrackerDetailsRequest,serverApi.expenseTrackerDelete().trim(),authToken,new DefaultResponse(), DefaultResponse.class);
    }


}
