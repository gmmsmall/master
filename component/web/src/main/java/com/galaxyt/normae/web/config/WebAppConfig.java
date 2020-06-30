package com.galaxyt.normae.web.config;

import com.galaxyt.normae.web.handler.GlobalRequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * Web 配置
 * @author zhouqi
 * @date 2020/5/18 11:44
 * @version v1.0.0
 * @Description
 *
 * Modification History:
 * Date                 Author          Version          Description
---------------------------------------------------------------------------------*
 * 2020/5/18 11:44     zhouqi          v1.0.0           Created
 *
 */
@Slf4j
@Configuration
public class WebAppConfig extends WebMvcConfigurationSupport {


    @Autowired
    private GlobalRequestHandler globalRequestHandler;


    /**
     * 处理开启全局异常拦截之后的问题
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        super.addResourceHandlers(registry);
    }

    /**
     * 注册拦截器
     * @param registry
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(this.globalRequestHandler)  //注册改拦截器
                .addPathPatterns("/**")     //表示拦截所有的请求，
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");  //排除 swagger 拦截
    }
}
