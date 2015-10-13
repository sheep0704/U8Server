package com.u8.server.sdk.uc;
/**
 * sid验证结果响应类。
 */
public class VerifySessionResponse extends BaseResponse {
	
	VerifySessionResponseData data;
	
	public VerifySessionResponseData getData(){
		return this.data;
	}
	public void setData(VerifySessionResponseData data){
		this.data = data;
	}
	

	public class VerifySessionResponseData{
		//最长为32个字符
		private String  accountId;
		//JY：九游
		//PP：PP助手
		private String creator;
		private String nickName;
		public String getAccountId() {
			return accountId;
		}
		public void setAccountId(String accountId) {
			this.accountId = accountId;
		}
		public String getCreator() {
			return creator;
		}
		public void setCreator(String creator) {
			this.creator = creator;
		}
		public String getNickName() {
			return nickName;
		}
		public void setNickName(String nickName) {
			this.nickName = nickName;
		}

	}

}
