package com.app.mediator.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemError {

    String errorCode;
    String errorStatus;
    String errorDescription;
}
