package com.app.mediator.service;

import com.app.mediator.proxy.CallProxy;
import com.app.mediator.requst.UserLoginRequst;
import com.app.mediator.requst.UserLogoutRequest;
import com.app.mediator.requst.UserRegisterRequest;
import com.app.mediator.response.DefaultResponse;
import com.app.mediator.server.response.LoginServerResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginUserServiceImpl implements LoginUserService{

    @Autowired
    ServerApi serverApi;
    @Autowired
    HttpServletRequest request;
    @Override
    public DefaultResponse saveUser(UserRegisterRequest userRegisterRequest) {
        String authToken = request.getHeader("Authorization");
        return CallProxy.doCall(userRegisterRequest, serverApi.userLoginRegisterUrl().trim(),authToken,new DefaultResponse(), DefaultResponse.class);
    }

    @Override
    public LoginServerResponse loginUser(UserLoginRequst userLoginReq) {
        return CallProxy.doCall(userLoginReq,serverApi.userLoginUrl().trim(),"",new LoginServerResponse(),LoginServerResponse.class);
    }

    @Override
    public DefaultResponse logoutUser() {
        String authToken = request.getHeader("Authorization");
        return CallProxy.doCall("", serverApi.userLogoutUrl().trim(), authToken,new DefaultResponse(), DefaultResponse.class);
    }
}
