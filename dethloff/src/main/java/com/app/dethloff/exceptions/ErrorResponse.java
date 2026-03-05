package com.app.dethloff.exceptions;

public record ErrorResponse(
        int status,
        String message,
        Long timeStamp
) { }
