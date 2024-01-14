package com.app.mediator.controller;

import com.app.mediator.common.ConstantPool;
import com.app.mediator.modal.ManageExpenseTracker;
import com.app.mediator.requst.ExpenseTrackerDetailsRequest;
import com.app.mediator.requst.ExpenseTrackerListReq;
import com.app.mediator.response.DefaultApiResponse;
import com.app.mediator.response.ExpenseTrackerDetailsResponse;
import com.app.mediator.service.ExpenseTrackerService;
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
@RequestMapping("/expensetrackerdetails")
public class ExpenseTrackerController {
    private static final Logger LOGGER = LogManager.getLogger(ExpenseTrackerController.class);
    @Autowired
    ExpenseTrackerService expenseTrackerService;
    @Autowired
    HttpServletRequest request;
    @Autowired
    ConfigurableApplicationContext appContex;

    @RequestMapping(value = "/lookup", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<ExpenseTrackerDetailsResponse> expenseTrackerDetails(@RequestBody ExpenseTrackerListReq expenseTrackerListReq) {
        ExpenseTrackerDetailsResponse response = null;
        try {
            LOGGER.printf(Level.INFO, "Entry in expenseTrackerDetails()");
            ManageExpenseTracker manageExpenseTracker = appContex.getBean(ManageExpenseTracker.class);
            manageExpenseTracker.setRequest(request);
            manageExpenseTracker.setExpenseTrackerService(expenseTrackerService);
            response = manageExpenseTracker.manageExpenseTrackerDetails(expenseTrackerListReq);
            LOGGER.printf(Level.INFO, "Exit from expenseTrackerDetails()");
        } catch (Exception exception) {
            LOGGER.printf(Level.ERROR, "Exception in expenseTrackerDetails(),[%1$s]", exception.toString());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<DefaultApiResponse> createExpenseDetails(@RequestBody ExpenseTrackerDetailsRequest expenseTrackerDetailsRequest) {
        DefaultApiResponse response = null;
        try {
            LOGGER.printf(Level.INFO, "Entry in updateExpenseDetails()");
            ManageExpenseTracker manageExpenseTracker = appContex.getBean(ManageExpenseTracker.class);
            manageExpenseTracker.setRequest(request);
            manageExpenseTracker.setExpenseTrackerService(expenseTrackerService);
            response = manageExpenseTracker.manageExpenseTracker(expenseTrackerDetailsRequest, ConstantPool.ACTION_ADD);
            LOGGER.printf(Level.INFO, "Exit from updateExpenseDetails()");
        } catch (Exception ex) {
            response = new DefaultApiResponse();
            LOGGER.printf(Level.ERROR, "Exception in updateExpenseDetails(),[%1$s]", ex.toString());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<DefaultApiResponse> updateExpenseDetails(@RequestBody ExpenseTrackerDetailsRequest expenseTrackerDetailsRequest) {
        DefaultApiResponse response = null;
        try {
            LOGGER.printf(Level.INFO, "Entry in updateExpenseDetails()");
            ManageExpenseTracker manageExpenseTracker = appContex.getBean(ManageExpenseTracker.class);
            manageExpenseTracker.setRequest(request);
            manageExpenseTracker.setExpenseTrackerService(expenseTrackerService);
            response = manageExpenseTracker.manageExpenseTracker(expenseTrackerDetailsRequest, ConstantPool.ACTION_UPDATE);
            LOGGER.printf(Level.INFO, "Exit from updateExpenseDetails()");
        } catch (Exception ex) {
            response = new DefaultApiResponse();
            LOGGER.printf(Level.ERROR, "Exception in updateExpenseDetails(),[%1$s]", ex.toString());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<DefaultApiResponse> deleteExpenseDetails(@RequestBody ExpenseTrackerDetailsRequest expenseTrackerDetailsRequest) {
        DefaultApiResponse response = null;
        try {
            LOGGER.printf(Level.INFO, "Entry in updateExpenseDetails()");
            ManageExpenseTracker manageExpenseTracker = appContex.getBean(ManageExpenseTracker.class);
            manageExpenseTracker.setRequest(request);
            manageExpenseTracker.setExpenseTrackerService(expenseTrackerService);
            response = manageExpenseTracker.manageExpenseTracker(expenseTrackerDetailsRequest, ConstantPool.ACTION_DELETE);
            LOGGER.printf(Level.INFO, "Exit from updateExpenseDetails()");
        } catch (Exception ex) {
            response = new DefaultApiResponse();
            LOGGER.printf(Level.ERROR, "Exception in updateExpenseDetails(),[%1$s]", ex.toString());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
