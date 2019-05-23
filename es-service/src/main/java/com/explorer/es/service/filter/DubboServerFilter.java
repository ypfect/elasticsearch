/* 
 * 创建日期 Aug 18, 2015
 *
 * 成都澳乐科技有限公司
 * 电话：028-85253121 
 * 传真：028-85253121
 * 邮编：610041 
 * 地址：成都市武侯区航空路丰德国际广场C座3楼
 * 版权所有
 */
package com.explorer.es.service.filter;

import com.alibaba.dubbo.rpc.*;
import com.alibaba.fastjson.JSON;
import com.explorer.es.service.dubbo.Const;
import com.explorer.es.service.dubbo.DubboClientInvoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * dobbo协议请求拦截器，用于监测各种上层应用调用情况
 * <li>项目名称：itrip_openapi</li>
 * <li>创建人：MacChen</li>
 * <li>创建时间：Aug 18, 2015 11:30:48 AM</li>
 * <li>联系邮箱：mac.chen@aoliday.com</li>
 * 
 * @version 0.0.1
 */
public class DubboServerFilter extends DubboAbstractFilter {

	private static final Logger RPCLOG = LoggerFactory.getLogger("dubborpc");
	private static final String LOG_INFO = "RPC info: API[ %s ], method[ %s ], args[ %s ], spendTime[ %s ms ], clientIp[ %s ], clientPort[ %s ], clientName[ %s ],(rid:%s)";

	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

		// 在日志里自动追加requestId
		String requestId = String.valueOf(invocation.getAttachment(Const.TRACE_ID));
		MDC.put(Const.TRACE_ID, requestId);

		Object args[] = invocation.getArguments();
		long start = System.currentTimeMillis();
		try {
			return invoker.invoke(invocation);
		} catch (RpcException re) {
			RPCLOG.error(String.format("rpc error,args:%s", JSON.toJSONString(args)), re);
			throw re;
		} catch (Throwable e) {
			RPCLOG.error(String.format("rpc error,args:%s", JSON.toJSONString(args)), e);
			throw new RpcException("Dubbo server uncaught exception!", e);
		} finally {
			long spend = (System.currentTimeMillis() - start);
			int clientPort = RpcContext.getContext().getRemotePort();
			String clientIP = RpcContext.getContext().getRemoteHost();
			String clientName = invocation.getAttachment(DubboClientInvoker.CLIENT_APPLICATION);
			String loginfo = String.format(LOG_INFO, invoker.getInterface().getSimpleName(), invocation.getMethodName(),
					JSON.toJSONString(args), spend, clientIP, clientPort, clientName,requestId);

			RPCLOG.info(loginfo);
		}
	}

}
