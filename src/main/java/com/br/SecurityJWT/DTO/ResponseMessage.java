package com.br.SecurityJWT.DTO;

import org.springframework.http.HttpStatusCode;

public record ResponseMessage(
    String message,
    HttpStatusCode status
) {}
