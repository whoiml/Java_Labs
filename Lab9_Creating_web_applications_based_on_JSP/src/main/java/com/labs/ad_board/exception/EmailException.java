package com.labs.ad_board.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmailException extends RuntimeException {

    public EmailException() {
        super();
    }

    public EmailException(String message) {
        super(message);
        log.error(message);
    }

    public EmailException(String message, Exception e) {
        super(message, e);
    }

    public EmailException(Exception e) {
        super(e);
    }
}
