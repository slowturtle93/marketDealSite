package com.market.server.error;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorMsg {
	
	private String msg;       // 메세지
	private String errorCode; // 에러코드
	
	/**
	 * 생성자
	 * 
	 * @param msg
	 * @param errorCode
	 */
	public ErrorMsg(String msg, String errorCode) {
		super();
		this.msg       = msg;
		this.errorCode = errorCode;
	}
}
