package cn.dev33.satoken.reactor.model;

import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseCookie.ResponseCookieBuilder;
import org.springframework.http.server.reactive.ServerHttpResponse;

import cn.dev33.satoken.context.model.SaResponse;
import cn.dev33.satoken.util.SaTokenInsideUtil;

/**
 * Response for Reactor
 * @author kong
 *
 */
public class SaResponseForReactor implements SaResponse {

	/**
	 * 底层Response对象
	 */
	ServerHttpResponse response;
	
	/**
	 * 实例化
	 * @param response response对象 
	 */
	public SaResponseForReactor(ServerHttpResponse response) {
		this.response = response;
	}
	
	/**
	 * 获取底层源对象 
	 */
	@Override
	public Object getSource() {
		return response;
	}

	/**
	 * 删除指定Cookie 
	 */
	@Override
	public void deleteCookie(String name) {
		addCookie(name, null, null, null, 0);
	}

	/**
	 * 写入指定Cookie 
	 */
	@Override
	public void addCookie(String name, String value, String path, String domain, int timeout) {
		
		// 构建CookieBuilder
		ResponseCookieBuilder builder = ResponseCookie.from(name, value)
				.domain(domain)
				.path(path)
				.maxAge(timeout)
				;
		
		// set path 
		if(SaTokenInsideUtil.isEmpty(path) == true) {
			path = "/";
		}
		builder.path(path);
		
		// set domain 
		if(SaTokenInsideUtil.isEmpty(domain) == false) {
			builder.domain(domain);
		}
		
		// 写入Cookie 
		response.addCookie(builder.build());
	}

}
