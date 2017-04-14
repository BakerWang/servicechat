package com.xzg.exption;


public class Assert {
	private Assert(){
		
	}
	public static void asse(boolean bool,String message){
		if(!bool)
			throw new BusinessException(message);
	}
}
