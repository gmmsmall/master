package com.galaxyt.normae.gateway.security;

import com.galaxyt.normae.core.thread.GlobalThreadFactory;
import com.galaxyt.normae.core.util.HttpUtil;
import com.galaxyt.normae.core.util.json.GsonUtil;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * 权限拉取
 * @author zhouqi
 * @date 2020/6/10 11:30
 * @version v1.0.0
 * @Description
 *
 * Modification History:
 * Date                 Author          Version          Description
---------------------------------------------------------------------------------*
 * 2020/6/10 11:30     zhouqi          v1.0.0           Created
 *
 */
@Slf4j
@Component
public class AuthorityPull {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private GatewayProperties gatewayProperties;

    /**
     * 已经拉取成功的客户端 ID 集合
     */
    private Set<String> alreadyPulledClientIdList = new HashSet<>();

    /**
     * 全部客户端 ID 集合
     */
    private Set<String> cLientIdList;

    /**
     * 拉取时间间隔 , 毫秒值
     * 若未全部拉取成功则每间隔 5 秒拉取一次
     */
    private final long PULL_INTERVAL_IN_MILLIS = 5000;

    /**
     * 初始化方法
     */
    public void initialize() {

        //获取本网关所代理的全部客户端的 ID
        this.cLientIdList = this.gatewayProperties.getRoutes().stream().map(RouteDefinition::getId).collect(Collectors.toSet());

        /*
        单线程执行拉取
         */
        Executors.newSingleThreadExecutor(GlobalThreadFactory.create("AuthorityPull", true)).submit(() -> {

            //若拉取不成功则会一直拉取
            while (true) {
                //若拉取成功则跳出循环终止拉取程序
                boolean pullComplete = this.pull();
                if (pullComplete) {
                    break;
                }
                //若不能跳出循环则需要等待一段时间之后再次进行拉取
                try {
                    Thread.sleep(PULL_INTERVAL_IN_MILLIS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //在跳出循环后将权限的初始化状态标记为 true
            AuthorityRegistry.INSTANCE.initialized();

            log.info("全部节点权限已拉取完成并成功缓存[{}]",AuthorityRegistry.INSTANCE.toString());

        });

    }


    /**
     * 拉取方法
     * @return
     * 方法内部会进行判断是否已经将本网关所能代理的全部服务的权限都拉取完成
     * true : 已全部拉取完毕
     * false : 还有未拉取完成的
     */
    private boolean pull() {

        //获取 eureka 客户端
        EurekaClient eurekaClient = this.applicationContext.getBean(DiscoveryClient.class);

        //循环每个客户端 ID
        for (String clientId : this.cLientIdList) {

            //若这个服务已经拉取完成则执行下一个服务
            if (this.alreadyPulledClientIdList.contains(clientId)) {
                continue;
            }

            //从 eureka 中得到该服务的全部实例 , 有可能该实例还未注册到 eureka 中 , 所以可能会返回 null
            Optional<Application> applicationO = Optional.ofNullable(eurekaClient.getApplication(clientId));
            applicationO.ifPresent(application -> {

                //得到全部的实例列表
                List<InstanceInfo> clientInstanceList = application.getInstancesAsIsFromEureka();
                //循环拉取
                for (InstanceInfo instanceInfo : clientInstanceList) {
                    //拼接请求 URL
                    String authorityPullEndpoint = String.format("http://%s:%s/endpoint/authority", instanceInfo.getIPAddr(), instanceInfo.getPort());
                    try {
                        //查询并添加缓存
                        AuthorityWrapper[] authoritys = HttpUtil.INSTANCE.get(authorityPullEndpoint, response -> GsonUtil.gson.fromJson(response, AuthorityWrapper[].class));
                        ConcurrentHashMap<String, AuthorityWrapper> authorityWrapperMap = new ConcurrentHashMap<>(16, 0.75f, 4);
                        for (AuthorityWrapper wrapper : authoritys) {
                            authorityWrapperMap.put(String.format("%s:%s", wrapper.getMethod(), wrapper.getUrl()), wrapper);
                        }
                        AuthorityRegistry.INSTANCE.put(clientId, authorityWrapperMap);
                        this.alreadyPulledClientIdList.add(clientId);       //将该节点标记为已拉取状态

                        log.info("拉取到服务[{}]的权限[{}]并已缓存", clientId, GsonUtil.getJson(authoritys));

                    } catch (Exception e) { //若拉取失败则进行下一次循环 , 直到全部节点全部拉取过
                        continue;
                    }
                }

            });
        }
        //判断是否已经全部拉取成功并返回
        return this.alreadyPulledClientIdList.size() == this.cLientIdList.size();
    }





}
