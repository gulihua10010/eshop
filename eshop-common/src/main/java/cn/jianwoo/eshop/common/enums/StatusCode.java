package cn.jianwoo.eshop.common.enums;

public enum StatusCode {
    Success(200,"OK"),
    Fail(-1,"Error"),
    InvalidParams(0,"Invalid Params");
    private  Integer status;
    private String msg;

    StatusCode(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public Integer getStatus() {

        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    StatusCode() {

    }
}
