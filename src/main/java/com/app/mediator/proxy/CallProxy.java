package com.app.mediator.proxy;

import com.app.mediator.bean.ResponseBase;
import com.app.mediator.bean.SystemError;
import com.app.mediator.common.CommonService;
import com.app.mediator.common.CommonValidator;
import com.app.mediator.common.ConstantPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;


public class CallProxy {

    private static final Logger LOGGER = LogManager.getLogger(CallProxy.class);

    public static <I, R extends ResponseBase> R doCall(I requestBean, String url,String token, R response, Class<R> responseClass) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            LOGGER.printf(Level.INFO, "Entry in doCall()");
            HttpHeaders headers= new HttpHeaders();
            headers.setBearerAuth(token);
            HttpEntity<I> requestEntity =  new HttpEntity<>(requestBean,headers);
            response = restTemplate.postForEntity(url, requestEntity, responseClass).getBody();
            if(CommonValidator.isEmpty(response.getErrorCode())){
                SystemError error = CommonService.getSystemError(ConstantPool.ERROR_CODE_UNKNOWN,"","");
                response.setErrorCode(error.getErrorCode());
                response.setErrorStatus(error.getErrorStatus());
                response.setErrorDescription(error.getErrorDescription());
            }
            LOGGER.printf(Level.INFO, "Exit from doCall()");
        } catch (Exception exception) {
            LOGGER.printf(Level.ERROR, "Exception in doCall(),[%1$s]", exception.toString());
        }
        return response;
    }


}
