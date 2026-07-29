package com.project.referral.exception;

public class GeminiParsingException extends RuntimeException {
    public GeminiParsingException(String message) { super(message); }
    public GeminiParsingException(String message, Throwable cause) { super(message, cause); }
}
