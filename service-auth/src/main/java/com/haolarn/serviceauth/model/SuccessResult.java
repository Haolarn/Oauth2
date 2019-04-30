package com.haolarn.serviceauth.model;

//封装调用成功的结果
public class SuccessResult {
    //正常时候返回值
    //{"access_token":"75ee6e81-5085-4d94-8d37-4d0daf785532",
    // "token_type":"bearer",
    // "refresh_token":"8f4ec528-d986-4860-970e-6b22b7e3dd5e",
    // "expires_in":39333,"scope":"server"}
    private String access_token;
    private String token_type;
    private String refresh_token;
    private String expires_in;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }
}
