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

import com.alibaba.dubbo.rpc.Filter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class DubboAbstractFilter implements Filter {
	//dubbo协议中额外参数传递key值，主要用于传递ItripRequestInfos信息，value为json字符串
	protected String requestInfoKey = "itrip_request_infos";

	/**
	 * 基本请求信息
	 * <li> 项目名称：itrip_common </li>
	 * <li> 创建人：MacChen </li>
	 * <li> 创建时间：Jul 2, 2015 4:13:20 PM </li>
	 * @version 0.0.1
	 */
	public class ItripRequestInfos implements Serializable {
		private static final long serialVersionUID = 6746017136212296857L;
		/** **************以下信息统一由前台应用设置******************** */
		// 服务ID，会根据dao.properties文件配置获取
		private String serverId;
		// 请求ID，系统生成，能够唯一代表前端用户的一次请求
		private String requestId;
		// 用户请求路径
		private String url;
		// 请求方法:get or post
		private String method;
		// 登陆用户ID
		private Integer userId;
		// 浏览器基本信息
		private String userAgent;
		// 请求页面来源
		private String referer;
		// 请求参数，json格式
		private String requestParams;
		/** **************以下信息统一由openapi设置******************** */
		// 请求openapi接口名称
		private String serviceName;
		// 请求openapi方法名称
		private String methodName;
		// 请求openapi参数列表
		private String methodArgs;
		// 请求openapi开始时间，格式：yyyy-MM-dd HH:mm:ss
		private String beginTime;
		// 请求openapi花费时间，单位：毫秒
		private long costTime;
		// 是否成功调用API
		private boolean success;
		// 执行sql详细信息
		private List<RunSqlInfo> sqlText;
		// api异常信息
		private String error;

		public String getRequestParams() {
			return requestParams;
		}

		public void setRequestParams(String requestParams) {
			this.requestParams = requestParams;
		}

		public String getServerId() {
			return serverId;
		}

		public void setServerId(String serverId) {
			this.serverId = serverId;
		}

		public String getRequestId() {
			return requestId;
		}

		public void setRequestId(String requestId) {
			this.requestId = requestId;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getMethod() {
			return method;
		}

		public void setMethod(String method) {
			this.method = method;
		}

		public Integer getUserId() {
			return userId;
		}

		public void setUserId(Integer userId) {
			this.userId = userId;
		}

		public String getUserAgent() {
			return userAgent;
		}

		public void setUserAgent(String userAgent) {
			this.userAgent = userAgent;
		}

		public String getServiceName() {
			return serviceName;
		}

		public void setServiceName(String serviceName) {
			this.serviceName = serviceName;
		}

		public String getMethodName() {
			return methodName;
		}

		public void setMethodName(String methodName) {
			this.methodName = methodName;
		}

		public String getMethodArgs() {
			return methodArgs;
		}

		public void setMethodArgs(String methodArgs) {
			this.methodArgs = methodArgs;
		}

		public String getBeginTime() {
			return beginTime;
		}

		public void setBeginTime(String beginTime) {
			this.beginTime = beginTime;
		}

		public long getCostTime() {
			return costTime;
		}

		public void setCostTime(long costTime) {
			this.costTime = costTime;
		}

		public boolean isSuccess() {
			return success;
		}

		public void setSuccess(boolean success) {
			this.success = success;
		}

		public String getError() {
			return error;
		}

		public void setError(String error) {
			this.error = error;
		}

		public String getReferer() {
			return referer;
		}

		public void setReferer(String referer) {
			this.referer = referer;
		}

		public List<RunSqlInfo> getSqlText() {
			return sqlText;
		}

		public void setSqlText(List<RunSqlInfo> sqlText) {
			this.sqlText = sqlText;
		}

		public void addSqlText(String id, String sql, String params, long time) {
			if (sqlText == null) {
				sqlText = new ArrayList<>();
			}
			sqlText.add(new RunSqlInfo(id, sql, params, time));
		}

	}

	/**
	 * 项目中运行sql基本信息
	 * <li> 项目名称：itrip_common_log </li>
	 * <li> 创建人：MacChen </li>
	 * <li> 创建时间：Jul 6, 2015 2:18:54 PM </li>
	 * @version 0.0.1
	 */
	class RunSqlInfo implements Serializable {
		private static final long serialVersionUID = -6710519100181167937L;

		public RunSqlInfo() {
		}

		public RunSqlInfo(String id, String sql, String params, long time) {
			super();
			this.id = id;
			this.sql = sql;
			this.params = params;
			this.time = time;
		}

		// sql id,与xml中的id相匹配
		private String id;
		// 具体sql语句，没有匹配参数之前的语句
		private String sql;
		// sql的参数
		private String params;
		// 具体执行时间
		private long time;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getSql() {
			return sql;
		}

		public void setSql(String sql) {
			this.sql = sql;
		}

		public String getParams() {
			return params;
		}

		public void setParams(String params) {
			this.params = params;
		}

		public long getTime() {
			return time;
		}

		public void setTime(long time) {
			this.time = time;
		}

	}

}
