package com.app.dethloff.error;

import lombok.Data;
import lombok.NoArgsConstructor;

public record ErrorResponse(
        int status,
        String message,
        Long timeStamp
) { }
