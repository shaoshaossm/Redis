package com.ssm.Demo;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author shaoshao
 * @version 1.0
 * @date 2022/4/17 15:11
 */
public class JedisDemo1 {

    /**
     * 本机测试
     */
    @Test
    public void test01() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        String ping = jedis.ping();
        System.out.println(ping);
    }

    /**
     * 虚拟机测试
     */
    @Test
    public void test02() {
        Jedis jedis = new Jedis("192.168.174.131", 6379);
        jedis.auth("123456");
        System.out.println(jedis.ping());
    }

    /**
     * Jedis基本操作 string
     */
    @Test
    public void test03() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.set("k1", "v1");
        jedis.set("k2", "v2");
        // 设置多个key-value
        jedis.mset("k3", "v3", "k4", "v4");
        System.out.println(jedis.mget("k3", "k4"));
        System.out.println(jedis.get("k1"));

        for (String key : jedis.keys("*")
        ) {
            System.out.println(key);
        }
        //
    }

    /**
     * 操作list
     */
    @Test
    public void test04() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.lpush("mylist", "hxl", "ssm", "zs");
        List<String> list = jedis.lrange("mylist", 0, -1);
        for (String element : list) {
            System.out.println(element);
        }
    }

    /**
     * 操作set
     */
    @Test
    public void test05() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.sadd("name", "hxl", "ssm", "zs");
        Set<String> name = jedis.smembers("name");
        System.out.println(name);
    }

    /**
     * 操作hash
     */
    @Test
    public void test06() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.hset("users", "age", "20");
        System.out.println(jedis.hget("users", "age"));

        jedis.hset("hash1", "userName", "lisi");
        System.out.println(jedis.hget("hash1", "userName"));
        Map<String, String> map = new HashMap<String, String>();
        map.put("telphone", "13810169999");
        map.put("address", "atguigu");
        map.put("email", "abc@163.com");
        jedis.hmset("hash2", map);
        List<String> result = jedis.hmget("hash2", "telphone", "email", "address");
        System.out.println(result);
        for (String element : result) {
            System.out.println(element);
        }

    }

    /**
     * 操作zset
     */
    @Test
    public void test07() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.zadd("score", 100d, "少司命");
        System.out.println(jedis.zrange("score", 0, -1));
    }

}
