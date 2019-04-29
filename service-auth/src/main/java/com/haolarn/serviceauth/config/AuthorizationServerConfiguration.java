package com.haolarn.serviceauth.config;

import com.haolarn.serviceauth.customImpl.MyRedisTokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

//作为配置bean注入spring容器
@Configuration
//来配置OAuth2.0授权服务机制，需要继承AuthorizationServerConfigurerAdapter来重写三个配置bean
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RedisConnectionFactory redisConnectionFactory;


    //用来配置令牌端点(Token Endpoint)的安全约束
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //允许进行表单认证，可以作为表单型参数。
        security.allowFormAuthenticationForClients().tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    //用来配置客户端详情服务（ClientDetailsService），客户端详情信息在这里进行初始化，
    // 你能够把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息。i
    //ClientDetailsService 该框架内嵌了建表sql语句跟提供sql进行查询，可以官网查看，此处可以直接查数据库进行获取clientID跟clientSecret
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //设置加密算法为BCrypt
        String finalSecret = "{bcrypt}" + new BCryptPasswordEncoder().encode("123456");
        // 配置两个客户端，一个用于password认证一个用于client认证
        clients.inMemory().withClient("client_1") //设置clientId为client_1
                .authorizedGrantTypes("client_credentials", "refresh_token")//设置授权类型为：客户端模式 更新令牌模式
                .scopes("select")//作用域为select ，此处可以设置为update ...
                .authorities("oauth2")//设置作者为oauth2
                .secret(finalSecret)//设置clientSecret为
                .and().withClient("client_2") //设置第二个clientId 模式作为密码模式
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("server")
                .authorities("oauth2")
                .secret(finalSecret);
    }

    //用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)。
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //设置token的存储用redis进行保存
        endpoints.tokenStore(new MyRedisTokenStore(redisConnectionFactory))
                //保存该token能允许访问的权限
                .authenticationManager(authenticationManager)
                //保存该token能够允许被请求的方法，get用来获取，post用来验证
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);

    }
}
