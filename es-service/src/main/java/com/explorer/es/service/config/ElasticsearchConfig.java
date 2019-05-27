package com.explorer.es.service.config;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description  https://blog.csdn.net/u010177412/article/details/82835230
 * @Author stanley.yu
 * @Date 2019/5/27 14:18
 */
@Configuration
@ConfigurationProperties(prefix = "elasticsearch")
public class ElasticsearchConfig {

    public static final Logger log = LoggerFactory.getLogger(ElasticsearchConfig.class);
    private String host;
    private String port;
    private String username;
    private String password;

    @Bean
    public RestHighLevelClient esConfig(){
        // 设置请求头，每个请求都会带上这个请求头
        Header[] defaultHeaders = {new BasicHeader("header", "value")};//可以设置约定
        RestClientBuilder clientBuilder = RestClient.builder(new HttpHost(host, Integer.valueOf(port), "http"));
        clientBuilder.setDefaultHeaders(defaultHeaders);
        // 设置监听器，每次节点失败都可以监听到，可以作额外处理
        clientBuilder.setFailureListener(new RestClient.FailureListener() {

            @Override
            public void onFailure(Node node) {
                super.onFailure(node);
                log.error(node.getName() + "节点注册失败...");
            }
        });

        /* 配置节点选择器，客户端以循环方式将每个请求发送到每一个配置的节点上，
        发送请求的节点，用于过滤客户端，将请求发送到这些客户端节点，默认向每个配置节点发送，
        这个配置通常是用户在启用嗅探时向专用主节点发送请求（即只有专用的主节点应该被HTTP请求命中）
        */
        clientBuilder.setNodeSelector(NodeSelector.SKIP_DEDICATED_MASTERS);

        /*
        配置异步请求的线程数量，Apache Http Async Client默认启动一个调度程序线程，以及由连接管理器使用的许多工作线程
        （与本地检测到的处理器数量一样多，取决于Runtime.getRuntime().availableProcessors()返回的数量）。线程数可以修改如下,
        这里是修改为1个线程，即默认情况
         */
        clientBuilder.setHttpClientConfigCallback(httpAsyncClientBuilder -> httpAsyncClientBuilder.setDefaultIOReactorConfig(IOReactorConfig.custom().setIoThreadCount(1).build()));

        /*
        配置请求超时，将连接超时（默认为1秒）和套接字超时（默认为30秒）增加，
        这里配置完应该相应地调整最大重试超时（默认为30秒），即上面的setMaxRetryTimeoutMillis，一般于最大的那个值一致即60000
         */
        clientBuilder.setRequestConfigCallback(builder -> builder.setConnectTimeout(5000).setSocketTimeout(60000));

         /*
           如果ES设置了密码，那这里也提供了一个基本的认证机制，下面设置了ES需要基本身份验证的默认凭据提供程序
         */
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
        clientBuilder.setHttpClientConfigCallback(httpAsyncClientBuilder -> {
            // 禁用抢先认证的方式
            // 不禁用的话意味着每个请求都将在没有授权标头的情况下发送，然后查看它是否被接受，并且在收到HTTP 401响应后，它再使用基本认证头重新发送完全相同的请求，这个可能是基于安全、性能的考虑
            httpAsyncClientBuilder.disableAuthCaching();
            return httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
        });
        return new RestHighLevelClient(clientBuilder);
    }





    // 自定义节点选择器的需求
    // 设置分配感知节点选择器，允许选择本地机架中的节点（如果有），否则转到任何机架中的任何其他节点
//        clientBuilder.setNodeSelector(iterable -> {
//            boolean foundOne = false;
//            for (Node node : iterable) {
//                String rackId = node.getAttributes().get("rack_id").get(0);
//                if ("rack_one".equals(rackId)) {
//                    foundOne = true;
//                    break;
//                }
//            }
//            if (foundOne) {
//                Iterator<Node> nodesIt = iterable.iterator();
//                while (nodesIt.hasNext()) {
//                    Node node = nodesIt.next();
//                    String rackId = node.getAttributes().get("rack_id").get(0);
//                    if ("rack_one".equals(rackId) == false) {
//                        nodesIt.remove();
//                    }
//                }
//            }
//        });


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
