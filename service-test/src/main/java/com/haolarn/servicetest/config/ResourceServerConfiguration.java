package com.haolarn.servicetest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
//设置为资源服务器
@EnableResourceServer
//spring Security默认是禁用注解的，开启注解，来判断用户对某个控制层的方法是否具有访问权限
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    //配置要保护的资源
    @Override
    public void configure(HttpSecurity http) throws Exception {
        //不允许csrf伪造请求
        http.csrf().disable()
        //拦截请求需要进行token验证
        .authorizeRequests()
                .antMatchers("/order/**").authenticated(); // 配置order访问控制，必须认证后才可以访问
    }
}
