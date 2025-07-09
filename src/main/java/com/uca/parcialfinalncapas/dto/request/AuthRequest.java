package com.uca.parcialfinalncapas.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {
    private String correo;
    private String password;
}
