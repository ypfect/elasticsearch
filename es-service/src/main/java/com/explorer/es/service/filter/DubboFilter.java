package com.explorer.es.service.filter;

import com.alibaba.dubbo.rpc.*;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 过滤器
 * @author Sean.Yang
 *
 */
public class DubboFilter implements Filter {

	protected static final Logger LOG = LoggerFactory.getLogger("dubborpc");

	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		Object args[] = invocation.getArguments();
		String argsJson = JSON.toJSONString(args);
		long start = System.currentTimeMillis();
		try {
			return invoker.invoke(invocation);
		} catch (RpcException re) {
			LOG.error(String.format("Duubo rpc error, args:[ {} ].", argsJson), re);
			throw re;
		} catch (Throwable e) {
			LOG.error(String.format("Duubo rpc error, args:[ {} ].", argsJson), e);
			throw new RpcException("Dubbo server uncaught exception!", e);
		} finally {
			long spend = (System.currentTimeMillis() - start);
			int clientPort = RpcContext.getContext().getRemotePort();
			// 获取调用方IP地址
			String clientIP = RpcContext.getContext().getRemoteHost();
			LOG.info("DubboRPC info: API[ {} ], method[ {} ], args[ {} ], spendTime[ {} ms ], clientIp[ {} ], clientPort[ {} ].",
					invoker.getInterface().getSimpleName(), invocation.getMethodName(), argsJson, spend, clientIP, clientPort);
		}
	}

}
