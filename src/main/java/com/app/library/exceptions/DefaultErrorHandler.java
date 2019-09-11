package com.app.library.exceptions;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class DefaultErrorHandler extends RuntimeException {
    private final int httpErrorCode;
    private final String errorMessage;
}
