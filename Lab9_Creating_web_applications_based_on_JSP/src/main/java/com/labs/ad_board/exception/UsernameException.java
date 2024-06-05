package com.labs.ad_board.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UsernameException extends RuntimeException {

    public UsernameException() {
        super();
    }

    public UsernameException(String message) {
        super(message);
        log.error(message);
    }

    public UsernameException(String message, Exception e) {
        super(message, e);
    }

    public UsernameException(Exception e) {
        super(e);
    }
}
