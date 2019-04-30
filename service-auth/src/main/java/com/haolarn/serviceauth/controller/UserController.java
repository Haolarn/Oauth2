package com.haolarn.serviceauth.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.Principal;

@RestController
@RequestMapping("/users")
//提供一个方法进行token的验证
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/current",method = RequestMethod.GET)
    public Principal getUser(Principal principal){
        logger.info(">>>>>>>>>>>>>>>>>>>>>>");
        logger.info(principal.toString());
        logger.info(">>>>>>>>>>>>>>>>>>>>>>");
        return principal;
    }

    @RequestMapping(value = "test", method = RequestMethod.GET)
    public String test(Authentication user, HttpServletRequest request) throws UnsupportedEncodingException {
        String s = user.getPrincipal().toString();
        String name = user.getName();
        String header = request.getHeader("Authorization");
        String token = StringUtils.substringAfter(header, "bearer");
        /*ConsParams.Auth.GET_SIGNING_KEY*/
        Claims body = Jwts.parser().setSigningKey("internet_plus".getBytes("UTF-8"))
                .parseClaimsJwt(token).getBody();
        String username = (String) body.get("username");
        logger.info("解析token获取到的username为{}",username);
        logger.info("从Authentication里获取到的username为{}",s);
        logger.info("从Authentication里获取到的username为{}",name);
        return s;
    }
}
