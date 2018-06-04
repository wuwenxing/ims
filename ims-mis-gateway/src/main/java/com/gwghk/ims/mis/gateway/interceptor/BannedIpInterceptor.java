package com.gwghk.ims.mis.gateway.interceptor;

import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.netty.util.HashedWheelTimer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.gwghk.ims.mis.gateway.util.IPUtil;


/**
 * 摘要：访问频率拦截器
 * @author Gavin.guo
 * @version 1.0
 * @Date 2017年8月2日
 */
public class BannedIpInterceptor extends HandlerInterceptorAdapter {
	private static final Logger LOGGER = LoggerFactory.getLogger(BannedIpInterceptor.class);
	private int requestPerSeconds = 10; 	// 每秒多少次请求
	private long bannedTime = 3000; 		// 1800000; // 禁止时间, 毫秒
	private int statusCode = 403;
	private IpAccessRateLimiter rateLimiter;
	private BannedList bannedList;

	@PostConstruct
	public void init() {
		LOGGER.info("init->setting: {}", this);
		rateLimiter = new IpAccessRateLimiter(requestPerSeconds);
		bannedList = new BannedList(bannedTime);
	}

	@PreDestroy
	public void destroy() {
		LOGGER.info("destroy->shutdown all WHEEL_TIMER.");
		if (rateLimiter != null) {
			rateLimiter.shutdown();
		}
		if (bannedList != null) {
			bannedList.shutdown();
		}
	}

	@Override
	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
			throws Exception {
		final String clientIp = IPUtil.getClientIP(request);
		final boolean flag = checkIp(clientIp);
		LOGGER.debug("preHandler->check ip: {}, result: {}", clientIp, flag);
		if (!flag) {
			response.setStatus(statusCode);
		}
		return flag;
	}

	@Override
	public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler,
			final ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

	/**
	 * check ip whether banned!
	 *
	 * @param clientIp
	 *            ip address
	 * @return true, if pass, false otherwise.
	 *
	 * @throws java.util.concurrent.ExecutionException
	 *             Exception.
	 */
	boolean checkIp(final String clientIp) throws java.util.concurrent.ExecutionException {
		if (requestPerSeconds < 1) {
			return true;
		}

		if (bannedList.isBanned(clientIp)) {
			/* banned */
			LOGGER.warn("{} is banned!", clientIp);
			return false;
		}

		if (!rateLimiter.tryAcquire(clientIp)) {
			/* ban it */
			LOGGER.debug("ban {}!", clientIp);
			bannedList.ban(clientIp);
			return false;
		}

		return true;
	}

	public int getRequestPerSeconds() {
		return requestPerSeconds;
	}

	public void setRequestPerSeconds(final int requestPerSeconds) {
		this.requestPerSeconds = requestPerSeconds;
	}

	public long getBannedTime() {
		return bannedTime;
	}

	public void setBannedTime(final long bannedTime) {
		this.bannedTime = bannedTime;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(final int statusCode) {
		this.statusCode = statusCode;
	}

	@Override
	public String toString() {
		return "BannedIpInterceptor{requestPerSeconds=" + requestPerSeconds + ", bannedTime=" + bannedTime
				+ ", statusCode=" + statusCode + ", rateLimiter=" + rateLimiter + ", bannedList=" + bannedList + "} "
				+ super.toString();
	}

	private static class BannedList {
		private static final HashedWheelTimer WHEEL_TIMER = new HashedWheelTimer();
		private static final HashSet<String> BANNED_LIST = new HashSet<>();

		private static final long DEFAULT_BANNED_TIME = 60000L;
		private final long bannedTime;

		BannedList(final long bannedTime) {
			if (bannedTime < 1) {
				this.bannedTime = DEFAULT_BANNED_TIME;
			} else {
				this.bannedTime = bannedTime;
			}
		}

		void ban(final String ipAddress) {
			BANNED_LIST.add(ipAddress);
			WHEEL_TIMER.newTimeout(timeout -> BANNED_LIST.remove(ipAddress), bannedTime, TimeUnit.MILLISECONDS);
		}

		boolean isBanned(final String ipAddress) {
			return BANNED_LIST.contains(ipAddress);
		}

		void shutdown() {
			WHEEL_TIMER.stop();
		}

		@Override
		public String toString() {
			return "BannedList{bannedTime=" + bannedTime + '}';
		}
	}

	private static class IpAccessRateLimiter {
		private static final HashedWheelTimer WHEEL_TIMER = new HashedWheelTimer();
		private static final Map<String, AtomicInteger> RATE_LIMITERS = new ConcurrentHashMap<>();
		private static final int DEFAULT_QPS = 1;

		private final int permitsPerSecond;

		IpAccessRateLimiter(final int permitsPerSecond) {
			if (permitsPerSecond < 1) {
				this.permitsPerSecond = DEFAULT_QPS;
			} else {
				this.permitsPerSecond = permitsPerSecond;
			}
		}

		boolean tryAcquire(final String ipAddress) {
			AtomicInteger atomicInteger = RATE_LIMITERS.get(ipAddress);
			if (atomicInteger == null) {
				atomicInteger = new AtomicInteger(permitsPerSecond);
				RATE_LIMITERS.putIfAbsent(ipAddress, atomicInteger);
				atomicInteger = RATE_LIMITERS.get(ipAddress);
				WHEEL_TIMER.newTimeout(timeout -> RATE_LIMITERS.remove(ipAddress), 1, TimeUnit.SECONDS);
			}
			return atomicInteger.getAndDecrement() > 0;
		}

		void shutdown() {
			WHEEL_TIMER.stop();
		}
	}
}
