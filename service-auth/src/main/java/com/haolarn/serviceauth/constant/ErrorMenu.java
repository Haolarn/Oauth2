package com.haolarn.serviceauth.constant;

public enum ErrorMenu {
    CLIENT_ID_ERRO("invalid_client","客户端凭证错误"),
    USER_EROOR("invalid_grant","用户账号密码错误"),
    GRANT_TYPE("unsupported_grant_type","授权类型错误"),
    SCOPE_ERROR("invalid_scope","scope作用域错误");

    private final String error;
    private final String error_description;

    ErrorMenu(String error, String error_description) {
        this.error = error;
        this.error_description = error_description;
    }

    public String getError() {
        return error;
    }

    public String getError_description() {
        return error_description;
    }
    //根据error错误编号获取error错误信息
    public static String getValueByCode(String error){
        for(ErrorMenu errorMenu:ErrorMenu.values()){
            if(error.equals(errorMenu.getError())){
                return errorMenu.getError_description();
            }
        }
        return  null;
    }

    public static void main(String[] args) {
        String invalid_client = getValueByCode("invalid_client");
        System.out.println(invalid_client);
    }
}
