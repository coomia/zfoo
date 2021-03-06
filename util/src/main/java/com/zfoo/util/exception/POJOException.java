package com.zfoo.util.exception;

/**
 * 不是一个POJO对象，POJO对象不应该继承别的类
 *
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 07.12 12:11
 */
public class POJOException extends RuntimeException {

    private static final String MESSAGE = "not a POJO object, can't extend other object";

    private static final String HYPHEN = "-";//连接号，连接号与破折号的区别是，连接号的两头不用空格

    private static final String LEFT_SQUARE_BRACKET = "[";//左方括号

    private static final String RIGHT_SQUARE_BRACKET = "]";//右方括号

    public POJOException() {
        super(POJOException.MESSAGE);
    }

    public POJOException(String message) {
        super(POJOException.MESSAGE + POJOException.HYPHEN + POJOException.LEFT_SQUARE_BRACKET + message + POJOException.RIGHT_SQUARE_BRACKET);
    }

}
