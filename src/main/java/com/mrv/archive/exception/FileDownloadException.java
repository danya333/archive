package com.mrv.archive.exception;

public class FileDownloadException extends RuntimeException {

    public FileDownloadException(
            final String message
    ) {
        super(message);
    }

}