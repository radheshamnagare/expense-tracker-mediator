package com.app.mediator.modal;

import com.app.mediator.bean.FailRespose;
import com.app.mediator.bean.ResponseStatus;
import com.app.mediator.bean.SystemError;
import com.app.mediator.common.CommonService;
import com.app.mediator.common.CommonValidator;
import com.app.mediator.common.ConstantPool;
import com.app.mediator.common.ErrorConstatnt;
import com.app.mediator.requst.UserLoginRequst;
import com.app.mediator.requst.UserRegisterRequest;
import com.app.mediator.response.DefaultApiResponse;
import com.app.mediator.response.DefaultResponse;
import com.app.mediator.response.MediatorLoginResponse;
import com.app.mediator.server.response.LoginResponse;
import com.app.mediator.service.LoginUserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class ManageLogin {
    private final static Logger LOGGER = LogManager.getLogger(ManageLogin.class);
    private LoginUserService loginUserService;
    HttpServletRequest request;

    private String authToken;

    private boolean isValidLoginRequest(UserLoginRequst userLoginRequst, List<FailRespose> fail) {
        FailRespose failRespose;
        try {
            if (CommonValidator.isEmpty(userLoginRequst.getUserId())) {
                failRespose = new FailRespose();
                failRespose.setId(ErrorConstatnt.DESC_USER_ID);
                failRespose.setReason(CommonService.getErrorDescription(ConstantPool.ERROR_CODE_REQUIRED, ErrorConstatnt.DESC_USER_ID, ""));
                fail.add(failRespose);
                return false;
            } else if (CommonValidator.isEmpty(userLoginRequst.getPassword())) {
                failRespose = new FailRespose();
                failRespose.setId(ErrorConstatnt.DESC_PASSWORD);
                failRespose.setReason(CommonService.getErrorDescription(ConstantPool.ERROR_CODE_REQUIRED, ErrorConstatnt.DESC_PASSWORD, ""));
                fail.add(failRespose);
                return false;
            } else if (CommonValidator.isEmpty(userLoginRequst.getDomain())) {
                failRespose = new FailRespose();
                failRespose.setId(ErrorConstatnt.DESC_DOMAIN);
                failRespose.setReason(CommonService.getErrorDescription(ConstantPool.ERROR_CODE_REQUIRED, ErrorConstatnt.DESC_DOMAIN, ""));
                fail.add(failRespose);
                return false;
            }
        } catch (Exception ex) {
            LOGGER.printf(Level.ERROR, "Exception in isValidLoginRequest", ex.toString());
        }
        return true;
    }

    public static MediatorLoginResponse getLoginResponse(String success, SystemError error, List<FailRespose> fail, String sessionId, String token, String authToken, String userName) {
        MediatorLoginResponse response = new MediatorLoginResponse();
        try {
            LOGGER.printf(Level.INFO, "Entry in getLoginResponse()");
            response.setSuccess(success);
            ResponseStatus status = CommonService.getResponseStatus(error, sessionId, token);
            response.setStatus(status);
            response.setFail(fail);
            response.setJwtToken(authToken);
            response.setUserName(userName);
            LOGGER.printf(Level.INFO, "Exit from getLoginResponse()");
        } catch (Exception exception) {
            LOGGER.printf(Level.ERROR, "Exception in getLoginResponse(),[%1$s]", exception.toString());
        }
        return response;
    }

    public MediatorLoginResponse manageLogin(UserLoginRequst userLoginRequest) {
        MediatorLoginResponse response;
        SystemError error;
        String apiKey = CommonService.getSessionId(request);
        String token = CommonService.getToken();
        List<FailRespose> fail = new ArrayList<>();
        try {
            LOGGER.printf(Level.INFO, "Entry in manageLogin");
            if (!CommonService.isValidSessionToken(apiKey, token)) {
                error = CommonService.getSystemError(ConstantPool.ERROR_CODE_INVALID, ErrorConstatnt.DESC_SESSION_TOKEN, "");
                response = getLoginResponse(String.valueOf(1), error, fail, apiKey, token, authToken,"");
            } else if (isValidLoginRequest(userLoginRequest, fail)) {
                LoginResponse loginResponse = loginUserService.loginUser(userLoginRequest);
                if (CommonValidator.isEmpty(loginResponse.getSuccess())) {
                    error = CommonService.getSystemError(ConstantPool.ERROR_CODE_FAIL, "", "");
                } else
                    error = new SystemError(loginResponse.getErrorCode(), loginResponse.getErrorStatus(), loginResponse.getErrorDescription());
                response = getLoginResponse(loginResponse.getSuccess(), error, fail, apiKey, token, loginResponse.getJwtToken(), loginResponse.getUserName());
            } else {
                error = CommonService.getSystemError(ConstantPool.ERROR_CODE_FAIL, "", "");
                response = getLoginResponse(String.valueOf(0), error, fail, apiKey, token, "","");
            }
            LOGGER.printf(Level.INFO, "Exit from manageLogin");
        } catch (Exception ex) {
            error = CommonService.getSystemError(ConstantPool.ERROR_CODE_UNKNOWN, "", "");
            response = getLoginResponse(String.valueOf(0), error, fail, apiKey, token, "","");
            LOGGER.printf(Level.ERROR, "Exception in manageLogin", ex.toString());
        }
        return response;
    }


    public DefaultApiResponse manageLogOut() {
        DefaultApiResponse response;
        List<FailRespose> fail = new ArrayList<>();
        try {
            LOGGER.printf(Level.INFO, "Entry in manageLogOut");

                DefaultResponse serverResponse = loginUserService.logoutUser();
                SystemError error = new SystemError(serverResponse.getErrorCode(), serverResponse.getErrorStatus(), serverResponse.getErrorDescription());
                response = CommonService.getDefaultApiResponse(serverResponse.getSuccess(), error, fail, "", "");
                if(serverResponse==null){
                    error = CommonService.getSystemError(ConstantPool.ERROR_CODE_UNKNOWN, "", "");
                    response = CommonService.getDefaultApiResponse(String.valueOf(0), error, fail, "", "");
                }
            LOGGER.printf(Level.INFO, "Exit from manageLogOut");
        } catch (Exception ex) {
            SystemError error = CommonService.getSystemError(ConstantPool.ERROR_CODE_UNKNOWN, "", "");
            response = CommonService.getDefaultApiResponse(String.valueOf(1), error, null, "", "");
            LOGGER.printf(Level.ERROR, "Exception in manageLogOut,[%1$s]", ex.toString());
        }
        return response;
    }

    private boolean isValidUserInfo(String firstName, String lastName, String emaiId, List<FailRespose> fail) {
        FailRespose failRespose;
        try {
            LOGGER.printf(Level.INFO, "Entry in checkUserInfo");
            if (CommonValidator.isEmpty(firstName)) {
                failRespose = new FailRespose();
                failRespose.setId(ErrorConstatnt.DESC_FIRST_NAME);
                failRespose.setReason(CommonService.getErrorDescription(ConstantPool.ERROR_CODE_REQUIRED, ErrorConstatnt.DESC_FIRST_NAME, ""));
                fail.add(failRespose);
                return false;
            } else if (CommonValidator.isEmpty(lastName)) {
                failRespose = new FailRespose();
                failRespose.setId(ErrorConstatnt.DESC_LASTNAME);
                failRespose.setReason(CommonService.getErrorDescription(ConstantPool.ERROR_CODE_REQUIRED, ErrorConstatnt.DESC_LASTNAME, ""));
                fail.add(failRespose);
                return false;
            } else if (!CommonValidator.isValidEmailId(emaiId)) {
                failRespose = new FailRespose();
                failRespose.setId(ErrorConstatnt.DESC_EMAIL_ID);
                failRespose.setReason(CommonService.getErrorDescription(ConstantPool.ERROR_CODE_INVALID, ErrorConstatnt.DESC_EMAIL_ID, emaiId));
                fail.add(failRespose);
                return false;
            }
            LOGGER.printf(Level.INFO, "Exit from checkUserInfo");
        } catch (Exception ex) {
            LOGGER.printf(Level.ERROR, "Exception checkUserInfo,[%1$s]", ex.toString());
        }
        return true;
    }

    private boolean isValidUserName(String userName, List<FailRespose> fail) {
        FailRespose failRespose;
        try {
            LOGGER.printf(Level.INFO, "Entry in isValidUserName");
            if (CommonValidator.isEmpty(userName)) {
                failRespose = new FailRespose();
                failRespose.setId(ErrorConstatnt.DESC_USER_ID);
                failRespose.setReason(CommonService.getErrorDescription(ConstantPool.ERROR_CODE_REQUIRED, ErrorConstatnt.DESC_USER_ID, ""));
                fail.add(failRespose);
                return false;
            } else if (userName.length() < 3) {
                failRespose = new FailRespose();
                failRespose.setId(ErrorConstatnt.DESC_USER_ID);
                failRespose.setReason(CommonService.getErrorDescription(ConstantPool.ERROR_CODE_INVALID, ErrorConstatnt.DESC_USER_ID, userName));
                return false;
            }
            LOGGER.printf(Level.INFO, "Exit in isValidUserName");
        } catch (Exception ex) {
            LOGGER.printf(Level.ERROR, "Exception in isValidUserName");
        }
        return true;
    }

    private boolean isValidPassword(String password, List<FailRespose> fail) {
        FailRespose failRespose;
        try {
            if (CommonValidator.isEmpty(password)) {
                failRespose = new FailRespose();
                failRespose.setId(ErrorConstatnt.DESC_PASSWORD);
                failRespose.setReason(CommonService.getErrorDescription(ConstantPool.ERROR_CODE_REQUIRED, ErrorConstatnt.DESC_PASSWORD, ""));
                fail.add(failRespose);
                return false;
            } else if (password.length() < 8) {
                failRespose = new FailRespose();
                failRespose.setId(ErrorConstatnt.DESC_PASSWORD);
                failRespose.setReason(CommonService.getErrorDescription(ConstantPool.ERROR_CODE_INVALID, ErrorConstatnt.DESC_PASSWORD, password));
                fail.add(failRespose);
                return false;
            }
        } catch (Exception ex) {
            LOGGER.printf(Level.ERROR, "Entry in isValidPassword,[%1$s]", ex.toString());
        }
        return true;
    }

    private boolean isValidDomain(String domain, List<FailRespose> fail) {
        FailRespose failRespose;
        try {
            LOGGER.printf(Level.INFO, "Entry in isValidDomain");
            if (CommonValidator.isEmpty(domain)) {
                failRespose = new FailRespose();
                failRespose.setId(ErrorConstatnt.DESC_DOMAIN);
                failRespose.setReason(CommonService.getErrorDescription(ConstantPool.ERROR_CODE_REQUIRED, ErrorConstatnt.DESC_DOMAIN, ""));
                fail.add(failRespose);
                return false;
            }
            LOGGER.printf(Level.INFO, "Exit in isValidDomain");
        } catch (Exception ex) {
            LOGGER.printf(Level.ERROR, "Entry in isValidDomain,[%1$s]", ex.toString());
        }
        return true;
    }

    private boolean isValidRequestForRegister(UserRegisterRequest userRegisterRequest, List<FailRespose> fail) {
        try {
            if (!isValidUserInfo(userRegisterRequest.getFirstName(), userRegisterRequest.getLastName(), userRegisterRequest.getEmailAddress(), fail)) {
                return false;
            } else if (!isValidUserName(userRegisterRequest.getUserName(), fail)) {
                return false;
            } else if (!isValidPassword(userRegisterRequest.getPassword(), fail)) {
                return false;
            } else if (!isValidDomain(userRegisterRequest.getDomain(), fail)) {
                return false;
            }
        } catch (Exception ex) {
            LOGGER.printf(Level.ERROR, "Exception in isValidRequestForRegister,[%1$s]", ex.toString());
        }
        return true;
    }

    public DefaultApiResponse manageRegisterUser(UserRegisterRequest userRegisterRequest) {
        DefaultApiResponse response;
        List<FailRespose> fail = new ArrayList<>();
        try {
            if (isValidRequestForRegister(userRegisterRequest, fail)) {
                DefaultResponse registrationComplate = loginUserService.saveUser(userRegisterRequest);
                SystemError error = new SystemError(registrationComplate.getErrorCode(), registrationComplate.getErrorStatus(), registrationComplate.getErrorDescription());
                if (!CommonValidator.isEmpty(registrationComplate.getFail()))
                    fail.addAll(registrationComplate.getFail());
                response = CommonService.getDefaultApiResponse(registrationComplate.getSuccess(), error, fail, "", "");
            } else {
                SystemError error = CommonService.getSystemError(ConstantPool.ERROR_CODE_FAIL, "", "");
                error.setErrorDescription(fail.get(0).getReason());
                response = CommonService.getDefaultApiResponse(String.valueOf(0), error, fail, "", "");
            }
        } catch (Exception ex) {
            SystemError error = CommonService.getSystemError(ConstantPool.ERROR_CODE_UNKNOWN, "", "");
            response = CommonService.getDefaultApiResponse(String.valueOf(0), error, fail, "", "");
            LOGGER.printf(Level.ERROR, "Exception in manageRegisterUser,[%1$s]", ex.toString());
        }
        return response;
    }
}
