package com.haolarn.serviceauth.units;

import com.haolarn.serviceauth.model.ResultRep;

public class ResultUnits {
    public static ResultRep<Object> error(Integer code, String msg){
        ResultRep<Object> result = new ResultRep<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static ResultRep<Object> success(Integer code, String msg, Object data){
        ResultRep<Object> result = new ResultRep<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
}
