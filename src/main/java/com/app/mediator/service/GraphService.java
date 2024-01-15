package com.app.mediator.service;


import com.app.mediator.requst.ExpenseTrackerGraphReq;
import com.app.mediator.server.response.ExpenseTrackerGraphResponse;

public interface GraphService {

    ExpenseTrackerGraphResponse graphDetails(ExpenseTrackerGraphReq req);
}
