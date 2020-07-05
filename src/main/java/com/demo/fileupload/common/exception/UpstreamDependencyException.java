package com.demo.fileupload.common.exception;

public class UpstreamDependencyException extends RuntimeException {

    public UpstreamDependencyException() {
    }

    public UpstreamDependencyException(String message) {
        super(message);
    }

    public UpstreamDependencyException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpstreamDependencyException(Throwable cause) {
        super(cause);
    }

    public UpstreamDependencyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
