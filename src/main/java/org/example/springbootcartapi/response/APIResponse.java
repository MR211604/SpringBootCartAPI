package org.example.springbootcartapi.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class APIResponse {
    private String message;
    private Object data;
}
