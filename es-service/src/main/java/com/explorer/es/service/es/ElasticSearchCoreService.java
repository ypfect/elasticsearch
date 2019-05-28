package com.explorer.es.service.es;

import com.explorer.es.export.constant.Result;
import com.explorer.es.export.constant.ResultUtil;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * @Description es 操作
 * @Author stanley.yu
 * @Date 2019/5/28 9:39
 */
public class ElasticSearchCoreService {

    public static final Logger LOG = LoggerFactory.getLogger("es");

    @Autowired
    private RestHighLevelClient client;


    /**
     * 创建索引
     * @param indexName
     * @return
     * @throws IOException
     */
    public Result createIndex(String indexName) throws IOException {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
        CreateIndexResponse createIndexResponse = client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        // 指示是否所有节点都已确认请求
        boolean acknowledged = createIndexResponse.isAcknowledged();
        // 指示是否在超时之前为索引中的每个分片启动了必需的分片副本数
        boolean shardsAcknowledged = createIndexResponse.isShardsAcknowledged();
        if (!acknowledged && !shardsAcknowledged) {
            LOG.info("创建索引失败！索引名称为{}：" + indexName);
        }
        LOG.info("创建索引成功！索引名称为{}：" + indexName);
        return ResultUtil.success();
    }

}
