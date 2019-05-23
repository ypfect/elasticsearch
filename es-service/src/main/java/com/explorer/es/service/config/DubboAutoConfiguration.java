package com.explorer.es.service.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @Description
 * @Author stanley.yu
 * @Date 2019/5/23 15:31
 */
@Configuration
@ConditionalOnProperty(name = {"dubbo.zookeeper.address","dubbo.name"})
@ImportResource("classpath:dubbo-interfaces.xml")
public class DubboAutoConfiguration {
}
