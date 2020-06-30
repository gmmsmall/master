package com.galaxyt.normae.security.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

/**
 * 用于获取全部需要通过网关向外暴露的接口
 * @author zhouqi
 * @date 2020/6/9 17:54
 * @version v1.0.0
 * @Description
 *
 * Modification History:
 * Date                 Author          Version          Description
---------------------------------------------------------------------------------*
 * 2020/6/9 17:54     zhouqi          v1.0.0           Created
 *
 */
@Slf4j
@Component
public class RequestMappingProcessor {

    @Autowired
    private WebApplicationContext webApplicationContext;


    public Collection<RequestMappingWrapper> findAll() {

        //未避免与 Swagger2 中的一个类型冲突 , 所以使用 beanName 的方式获取
        RequestMappingHandlerMapping mapping = (RequestMappingHandlerMapping) this.webApplicationContext.getBean("requestMappingHandlerMapping");
        //获取全部方法
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();

        //用于装载解析出来的全部权限
        Map<String, RequestMappingWrapper> rmMap = new HashMap<>();

        //循环
        for (RequestMappingInfo info : map.keySet()) {

            //获取提供该接口的方法
            HandlerMethod method = map.get(info);

            /*
            获取该方法上标注的 Authority 注解
            仅当该接口方法上存在 Authority 注解时才会认为该接口会通过网关进行暴露 , 否则程序将不再对该接口进行处理 , 网关也不会允许访问该接口
             */
            Optional<Authority> authorityO = Optional.ofNullable(method.getMethodAnnotation(Authority.class));
            authorityO.ifPresent(authority -> {

                //同一个项目中不允许存在两个同样的 mark , 否则会直接导致程序停机
                if (rmMap.containsKey(authority.mark())) {
                    log.error("检查到相同的权限标识[{}]被标记于[{}] , 系统即将停机 ! ", authority.mark(), method.getMethod().toGenericString());
                    System.exit(0);
                }

                //获取 url 地址
                String url = (String) info.getPatternsCondition().getPatterns().toArray()[0];
                //获取请求类型
                String requestMethod = info.getMethodsCondition().getMethods().toArray()[0].toString();

                RequestMappingWrapper wrapper = new RequestMappingWrapper();
                wrapper.setUrl(url);
                wrapper.setMethod(requestMethod);
                wrapper.setMark(authority.mark());
                wrapper.setName(authority.name());
                wrapper.setLogin(authority.isLogin());
                wrapper.setHaveAuthority(authority.isHaveAuthority());

                rmMap.put(authority.mark(), wrapper);

                log.debug(wrapper.toString());

            });

        }


        return rmMap.values();
    }





}
