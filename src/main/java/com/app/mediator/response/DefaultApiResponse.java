package com.app.mediator.response;

import com.app.mediator.bean.FailRespose;
import com.app.mediator.bean.ResponseStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@XmlRootElement(name = "default-api-response")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DefaultApiResponse {

    @XmlElement(name="success")
    @JsonProperty("success")
    String success;

    @XmlElement(name="status")
    @JsonProperty("status")
    ResponseStatus status;

    @XmlElement(name="fail")
    @JsonProperty("fail")
    List<FailRespose> fail;
}
