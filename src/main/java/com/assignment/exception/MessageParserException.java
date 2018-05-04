package com.assignment.exception;

public class MessageParserException extends Exception {
    private String message;

    public MessageParserException() {
    }

    public MessageParserException(String message) {
        super(message);
    }

    public MessageParserException(Throwable cause) {
        super(cause);
    }

    public MessageParserException(String message, Throwable cause) {
        super(message, cause);
    }


}
