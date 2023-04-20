package com.completablefuture.exception;

import java.io.IOException;

public class FileParseException extends Throwable {
    public FileParseException(String msg, IOException exception) {
        super(msg,exception);
    }
}
