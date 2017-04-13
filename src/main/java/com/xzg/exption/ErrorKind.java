package com.xzg.exption;

public enum ErrorKind {
	NULL_OBJ("xzgO001","对象为空"),
    ERROR_ADD_USER("xzgO002","添加用户失败"),
    UNKNOWN_ERROR("xzgO999","系统繁忙，请稍后再试....");
	
    private String value;
    private String desc;

    private ErrorKind(String value, String desc) {
        this.setValue(value);
        this.setDesc(desc);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "[" + this.value + "]" + this.desc;
    }
}
