package com.explorer.es.service.service;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ClusterHealthService {

	@Autowired
	private RestHighLevelClient highLevelClient;

	public ClusterHealthResponse clusterHealth() throws IOException {
		ClusterHealthRequest request = new ClusterHealthRequest();
		request.timeout(TimeValue.timeValueSeconds(50));
		request.timeout("50s");
		ClusterHealthResponse response = highLevelClient.cluster().health(request, RequestOptions.DEFAULT);
		return response;
	}

}
