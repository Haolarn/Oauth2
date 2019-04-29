package com.haolarn.serviceauth.Filter;

import com.alibaba.fastjson.JSONObject;
import com.haolarn.serviceauth.constant.ErrorMenu;
import com.haolarn.serviceauth.model.ErrorResult;
import com.haolarn.serviceauth.model.ResultRep;
import com.haolarn.serviceauth.model.SuccessResult;
import com.haolarn.serviceauth.units.ResultUnits;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        ReponseWrapper wrapperResponse = new ReponseWrapper((HttpServletResponse)response);//转换成代理类
        // 这里只拦截返回，直接让请求过去，如果在请求前有处理，可以在这里处理
        filterChain.doFilter(request, wrapperResponse);
        byte[] content = wrapperResponse.getContent();//获取返回
        //判断是否有值
        if (content.length > 0){
            ResultRep<Object> result = null;
            String str = new String(content, "UTF-8");
            System.out.println("返回值:" + str);
            String ciphertext = null;
            if (str.contains("access_token")){
                SuccessResult successResult = JSONObject.parseObject(str, SuccessResult.class);
                result = ResultUnits.success(200, "获取成功", successResult.getAccess_token());
                returnResult(response, JSONObject.toJSONString(result));
            }else if (str.contains("error")){
                ErrorResult errorResult = JSONObject.parseObject(str, ErrorResult.class);
                String valueByCode = ErrorMenu.getValueByCode(errorResult.getError());
                result = ResultUnits.error(1,valueByCode);
                returnResult(response, JSONObject.toJSONString(result));
            }else {
                ciphertext = str;
                returnResult(response, ciphertext);
            }
        }
    }

    @Override
    public void destroy() {

    }

    public void returnResult(ServletResponse response, String result){
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            out.write(result.getBytes());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
