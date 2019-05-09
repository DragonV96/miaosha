package com.glw.miaosha.exception;

import com.glw.miaosha.result.CodeMsg;

/**
 * Create by glw
 * 2019/5/10 0:35
 */
public class GlobalException extends RuntimeException {

    private static final long serialVersionUID = 1l;

    private CodeMsg codeMsg;

    public GlobalException(CodeMsg codeMsg) {
        super(codeMsg.toString());
        this.codeMsg = codeMsg;
    }

    public CodeMsg getCodeMsg() {
        return codeMsg;
    }
}
