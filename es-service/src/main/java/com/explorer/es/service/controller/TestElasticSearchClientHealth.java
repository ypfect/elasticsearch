package com.explorer.es.service.controller;

import com.alibaba.fastjson.JSON;
import com.explorer.es.service.service.ClusterHealthService;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @Description
 * @Author stanley.yu
 * @Date 2019/5/27 16:11
 */
@RestController
public class TestElasticSearchClientHealth {

    @Autowired
    private ClusterHealthService healthService;

    @GetMapping("/health")
    public String testHealth() throws IOException {
        ClusterHealthResponse clusterHealthResponse = healthService.clusterHealth();
        return JSON.toJSONString(clusterHealthResponse);
    }
}
