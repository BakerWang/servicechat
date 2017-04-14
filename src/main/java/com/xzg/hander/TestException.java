package com.xzg.hander;

import java.io.IOException;

import com.xzg.exption.BusinessException;

public class TestException {

	public static void testException() throws Exception{
		throw new IOException("ccc");
	}
	public static int testException2(){
		
		return 1/0;
	}
}
