package com.xzg.service;

import org.apache.log4j.Logger;
public interface PublicInfo {
	public  static Logger logger = Logger.getLogger(PublicInfo.class);
	public static final String SUCCESS = "success";
	public static final String ERROR = "error";
	// 1.定义枚举类型
    public enum Light {
        // 利用构造函数传参
        RED(1), GREEN(3), YELLOW(2);
        // 定义私有变量
        private int nCode;
        // 构造函数，枚举类型只能为私有
        private Light(int _nCode) {
            this.nCode = _nCode;
        }
        @Override
        public String toString() {
            return String.valueOf(this.nCode);
        }
    }
}
