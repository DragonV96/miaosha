package com.glw.miaosha.exception.result;

/**
 * @author glw
 * @date 2019/4/30 10:13
 */
public class Result<T> {
    private int code;       // 数据状态码
    private String msg;     // 状态码对应文字信息
    private T data;         // 数据

    /**
     * 成功时调用
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(T data) {
        return new Result<T>(data);
    }

    /**
     * 失败时调用
     * @param <T>
     * @return
     */
    public static <T> Result<T> error(CodeMsg codeMsg) {
        return new Result<T>(codeMsg);
    }

    private Result(T data){
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }

    private Result(CodeMsg codeMsg){
        if (codeMsg == null) {
            return;
        }
        this.code = codeMsg.getCode();
        this.msg = codeMsg.getMsg();
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}
