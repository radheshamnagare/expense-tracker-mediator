package com.app.mediator.controller;
import com.app.mediator.modal.ManageGraph;
import com.app.mediator.requst.ExpenseTrackerGraphReq;
import com.app.mediator.response.MadiatorExpenseTrackerGraphResponse;
import com.app.mediator.service.GraphService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/graph")
public class GraphExpenseTracker {
    private final static Logger LOGGER = LogManager.getLogger(GraphExpenseTracker.class);
    @Autowired
    GraphService graphService;
    @Autowired
    HttpServletRequest request;
    @Autowired
    ConfigurableApplicationContext appContext;

    @RequestMapping(value = "/lookup" ,method = RequestMethod.POST,produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<MadiatorExpenseTrackerGraphResponse> graphDetails(@RequestBody ExpenseTrackerGraphReq expenseTrackerGraphReq){
        MadiatorExpenseTrackerGraphResponse reponse=null;
        try{
            LOGGER.printf(Level.INFO,"Entry in graphDetails()");
            ManageGraph manageGraph = appContext.getBean(ManageGraph.class);
            manageGraph.setRequest(request);
            manageGraph.setGraphService(graphService);
            reponse = manageGraph.graphDetails(expenseTrackerGraphReq);
            LOGGER.printf(Level.INFO,"Exit from graphDetails()");
        }catch (Exception exception){
            LOGGER.printf(Level.ERROR,"Exception in graphDetails(),[%1$s]",exception.toString());
        }
        return new ResponseEntity<>(reponse, HttpStatus.OK);
    }
}
