package com.app.dethloff.rest.error;

import lombok.Data;
import lombok.NoArgsConstructor;

public record ErrorResponse(
        int status,
        String message,
        Long timeStamp
) { }
