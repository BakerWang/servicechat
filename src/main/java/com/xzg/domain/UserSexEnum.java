package com.xzg.domain;

public enum UserSexEnum {
	MAN("0","男"), WOMAN("1","女");
	private String value;
	private String desc;
	private UserSexEnum(String value,String desc){
		this.setDesc(desc);
		this.setValue(value);
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
