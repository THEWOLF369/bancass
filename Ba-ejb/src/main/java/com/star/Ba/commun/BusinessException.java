package com.star.Ba.commun;

public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public static Throwable getRootCause(Throwable e) {
        Throwable t = e;
        while (t.getCause() != null) {
            t = t.getCause();
        }
        return t;
    }

    public static BusinessException findBusinessException(Throwable e) {
        Throwable t = e;
        while (t != null) {
            if (t instanceof BusinessException) {
                return (BusinessException) t;
            }
            t = t.getCause();
        }
        return null;
    }
}