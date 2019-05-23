package com.explorer.es.service.dubbo;

import com.alibaba.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用于采集dubbo客户端各种调用日志，以及其他业务信息传递
 * <li>项目名称：itrip_common</li>
 * <li>创建人：MacChen</li>
 * <li>创建时间：Mar 28, 2017 2:25:04 PM</li>
 * <li>联系邮箱：458391178@qq.com</li>
 * <li>jeff.cao 2017/9/7添加追踪ID</li>
 * 
 * @version 0.0.1
 */
public class DubboClientInvoker {

	private static String HOST = Const.HOST + "rpc";;

	public static final String CLIENT_APPLICATION = "CLIENT_APPLICATION";

	private static final Logger LOG = LoggerFactory.getLogger("dubboclient");
	private static final String LOG_ERROR = "[Dubbo client invoke error]: API[%s], method[%s], serverIp[%s], serverPort[%s], error[%s],(rid:%s)";
	private static final String LOG_INFO = "[Dubbo client invoke info]: API[%s], method[%s], spendTime[%s]ms, serverIp[%s], serverPort[%s],(rid:%s)";

	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		String requestId = Const.getRequestId(HOST);
		RpcContext context = RpcContext.getContext();
		String serverIp = context.getRemoteHost();
		int serverPort = context.getRemotePort();
		long start = System.currentTimeMillis();
		try {
			invocation.getAttachments().put(Const.TRACE_ID, requestId);
			invocation.getAttachments().put(CLIENT_APPLICATION, context.getUrl().getParameter("application"));
			return invoker.invoke(invocation);
		} catch (RpcException e) {
			LOG.error(String.format(LOG_ERROR, invoker.getInterface().getSimpleName(), invocation.getMethodName(),
					serverIp, serverPort, e, requestId));
			throw e;
		} finally {
			LOG.info(String.format(LOG_INFO, invoker.getInterface().getSimpleName(), invocation.getMethodName(),
					(System.currentTimeMillis() - start), serverIp, serverPort, requestId));
		}
	}

}
