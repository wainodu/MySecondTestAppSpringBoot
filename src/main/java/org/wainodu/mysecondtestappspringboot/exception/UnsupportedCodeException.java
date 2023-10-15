package org.wainodu.mysecondtestappspringboot.exception;

public class UnsupportedCodeException extends Exception{
    public UnsupportedCodeException() {
        super("uid = 123");
    }
}
