package com.youssef.gamal.websockets.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdditionMessageReq {

    private Integer number;
    private Integer addedValue;
    private Integer iterations;
}
