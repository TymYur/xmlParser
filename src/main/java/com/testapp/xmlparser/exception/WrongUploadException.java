package com.testapp.xmlparser.exception;

public class WrongUploadException extends RuntimeException {

    private static final String MESSAGE = "Wrong file structure of the file.";

    public WrongUploadException(Exception ex) {
        super(MESSAGE, ex);
    }

}
