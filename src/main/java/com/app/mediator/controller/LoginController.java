package com.app.mediator.controller;

import com.app.mediator.modal.ManageLogin;
import com.app.mediator.requst.UserLoginRequst;
import com.app.mediator.requst.UserLogoutRequest;
import com.app.mediator.requst.UserRegisterRequest;
import com.app.mediator.response.DefaultApiResponse;
import com.app.mediator.response.LoginResponse;
import com.app.mediator.service.LoginUserService;
import jakarta.servlet.http.Cookie;
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
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/user")
public class LoginController{

    final static Logger LOGGER = LogManager.getLogger(LoginController.class);
    @Autowired
    HttpServletRequest request;
    @Autowired
    LoginUserService loginUserService;
    @Autowired
    ConfigurableApplicationContext contex;

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<LoginResponse> loginUser(@RequestBody UserLoginRequst userLoginReq) {
        LoginResponse response = null;
        try {
            LOGGER.printf(Level.INFO, "Entry in loginUser");
            ManageLogin manageLogin = contex.getBean(ManageLogin.class);
            manageLogin.setLoginUserService(loginUserService);
            manageLogin.setRequest(request);
            response = manageLogin.manageLogin(userLoginReq);
            LOGGER.printf(Level.INFO, "Exit from loginUser");
        } catch (Exception ex) {
            LOGGER.printf(Level.ERROR, "Exception in loginUser,[%1$s]", ex.toString());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<DefaultApiResponse> logOut() {
        DefaultApiResponse response = null;
        try {
            Cookie u[] = request.getCookies();
            LOGGER.printf(Level.INFO, "Entry in logOut");
            ManageLogin manageLogin = contex.getBean(ManageLogin.class);
            manageLogin.setLoginUserService(loginUserService);
            manageLogin.setRequest(request);
            response = manageLogin.manageLogOut();
            LOGGER.printf(Level.INFO, "Exit from logOut");
        } catch (Exception ex) {
            LOGGER.printf(Level.ERROR, "Exception in logOut,[%1$s]", ex.toString());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<DefaultApiResponse> registerUser(@RequestBody UserRegisterRequest userRegisterRequest) {
        DefaultApiResponse response = null;
        try {
            LOGGER.printf(Level.INFO, "Entry in registerUser");
            ManageLogin manageLogin = contex.getBean(ManageLogin.class);
            manageLogin.setLoginUserService(loginUserService);
            manageLogin.setRequest(request);
            response = manageLogin.manageRegisterUser(userRegisterRequest);
            LOGGER.printf(Level.INFO, "Exit from registerUser");
        } catch (Exception ex) {
            LOGGER.printf(Level.ERROR, "Exception in registerUser,[%1$s]", ex.toString());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    public ResponseEntity<DefaultApiResponse> test(){
        DefaultApiResponse response = new DefaultApiResponse();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
