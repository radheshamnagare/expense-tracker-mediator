package com.app.mediator.service;


import com.app.mediator.requst.ExpenseTrackerDetailsRequest;
import com.app.mediator.requst.ExpenseTrackerListReq;
import com.app.mediator.response.DefaultResponse;
import com.app.mediator.server.response.ExpenseTrackerDetailsResponse;

public interface ExpenseTrackerService {
    ExpenseTrackerDetailsResponse expenseTrackerDetails(ExpenseTrackerListReq req);

    DefaultResponse createExpenseTrackerDetails(ExpenseTrackerDetailsRequest expenseTrackerDetailsRequest);

    DefaultResponse updateExpenseTrackerDetails(ExpenseTrackerDetailsRequest expenseTrackerDetailsRequest);

    DefaultResponse deleteExpenseTrackerDetails(ExpenseTrackerDetailsRequest expenseTrackerDetailsRequest);

}
