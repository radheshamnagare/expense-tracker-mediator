package com.app.mediator.service;


import com.app.mediator.requst.ExpenseTrackerGraphReq;
import com.app.mediator.server.response.ExpenseTrackerGraphServerResponse;

public interface GraphService {

    ExpenseTrackerGraphServerResponse graphDetails(ExpenseTrackerGraphReq req);
}
