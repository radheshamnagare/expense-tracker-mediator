package com.app.mediator.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ServerApiImpl implements ServerApi{

    @Value("${url}")
    private String url;

    @Value("${userLoginApi}")
    private String userLoginUrl;
    @Override
    public String userLoginUrl() {
        return url+userLoginUrl;
    }

    @Value("${userLogoutApi}")
    private String userLogoutUrl;
    @Override
    public String userLogoutUrl() {
        return url+userLogoutUrl;
    }

    @Value("${userRegisterApi}")
    private String userLoginRegister;
    @Override
    public String userLoginRegisterUrl() {
        return url+userLoginRegister;
    }

    @Value("${expenseTrackerDetailsApi}")
    private String expenseTrackerLookupUrl;
    @Override
    public String expenseTrackerLookupUrl() {
        return url+expenseTrackerLookupUrl;
    }

    @Value("${expenseTrackerCreateApi}")
    private String expenseTrackerCreateUrl;
    @Override
    public String expenseTrackerCreateUrl() {
        return url+expenseTrackerCreateUrl;
    }

    @Value("${expenseTrackerUpdateApi}")
    private String expenseTrackerUpdateUrl;
    @Override
    public String expenseTrackerUpdateUrl() {
        return url+expenseTrackerUpdateUrl;
    }

    @Value("${expenseTrackerDeleteApi}")
    private String expenseTrackerDeleteUrl;
    @Override
    public String expenseTrackerDelete() {
        return url+expenseTrackerDeleteUrl;
    }

    @Value("${graphDetailApi}")
    private String graphDetailsUrl;
    @Override
    public String graphDetails() {
        return url+graphDetailsUrl;
    }
}
