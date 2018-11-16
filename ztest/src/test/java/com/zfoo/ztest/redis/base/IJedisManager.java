package com.zfoo.ztest.redis.base;

import java.util.List;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2018/11/14
 */
public interface IJedisManager {

	String set(String key, String value);
	String get(String key);
	Boolean exists(String key);
	Long expire(String key, int seconds);
	Long ttl(String key);
	Long incr(String key);
	Long hset(String key, String field, String value);
	String hget(String key, String field);
	Long hdel(String key, String... field);
	Boolean hexists(String key, String field);
	List<String> hvals(String key);
	Long del(String key);
}
