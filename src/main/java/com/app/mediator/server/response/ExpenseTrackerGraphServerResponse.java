package com.app.mediator.server.response;

import com.app.mediator.bean.FailRespose;
import com.app.mediator.bean.GraphDetails;
import com.app.mediator.bean.ResponseBase;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@XmlRootElement(name="expense-tracker-graph-response")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExpenseTrackerGraphServerResponse extends ResponseBase {

    @XmlElement(name="graph-details")
    @JsonProperty("graph-details")
    List<GraphDetails> graphDetails;

    @XmlElement(name="fail")
    @JsonProperty("fail")
    List<FailRespose> fail;
}
