package com.dslcode.core.exception;

/**
 * Created by dongsilin on 2017/4/12.
 */
public class ExceptionHandler extends RuntimeException {

    private String message;

    public ExceptionHandler(String message) {
        super(message);
        this.message = message;
    }


    public ExceptionHandler() {
        super();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
