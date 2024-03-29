package com.app.mediator.response;


import com.app.mediator.bean.ExpenseTrackerBean;
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
@XmlRootElement(name="expense-tracker-details-response")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MeditaorExpenseTrackerDetailsResponse {

    @XmlElement(name="expense-tracker-details")
    @JsonProperty("expense-tracker-details")
    List<ExpenseTrackerBean> expenseTrackerBeanList;

    @XmlElement(name="status")
    @JsonProperty("status")
    ResponseStatus status;

    @XmlElement(name="fail")
    @JsonProperty("fail")
    List<FailRespose> fail;
}
