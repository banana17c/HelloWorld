package com.web.entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.SimpleTransactionStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.entry.dubbo.consumers.HelloConsumer;
import com.web.entry.dubbo.consumers.IwhaleCloudDubboService;

import com.ztesoft.zsmart.bss.drm.app.gis.model.rsp.ActIncReasonResponse;


@RestController
@SpringBootApplication
@Configuration
@EnableDubbo(scanBasePackages = "com.web.entry.dubbo.providers.impl") // TODO
//@PropertySource("classpath:/dubbo-provider.properties")
@ComponentScan(value = {"com.web.entry.dubbo", "com.web.entry.view"})
class ProgramEntry {

//    @Autowired
    private HelloConsumer hello;

    @Autowired
    private IwhaleCloudDubboService iwhaleCloudDubboService;

    @RequestMapping("/")
    public String hello(HttpServletRequest request) {

        System.out.println(request.getHeader("X-Real-IP"));
        System.out.println(request.getHeader("Host"));
        System.out.println(request.getHeader("X-Forwarded-For"));
        System.out.println(request.getHeader("X-Real-Port"));
        System.out.println(request.getScheme());
        System.out.println(request.getServerName());
        System.out.println(request.getRemoteAddr());
        return "Hello World";
    }

    @RequestMapping("/dubbo")
    public String dubbo() {

        return hello.call();
    }

    @RequestMapping("/iwhalecloud")
    public ActIncReasonResponse iwhalecloud() {

        ActIncReasonResponse response = iwhaleCloudDubboService.qryActIncReason();
        System.out.println(response);

        return response;
    }

    @Bean
    @Primary
    public PlatformTransactionManager transactionManager() {
        return new PlatformTransactionManager() {

            @Override
            public TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException {
                System.out.println("get transaction ...");
                return new SimpleTransactionStatus();
            }

            @Override
            public void commit(TransactionStatus status) throws TransactionException {
                System.out.println("commit transaction ...");
            }

            @Override
            public void rollback(TransactionStatus status) throws TransactionException {
                System.out.println("rollback transaction ...");
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(ProgramEntry.class, args);
    }
}
