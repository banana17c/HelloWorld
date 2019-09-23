package com.web.entry.dubbo.providers.impl;


import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.web.entry.dubbo.providers.HelloService;

@Service
@Component
@Transactional
public class HelloServiceImpl implements HelloService {
    @Override
    public String call() {
        return "dubbo";
    }
}

