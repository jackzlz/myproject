/**
 * 
 */
package com.example.myproject.util;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * <p>
 * 类的描述
 * </p>
 *
 * @author zhenglz 2016年12月5日
 *
 */
public class RedisUtil {

	/** 加锁标志 */
	private static final String LOCKED = "TRUE";

	// 锁定时间(s)
	private static final int MAX_LOCK_TIME = 60;

	// 超时时间(s)
	private static final int MAX_TIMEOUT = 10;

	private static StringRedisTemplate stringRedisTemplate;

	@Autowired
	public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
		RedisUtil.stringRedisTemplate = stringRedisTemplate;
	}

	/**
	 * 压栈
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static Long push(String key, String value) {
		return stringRedisTemplate.opsForList().leftPush(key, value);
	}

	/**
	 * 出栈
	 * 
	 * @param key
	 * @return
	 */
	public static String pop(String key) {
		return stringRedisTemplate.opsForList().leftPop(key);
	}

	/**
	 * 入队
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static Long in(String key, String value) {
		return stringRedisTemplate.opsForList().rightPush(key, value);
	}

	/**
	 * 出队
	 * 
	 * @param key
	 * @return
	 */
	public static String out(String key) {
		return stringRedisTemplate.opsForList().leftPop(key);
	}

	/**
	 * 栈/队列长
	 * 
	 * @param key
	 * @return
	 */
	public static Long length(String key) {
		return stringRedisTemplate.opsForList().size(key);
	}

	/**
	 * 范围检索
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public static List<String> range(String key, int start, int end) {
		return stringRedisTemplate.opsForList().range(key, start, end);
	}

	/**
	 * 移除
	 * 
	 * @param key
	 * @param i
	 * @param value
	 */
	public static void remove(String key, long i, String value) {
		stringRedisTemplate.opsForList().remove(key, i, value);
	}

	/**
	 * 检索
	 * 
	 * @param key
	 * @param index
	 * @return
	 */
	public static String index(String key, long index) {
		return stringRedisTemplate.opsForList().index(key, index);
	}

	/**
	 * 置值
	 * 
	 * @param key
	 * @param index
	 * @param value
	 */
	public static void set(String key, long index, String value) {
		stringRedisTemplate.opsForList().set(key, index, value);
	}

	/**
	 * 裁剪
	 * 
	 * @param key
	 * @param start
	 * @param end
	 */
	public static void trim(String key, long start, int end) {
		stringRedisTemplate.opsForList().trim(key, start, end);
	}

	public static void set(String key, String value) {
		stringRedisTemplate.opsForValue().set(key, value);
	}

	public static String get(String key) {
		return stringRedisTemplate.opsForValue().get(key);
	}

	public static boolean setNX(String key, String value, int validTime) {
		if (stringRedisTemplate.opsForValue().setIfAbsent(key, value)) {
			// todo throw exception
			if (validTime > 0) {
				return stringRedisTemplate.expire(key, validTime, TimeUnit.SECONDS);
			} else {
				return true;
			}
		}
		return false;
	}

	public static boolean tryLock(String key) {
		String lockKey = "locked#" + key;
		long startTime = System.currentTimeMillis();
		int sleepTimes = 0;

		while (!setNX(lockKey, LOCKED, MAX_LOCK_TIME)) {
			try {
				Thread.sleep(1);

				sleepTimes++;

				if (sleepTimes % 100 == 0) {
					// 超时判断
					if (System.currentTimeMillis() - startTime > MAX_TIMEOUT * 1000) {
						return false;
					}
				}
			} catch (InterruptedException e) {
				return false;
			}
		}

		return true;
	}

	public static void unLock(String key) {
		String lockKey = "locked#" + key;

		delete(lockKey);
	}

	public static void delete(String key) {
		stringRedisTemplate.delete(key);
	}
}
