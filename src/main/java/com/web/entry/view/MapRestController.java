package com.web.entry.view;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <Description> zsmart-drm-parent，其他注解和注解中的参数需要用的时候再加 <br>
 *
 * @author chen.guangwen <br>
 * @CreateDate 2019/5/13 <br>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RestController
@RequestMapping
@interface MapRestController {

    @AliasFor(annotation = RequestMapping.class)
    String[] value() default {"/rest"};
}
