package com.shi.orm.exception;

public class BaseOrmException extends RuntimeException {
    public BaseOrmException(String reason) {
        super(reason);
    }

    public BaseOrmException(String reason, Throwable cause) {
        super(reason, cause);
    }
}
