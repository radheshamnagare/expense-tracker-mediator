package com.app.mediator.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name="response-status")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseStatus {

    @XmlElement(name="error-code")
    @JsonProperty("error-code")
    String errorCode;

    @XmlElement(name="error-status")
    @JsonProperty("error-status")
    String errorStatus;

    @XmlElement(name="error-description")
    @JsonProperty("error-description")
    String errorDescription;

    @XmlElement(name="session-id")
    @JsonProperty("session-id")
    String sessionId;

    @XmlElement(name="token")
    @JsonProperty("token")
    String token;
}
