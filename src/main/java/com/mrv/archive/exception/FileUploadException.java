package com.mrv.archive.exception;

public class FileUploadException extends RuntimeException {

    public FileUploadException(
            final String message
    ) {
        super(message);
    }

}