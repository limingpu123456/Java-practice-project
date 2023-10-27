package org.example.base;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JSONResponse {
    private boolean success;
    private String code;
    private String message;
    private Object data;
}
