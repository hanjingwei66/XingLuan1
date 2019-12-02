package com.shuojie.utils.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class SingleResult<T> {
    private static volatile SingleResult singleResult;

    private String code;
    private String message;
    private String status;
    private String command;
    private T data;
    private List list;
    private Integer type;
    public static SingleResult getInstance() {
        if(singleResult==null){
            synchronized (SingleResult.class){
                if(singleResult==null){
                    singleResult=new SingleResult();
                }
            }
        }
        return singleResult;
    }
    private SingleResult() {

    }


    private SingleResult(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    private SingleResult(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public SingleResult(String  code, String message, String command, T data) {
        this.code = code;
        this.message = message;
        this.command = command;
        this.data = data;
    }
    public SingleResult(String code, String message, String command) {
        this.code = code;
        this.message = message;
        this.command = command;
    }
    public SingleResult(String code, String message, String command,List<T> list) {
        this.code = code;
        this.message = message;
        this.command = command;
        this.list=list;
    }
    /**
     * 创建一个带有<b>状态</b>、<b>消息</b>和<b>数据</b>的结果对象.
     *
     * @param status
     *            状态
     * @param message
     *            消息内容
     * @param data
     *            数据
     * @return 结构数据
     */
    public static <T> SingleResult<T> buildResult(Status status, String message, T data) {

        return new SingleResult<T>(status.getCode(), message, data);
    }

    public static <T> SingleResult<T> buildResult(Status status, String message) {
        return new SingleResult<T>(status.getCode(), message);
    }
    public static <T> SingleResult<T> buildResult(Status status, String message,String command) {
        return new SingleResult<T>(status.getCode(), message);
    }

//    public static <T> SingleResult<T> buildResult(Status status, T data) {
//        return new SingleResult<T>(status.getCode(), status.getReason(), data);
//    }

    public static <T> SingleResult<T> buildResult(Status status) {
        return new SingleResult<T>(status.getCode(), status.getReason());
    }


    public static <T> SingleResult<T> buildResult(Status status ,String message,String command,T data) {
        return new SingleResult<T>(status.getCode(), status.getReason(),command,data);
    }

    public enum Status {

        /**
         * 状态
         */
       OJBK("200","SUCCESS","api"),OK("200", "SUCCESS"), BAD_REQUEST("400", "错误的请求"), UNAUTHORIZED("401", "禁止访问"), NOT_FOUND("404",
                "没有可用的数据"),  PWD_EEOR("300",
                "密码错误"),EXIT("403",
                "已经存在"),INTERNAL_SERVER_ERROR("500",
                "服务器遇到了一个未曾预料的状况"), SERVICE_UNAVAILABLE("503", "服务器当前无法处理请求"), ERROR("9999", "数据不能为空");
        /**
         * 状态码,长度固定为6位的字符串.
         */
        private String code;

        /**
         * 错误信息.
         */
        private String reason;
        /**
         * 指令（提供前端使用）
         */
        private String command;

        Status(String code, String reason) {
            this.code = code;
            this.reason = reason;
        }
        Status(String code, String reason,String command) {
            this.code = code;
            this.reason = reason;
            this.command =command;
        }
        public String getCode() {
            return code;
        }

        public String getReason() {
            return reason;
        }
        public String getCommand(){
            return command;
        }
        @Override
        public String toString() {
            return code + ": " + reason;
        }

    }

}
