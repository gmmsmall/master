package com.galaxyt.normae.api.uaa;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * UAA 服务熔断
 * @author zhouqi
 * @date 2020/5/28 14:18
 * @version v1.0.0
 * @Description
 *
 * Modification History:
 * Date                 Author          Version          Description
---------------------------------------------------------------------------------*
 * 2020/5/28 14:18     zhouqi          v1.0.0           Created
 *
 */
@Slf4j
@Component
public class UaaFallback implements FallbackFactory<UaaClient> {

    @Override
    public UaaClient create(Throwable throwable) {
        return null;
    }


}
