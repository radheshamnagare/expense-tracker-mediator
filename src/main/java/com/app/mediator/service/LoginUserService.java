package com.app.mediator.service;

import com.app.mediator.requst.UserLoginRequst;
import com.app.mediator.requst.UserLogoutRequest;
import com.app.mediator.requst.UserRegisterRequest;
import com.app.mediator.response.DefaultResponse;
import com.app.mediator.server.response.LoginServerResponse;

public interface LoginUserService {

    DefaultResponse saveUser(UserRegisterRequest userRegisterRequest);

    LoginServerResponse loginUser(UserLoginRequst userLoginReq);

    DefaultResponse logoutUser();

}
