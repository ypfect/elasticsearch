package com.explorer.es.service.dubbo;

import org.slf4j.MDC;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 请添加描述
 * <li>创建人：Jeff.cao</li>
 * <li>创建时间：2017年9月7日 下午1:41:10</li>
 * 
 * @version 0.0.1
 */

public class Const {

	/** X-Request-id is set by openrestry */
	public static final String HTTP_REQUEST_Id = "x-request-id";

	/** lo4j mdc里记录的请求ID */
	public static final String TRACE_ID = "requestId";

	public static final String HOST = getHostName();

	/***
	 * 如果是RPC调用,可能没有rid,这里添加
	 * 
	 * @return
	 */
	public static String getRequestId(String prefix) {
		String reqId = MDC.get(Const.TRACE_ID);
		if (reqId == null) {
			reqId = new StringBuilder(prefix).append(System.nanoTime()).toString();
			MDC.put(Const.TRACE_ID, reqId);
		}
		return reqId;
	}

	/***
	 * 获取主机名
	 * 
	 * @return
	 */
	public static String getHostName() {
		InetAddress host = null;
		try {
			host = InetAddress.getLocalHost();
			return host.getHostName();
		} catch (UnknownHostException uhe) {
			// 依赖log包
			uhe.printStackTrace();
			return host == null ? "unknown" : host.getHostAddress();
		}
	}
}
