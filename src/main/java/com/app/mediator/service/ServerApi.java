package com.app.mediator.service;

public interface ServerApi {

    String userLoginUrl();

    String userLogoutUrl();

    String userLoginRegisterUrl();

    String expenseTrackerLookupUrl();

    String expenseTrackerCreateUrl();

    String expenseTrackerUpdateUrl();

    String expenseTrackerDelete();

    String graphDetails();
}
