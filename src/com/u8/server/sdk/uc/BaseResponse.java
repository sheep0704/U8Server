package com.u8.server.sdk.uc;

/**
 * 接口响应类。
 */
public class BaseResponse {
	protected long id;
	protected ResponseState state;
	protected Object data;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ResponseState getState() {
		return state;
	}

	public void setState(ResponseState state) {
		this.state = state;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
