package com.web.entry.dubbo.consumers;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.web.entry.dubbo.providers.HelloService;

@Component
public class HelloConsumer {

    @Reference
    private HelloService helloService;

    /**
     * <Description> zsmart-drm-parent <br>
     *
     * @author chen.guangwen <br>
     * @CreateDate 2019/9/23 <br>
     */
    public String call() {
        return helloService.call();
    }
}
