package com.labs.ad_board.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IdException extends RuntimeException {

    public IdException() {
        super();
    }

    public IdException(String message) {
        super(message);
        log.error(message);
    }

    public IdException(String message, Exception e) {
        super(message, e);
    }

    public IdException(Exception e) {
        super(e);
    }
}
