package com.app.mediator.service;

import com.app.mediator.proxy.CallProxy;
import com.app.mediator.requst.ExpenseTrackerGraphReq;
import com.app.mediator.server.response.ExpenseTrackerGraphResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GraphServiceImpl implements  GraphService{

    @Autowired
    ServerApi serverApi;
    @Autowired
    HttpServletRequest request;

    @Override
    public ExpenseTrackerGraphResponse graphDetails(ExpenseTrackerGraphReq req) {
        String authToken = request.getHeader("Authorization");
        return CallProxy.doCall(req, serverApi.graphDetails().trim(),authToken,new ExpenseTrackerGraphResponse(), ExpenseTrackerGraphResponse.class );
    }
}
