package com.u8.server.sdk.uc;

/**
 * 响应状态类。
 */
public class ResponseState {
	/**
	 * 响应码
	 */
	private int code;
	/**
	 * 结果描述
	 */
	private String msg;

	public int getCode() {
		return this.code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
